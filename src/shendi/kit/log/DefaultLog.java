package shendi.kit.log;

/**
 * 默认的日志缓存类,简化扩展操作,继承此类,一般只需要重写format,print,save三函数即可.
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 * @version 1.0
 */
public class DefaultLog extends ALog {
	
	/** 所有DefaultLog的实例是否显示日志 */
	private static boolean isLog = true;
	
	/** 日志调用层级 */
	protected final int CALL_LEVEL_LOG = 6;
	
	public DefaultLog(String name) {
		super(name);
		super.isLog = isLog;
	}

	@Override protected int initSize() { return 1000; }

	@Override
	protected String initTitle(String name) { return "-----------" + name + "-----------"; }

	@Override
	protected String format(Object log, Object... objs) {
		return Log.log(log, "[Default]", false, Log.CALL_LEVEL_LOG, objs);
	}

	@Override protected void print(String log) { System.out.print(log); }
	
	@Override protected void save(String log) { Log.log(log, "[Default]", true, CALL_LEVEL_LOG); }
	
	/**
	 * 设置所有DefualtLog对象打印的日志是否在控制台显示
	 * @param isLog 是否在控制台显示
	 */
	public static void setIsLogDef(boolean isLog) { DefaultLog.isLog = isLog; }
}
