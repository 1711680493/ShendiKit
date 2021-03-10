package shendi.kit.console.command;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

import shendi.kit.console.Console;
import shendi.kit.path.PathFactory;
import shendi.kit.util.SKClassLoader;
import shendi.kit.util.StreamUtils;

/**
 * 内置命令,用以执行 Java 代码.<br>
 * 此命令会创建Java源文件,保存至项目文件夹的 temp/source/ExecuteTemp.java
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 * @version 1.0
 * @see Console
 */
public class ExecuteCommand extends Command {

	/** 空指针,用于确定父类构造函数 */
	private static final Method NULL_METHOD = null;
	
	/** 执行语句,代码前缀 */
	public static final String C_FIRST = "public class ExecuteTemp { public void exec() {";
	
	/** 执行语句,代码尾缀 */
	public static final String C_LAST = "} }";
	
	/** 源代码的保存文件 */
	public final File SOURCE;
	
	/** 源文件夹编译后的二进制文件 */
	public final File CLASS;
	
	public ExecuteCommand() {
		super(null, NULL_METHOD, null, "执行Java代码,例如执行语句参数为 /c System.out.print(\"hello,world\")");
		SOURCE = new File(PathFactory.getPath(PathFactory.PROJECT, "/temp/source/ExecuteTemp.java"));
		CLASS = new File(PathFactory.getPath(PathFactory.PROJECT, "/temp/source/ExecuteTemp.class"));
		
		File path = SOURCE.getParentFile();
		if (!path.exists()) path.mkdirs();
	}

	@Override
	public String execute(HashMap<String, String> params) {
		String code = params.get("c");
		
		FileOutputStream output = null;
		FileInputStream input = null;
		
		try {
			output = new FileOutputStream(SOURCE);
			output.write(C_FIRST.concat(code).concat(C_LAST).getBytes());
			
			JavaCompiler c = ToolProvider.getSystemJavaCompiler();
			
			if (c.run(null, null, null, SOURCE.getPath()) == 0) {
				input = new FileInputStream(CLASS);
				byte[] data = StreamUtils.readAllByte(input);
				
				// 字节码转类,并执行,执行后重新加载类加载器
				Class<?> tempClass = SKClassLoader.createClass(null, data, 0, data.length);
				Method tempMethod = tempClass.getMethod("exec");
				tempMethod.invoke(tempClass.getConstructor().newInstance());
				
				SKClassLoader.reload();
			} else return "编译文件失败,请检查代码";
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (input != null) input.close();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (output != null) output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return null;
	}
	
}
