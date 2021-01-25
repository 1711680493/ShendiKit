package shendi.kit.time;

import java.util.Date;

/**
 * 代表一个时间.<br>
 * 此类一经创建就不能改变,并且参数都已初始化.
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 * @version 1.0
 * @since ShendiKit 1.0
 */
public final class Time {
	private final String sTime;
	private final Date dTime;
	private final long lTime;
	/**
	 * 用字符串的时间格式进行初始化.
	 * @param time 字符串类型的时间表示
	 * @param format 格式
	 */
	public Time(String time,TimeFormat format) {
		this.sTime = time;
		dTime = format.getDate(sTime);
		lTime = format.getNum(dTime);
	}
	/**
	 * 用 Date 的时间格式进行初始化.
	 * @param time Date类型的时间表示
	 * @param format 格式
	 */
	public Time(Date time,TimeFormat format) {
		this.dTime = time;
		sTime = format.getString(dTime);
		lTime = format.getNum(dTime);
	}
	/**
	 * 用 long 的时间格式进行初始化.
	 * @param time long类型的时间表示
	 * @param format 格式
	 */
	public Time(long time,TimeFormat format) {
		this.lTime = time;
		dTime = format.getDate(lTime);
		sTime = format.getString(dTime);
	}
	
	@Override
	public String toString() { return sTime; }
}
