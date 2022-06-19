package shendi.kit.log;

/**
 * 调试级别的日志缓存类,日志默认是否在控制台显示根据 {@link Log#isLogDebug()}.
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 * @version 1.0
 */
public class DebugLog extends DefaultLog {

	/**
	 * 是否为调试模式, 当非调试模式, 不做任何操作
	 * @since SK 1.1
	 */
	public boolean isDebug = true;
	
	public DebugLog(String name) {
		super(name);
		super.setIsLog(Log.isLogDebug());
	}
	
	@Override
	protected String format(Object log, Object... objs) {
		return isDebug ? Log.log(log, LogFactory.LEVEL_DEBUG, false, Log.CALL_LEVEL_LOG, objs) : null;
	}
	
	@Override protected void save(String log) {
		if (isDebug) Log.log(log, LogFactory.LEVEL_DEBUG, true, CALL_LEVEL_LOG);
	}
	
}
