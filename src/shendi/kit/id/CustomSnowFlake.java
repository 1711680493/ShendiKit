package shendi.kit.id;

import shendi.kit.exception.NumberException;
import shendi.kit.exception.NumberLessThanZero;
import shendi.kit.util.BitUtil;

/**
 * 自定义雪花算法,生成唯一id,不受64位限制,可任意调整大小.<br>
 * 标准的雪花算法为,第一位为0,接着41位时间戳,5位机房id,5位机器id,12位序列号.<br>
 * 可通过构造创建时来自定义
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 * @version 1.0
 */
public class CustomSnowFlake {
	/** 机房id,默认1 */
    private int groupId = 1;
    
    /** 机器id,默认1 */
    private int hostId = 1;
    
    /** 序列号,默认0 */
    private int sequence = 0;

    /** 机房id所占位数,默认5 */
    private int gBitNum = 5;
    
    /** 机器id所占位数,默认5 */
    private int hBitNum = 5;
    
    /** 序列号所占位数,默认12,最大4096台机器在同一毫秒生成,如果没这么大的需求,可以更改 */
    private int sBitNum = 12;
    
    /** 时间戳所占位数 */
    private int tBitNum = 41;
    
    /** 初始时间戳,当前的默认值可用70年,当前为2020-12-17,可以通过set来更改为自己当前时间来延长,须为固定值 */
    private long sTime = 1608196287204L;
    
    /** 机房id默认的值,1<<17 */
    private int groupValue = 131072;
    
    /** 机器id默认的值,1<<12 */
    private int hostValue = 4096;

    /** 时间戳默认最大值 */
    private long tMax = 2199023255551L;
    
    /** 序列号最大的值 */
    private int sequenceMax = 4095;
    
    /** 上一次时间戳的值,用于处理并发 */
    private long upTime;
    
    /** group bit + host bit + secuence bit num */
    private int ghsBitNum = 22;
    
    /** 使用默认设置创建雪花算法生成器,如果不用于分布式则可使用此创建. */
    public CustomSnowFlake() {}
    
    /**
     * 通过使用默认规格来设置机房id和机器id.
     * @param groupId 机房id
     * @param hostId 机器id
     * @exception NumberException 当groupId/hostId不正确时抛出
     */
    public CustomSnowFlake(int groupId, int hostId) {
    	if (groupId < 0 || groupId > BitUtil.bitMax(gBitNum)) {
    		throw new NumberException("groupId < 0 || groupId > gBitNum!");
    	}
    	if (hostId < 0 || hostId > BitUtil.bitMax(hBitNum)) {
    		throw new NumberException("hostId < 0 || hostId > hBitNum!");
    	}
    	this.groupId = groupId;
    	this.hostId = hostId;
    	
    	this.groupValue = groupId << sBitNum << hBitNum;
    	this.hostValue = hostId << sBitNum;
    }
    
    /**
     * 通过使用默认规格来设置机房id和机器id.
     * @param groupId 机房id
     * @param hostId 机器id
     * @exception NumberException 当groupId/hostId不正确时抛出
     */
    public CustomSnowFlake(int tBitNum, int groupId, int hostId) {
    	if (tBitNum < 0) throw new NumberLessThanZero("tBitNum < 0");
    	if (groupId < 0 || groupId > BitUtil.bitMax(gBitNum)) {
    		throw new NumberException("groupId < 0 || groupId > gBitNum!");
    	}
    	if (hostId < 0 || hostId > BitUtil.bitMax(hBitNum)) {
    		throw new NumberException("hostId < 0 || hostId > hBitNum!");
    	}
    	this.tBitNum = tBitNum;
    	this.groupId = groupId;
    	this.hostId = hostId;
    	
    	this.tMax = ((long)1 << tBitNum) - 1;
    	this.groupValue = groupId << sBitNum << hBitNum;
    	this.hostValue = hostId << sBitNum;
    }
    
    /**
     * 设置算法的一些基础规格
     * @param tBitNum 时间戳所占位
     * @param gBitNum 机房id所占位
     * @param hBitNum 机器id所占位
     * @param sBitNum 序列号所占位
     * @param groupId 机房id
     * @param hostId  机器id
     * @throws NumberException		当参数不正确时抛出
     * @throws NumberLessThanZero	当传递的占位参数小于0时抛出
     */
    public CustomSnowFlake(int tBitNum, int gBitNum, int hBitNum, int sBitNum, int groupId, int hostId) {
    	if (tBitNum < 0 || gBitNum < 0 || hBitNum < 0 || sBitNum < 0) {
    		throw new NumberLessThanZero("has args bit num < 0");
    	}
    	
    	this.tBitNum = tBitNum;
    	this.gBitNum = gBitNum;
    	this.hBitNum = hBitNum;
    	this.sBitNum = sBitNum;
    	
    	if (groupId < 0 || groupId > BitUtil.bitMax(gBitNum)) {
    		throw new NumberException("groupId < 0 || groupId > gBitNum!");
    	}
    	if (hostId < 0 || hostId > BitUtil.bitMax(hBitNum)) {
    		throw new NumberException("hostId < 0 || hostId > hBitNum!");
    	}
    	
    	this.groupId = groupId;
    	this.hostId = hostId;
    	this.ghsBitNum = gBitNum + hBitNum + sBitNum;
    	
    	this.tMax = ((long)1 << tBitNum) - 1;
    	this.groupValue = groupId << sBitNum << hBitNum;
    	this.hostValue = hostId << sBitNum;
    }

    /**
     * 生产一个唯一的id.
     * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
     * @return id
     * @throws RuntimeException 如果生成的id已经超过限制则抛出.
     */
	public synchronized long spawn() {
		long time = System.currentTimeMillis();
		if (time < upTime) throw new NumberException("upTime > time, exception please feedback for https://github.com/1711680493/ShendiKit");
		if (time == upTime) {
			sequence++;
			if (sequence > sequenceMax) return spawn();
		} else if (sequence != 0) {
			sequence = 0;
		}
		upTime = time;
		
		time -= sTime;
		if (time > tMax) throw new RuntimeException("Current time > max, so, please update function or reset sTime.");
		time <<= ghsBitNum;
		
		return time + groupValue + hostValue + sequence;
	}
	
	/** @return 时间戳所占位数 */
	public int getTimeBitNum() { return tBitNum; }
	
	/** @return 机房id */
	public int getGroupId() { return groupId; }
	
	/** @return 机器id */
	public int getHostId() { return hostId; }
	
	/** @param sTime 初始时间戳 */
	public void setStartTime(long sTime) { this.sTime = sTime; }
}
