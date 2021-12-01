/**
 * shendi.kit 模块,使用的 Java8.
 * 为了兼容高版本 Java 所以需要重新编译此导入 jar中.
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 * @version 1.0
 * @since ShendiKit 1.0
 */
module shendi.kit {
	requires transitive java.desktop;
	requires transitive java.compiler;
	
	exports shendi.kit.annotation;
	exports shendi.kit.config;
	
	exports shendi.kit.console;
	exports shendi.kit.console.command;
	
	exports shendi.kit.data;
	exports shendi.kit.deskapp;
	exports shendi.kit.deskapp.effect;
	exports shendi.kit.encrypt;
	exports shendi.kit.exception;
	exports shendi.kit.log;
	exports shendi.kit.log.data;
	exports shendi.kit.log.interpreter;
	exports shendi.kit.path;
	exports shendi.kit.project;
	exports shendi.kit.time;
	exports shendi.kit.util;
	exports shendi.kit.reptile;
	exports shendi.kit.security;
	
	exports shendi.kit.format.json;
	
	exports shendi.kit.id;
	exports shendi.kit.manager;
	
	exports shendi.kit.net;
	exports shendi.kit.net.http;
	
	exports shendi.kit.thread;
}