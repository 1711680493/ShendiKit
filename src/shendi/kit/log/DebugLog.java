package shendi.kit.log;

/**
 * 调试级别的日志缓存类,日志默认是否在控制台显示根据 {@link Log#isLogDebug()}.
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 * @version 1.0
 */
public class DebugLog extends DefaultLog {

	public DebugLog(String name) { super(name); super.setIsLog(Log.isLogDebug()); }
	
	@Override
	protected String format(Object log, Object... objs) {
		return Log.log(log, LogFactory.LEVEL_DEBUG, false, Log.CALL_LEVEL_LOG, objs);
	}
	
	@Override protected void save(String log) { Log.log(log, LogFactory.LEVEL_DEBUG, true, CALL_LEVEL_LOG); }
	
}
