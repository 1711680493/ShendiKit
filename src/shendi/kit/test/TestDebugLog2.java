package shendi.kit.test;

import shendi.kit.log.DebugLog;

public class TestDebugLog2 {
	/** 可以定义成全局变量统一控制, 也可多对象细分名称(需自行管理所有调试日志对象,当生产环境需要将所有对象都设置为非调试模式) */
	public static final DebugLog DLOG = new DebugLog("调试日志");
	
	public static void main(String[] args) {
		
		// 是否为调试模式, 默认true, 当非调试模式则不做任何操作
		DLOG.isDebug = true;
		/* 
			是否在控制台显示, 默认true, 不在控制台显示也会保存到文件
			当非调试模式, 此项无效
		*/
		DLOG.setIsLog(true);
		
		DLOG.log("第一条日志: xxxx, 账号=%s", "123456");
		DLOG.log("第二条日志: xxxx, 密码=%s", "654321");
		DLOG.log("第三条日志: xxxx, 成功");
		
		// 因为是基于日志缓存, 最后需要手动保存到文件, 非调试模式下此项不做任何操作
		// 或者使用 try-with resource
		DLOG.commit();
	}
}
