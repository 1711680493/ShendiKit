package shendi.kit.log;

/**
 * 默认的日志缓存类,简化扩展操作,继承此类,一般只需要重写format,print,save三函数即可.
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 * @version 1.0
 */
public class DefaultLog extends ALog {
	
	/** 日志调用层级 */
	protected final int CALL_LEVEL_LOG = 6;
	
	public DefaultLog(String name) { super(name); }

	@Override protected int initSize() { return 1000; }

	@Override
	protected String initTitle(String name) { return "-----------" + name + "-----------"; }

	@Override
	protected String format(Object log, Object... objs) {
		return Log.log(log, "[Default]", false, Log.CALL_LEVEL_LOG, objs);
	}

	@Override protected void print(String log) { System.out.print(log); }
	
	@Override protected void save(String log) { Log.log(log, "[Default]", true, CALL_LEVEL_LOG); }
	
}
