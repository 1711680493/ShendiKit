package shendi.kit.thread;

/**
 * 线程管理类型.
 * 创建时间 2021年12月1日 下午3:01:16
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 * @version 1.0
 */
public enum ThreadType {
	
	/** 永不等待,当线程状态变为等待状态时,自动中断(用于一直运行的程序,死循环 -有的程序长期运行线程状态莫名其妙变成了等待状态.) */
	NO_WATTING
	
}
