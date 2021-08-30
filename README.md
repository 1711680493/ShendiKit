# Shendi Kit
**author：** Shendi

**版本：** 1.1

**最后更改时间：** 2021-08-13



[引导页面](https://1711680493.github.io)

Java工具包,纯Java制作,使用JDK8

以前版本可在分支中找到



# 文档及测试样例
[在线文档 v1.1](https://1711680493.github.io/doc/SK-1.1)<br>

样例在源码的 shendi.kit.test 包下.



# 目录
### [版本变化](#SK-版本变化)
>[1.1 Start](#v-10)<br>
>[1.1 Small Kit](#v-11)

### [SK 配置](#开始配置)
>[SK 配置文件地址](#配置文件地址)<br>
>[SK 默认配置文件](#默认配置文件)<br>
>
>>[注解扫描配置](#anno_scan.shendi注解扫描配置)<br>

### [控制台](#控制台模块)
>[给自己的程序增加一个控制台模块](#给自己的程序增加一个控制台模块)<br>
>[添加命令](#添加命令)<br>
>[命令的使用](#命令的使用)<br>
>[命令行控制台](#命令行控制台)<br>
>[窗体控制台](#窗体控制台)<br>
>[自定义控制台](#自定义控制台)<br>
>[内置命令](#内置命令)
>>[execute命令](#execute命令)<br>
>>[扩展内置命令](#扩展内置命令)

### [配置文件](#非常简单的使用-properties-配置文件)
### [时间工具](#时间工具包)
### [日志工具](#日志工具包)
### [加密工具](#加密工具包)
### [爬虫工具](#爬虫工具包)
>此包待完善,目前只提供了一些简单地功能<br>
>[获取网页数据](#获取网页数据)<br>
>[两行代码获取所有的a标签](#两行代码获取所有的a标签)<br>
>[元素标签](#元素标签)<br>

### [JSON工具](#JSON工具包)
>待完善<br>
>[JSONObject](#JSONObject)<br>

### [id工具](#id工具包)
>[雪花算法](#SnowFlake)

### [HTTP工具](#HTTP工具包)
>[工具类](#工具类)<br>
>[响应数据处理接口](#响应数据处理接口)<br>
>[文件下载(处理响应体)](#文件下载)<br>
>[重定向/转发(保留session等)](#重定向与转发)

### [简洁实用工具包-shendi.kit.util](#工具类)
>统一在 shendi.kit.util 包下<br>
>[流处理工具类](#StreamUtils)<br>
>[自定义类加载器](#SKClassLoader)<br>
>[HTTP工具类](#HttpUtil) *已移动至 shendi.kit.net.http, 具体请参考文档[HTTP工具](#HTTP工具包)<br>
>[数学工具类](#Math)<br>
>[判空工具类](#IsNullUtil)<br>
>[字节工具类](#ByteUtil)<br>
>[位工具类](#BitUtil)<br>
>[文件工具类](#FileUtil)<br>

# SK 版本变化
## v 1.0
>起航,包含以下<br>
>Properties配置,控制台,时间工具,日志,加密工具,路径工具,爬虫工具,util包等.

## v 1.1
Small kit
<ol>
	<li>
		配置工具
		<ul>
			<li>优化了配置类 PropertiesConfiguration</li>
			<li>ConfigurationFactory类新增方法 getProperty(config, name, encoding(可选))<br>
			等价于 ConfigurationFactory.getConfig(config).getProperty(name);</li>
		</ul>
	</li>
	<li>
		控制台
		<ul>
			<li>控制台在创建时可以设置组,命令注解可以设置组,解决之前多个控制台共用所有命令问题</li>
			<li>命令可传递参数,对于字段,传递的参数直接设置,对于函数,传递的参数将当作函数参数传递,详细信息请参考控制台文档</li>
			<li>控制台的职责简化为为接收命令/返回结果,对于执行命令操作已在父类Console实现(execute)</li>
			<li>新增shendi.kit.console.command包,包含内置命令类</li>
			<li>控制台内置命令 execute,用以执行Java语句</li>
		</ul>
	</li>
	<li>
		HTTP工具
		<ul>
			<li>新增 shendi.kit.net.http 包,将 HttpUtil 从 util 包中提出</li>
			<li>修复HttpUtil 1.0的已知问题,比如无法访问接口等</li>
			<li>在之前POST请求无法带请求参数,现在可以使用addParameter(key,value)或setParameters(param)来直接设置</li>
			<li>增加对HEAD类型支持,以及可从host可携带端口,新增构造 (host, type)</li>
			<li>包内新增HttpDataDispose接口用以处理http响应数据(比如文件下载,具体请参考文档)</li>
			<li>支持重定向与转发</li>
		</ul>
	</li>
	<li>
		util包
		<ul>
			<li>util包中新增Math类,用于处理单位换算等</li>
			<li>util包中新增IsNullUtil类,用于判断给定的参数是否为空(条件可以自行设定)</li>
			<li>util包中新增ByteUtil类,用于处理对字节的操作</li>
			<li>util包中新增BitUtil类,用于处理对bit的操作</li>
			<li>util包中新增FileUtil类,用于处理对文件的操作</li>
		</ul>
	</li>
	<li>
		注解
		<ul>
			<li>解决了扫描注解高版本Java无法扫描本项目的问题</li>
			<li>优化扫描器,使得扫描时不加载对应类的静态方法</li>
            <li>解决SpringBoot打包后扫描出错,参考<a href='#anno_scan.shendi'>anno_scan.shendi</a></li>
		</ul>
	</li>
	<li>
		SKClassLoader
		<ul>
			<li>解决 SKClassLoader在高版本JDK中找不到类的问题</li>
			<li>新增createClass,reload 函数</li>
		</ul>
	</li>
	<li>
		日志
		<ul>
			<li>Log日志输出支持格式化输出,且增加两种日志级别 Debug和Exception,并支持新增日志级别,参考 Log.log 函数</li>
			<li>新增ALog抽象类,用以对日志进行缓存,新增DefaultLog实现类,新增DebugLog类,用以处理Debug日志缓存</li>
            <li>新增 shendi.kit.log.data 包用于格外的日志数据持久化,参考DataLog类</li>
		</ul>
	</li>
	<li>
		路径
		<ul>
			<li>优化了待发布的Path包,解决高版本,JavaWeb等路径获取问题</li>
			<li>可以自行设置项目类型以供确定路径,通过修改 ProjectTypeUtils.type<br>
				这里列举一些常用需要设置的项目类型
				<ul>
					<li>SpringBoot: 需要设置为 ProjectType.Java,不然打包后找不到路径(maven项目可能也需要如此)</li>
				</ul>
			</li>
		</ul>
	</li>
	<li>新增shendi.kit.id包,用于处理id生成</li>
	<li>TimeUtils改进,将Time,TimeFormat从内部类提取,且修复已知BUG</li>
	<li>StreamUtils新增 readAllByte(input) 函数,用以读取输入流中所有的数据</li>
</ol>



# 开始配置
## 配置文件地址

>普通Java项目以及SpringBoot项目等,配置文件地址在项目根目录的/files下<br>
>Web项目配置文件地址在WebContent的/files下<br>
>具体位置以 shendi.kit.path.ProjectPath 为准.
>
>>如果没有此目录,则项目可能无法正常运作



## 默认配置文件

在配置文件地址下(/files)



### anno_scan.shendi

注解扫描配置



**编码：** UTF-8

**描述：** 配置需要扫描的jar以及类路径



**文件内容(不支持注释)**

```
jar文件路径
jar文件路径=此jar文件中要扫描的类的路径
jar文件路径=此jar文件中要扫描的类的路径;路径2;路径3
...
```



其中jar文件路径可以为相对路径，也可以为jar包名，应携带后缀(.jar)

例如

```
shendi-kit-1.1.jar
/lib/shendi-kit-1.1.jar
```



类路径为需要扫描的类所在指定文件夹下，当指定后，不是此文件夹下的类将不会被解析

**当使用SpringBoot或Maven** ，打包后我们的类文件将会在子文件夹下

普通项目打包，jar 文件结构一般如下

```
|-META-INF
	|-MANIFEST.MF
包名,例如
|-shendi
	|-kit
```



SpringBoot jar文件结构

```
|-BOOT-INF
	|-classes
		|-包名
	|-lib
|-META-INF
	|-maven
	|-MANIFEST.MF
|-org
```



于是为了找到包名所在路径，在 anno_scan.shendi 文件里需要指定，否则打包后将会运行出错，指定方式如下

```
jar文件路径=BOOT-INF/classes
```

有多个路径使用分号分隔，且结尾不能为斜杠（BOOT-INF/classes/ 这种将不会被扫描）



如果不需要使用 SK 注解，则可将此文件内容改为(大小写一致)

```
No
```



如果不需要扫描 Jar 包，则可将此文件内容改为(内容需完全一模一样)

```
No Jar
```



**当项目打包后，且使用到了SK注解，则打包后的jar需要写入此文件里，否则将扫描不到**





#### 以前版本使用

**SK 1.0**

使用分号分隔，不支持 jar 包的类在子文件夹下

```
jar1.jar;jar2.jar
```

为了避免重复,可以加上jar包所在的相对路径,如下

/lib/jar1.jar;/lib/jar2.jar

根据jar包后缀进行判断,所以如果需要扫描所有jar,配置文件内容可以为 .jar



在扫描时会触发此类的静态代码块,所以扫描所有jar则可能出现一些问题,例如mysql的jdbc驱动...

文件内容也可为以下内容(大小写需一致)

```
No
```

```
No Jar
```

No代表移除注解扫描，No Jar则不扫描jar

**jar文件内的jar将不会被扫描**





# 控制台模块

如果你要开发的程序是命令行形式的,则使用此模块可以让你快速开发<br>
对于一般程序(Java Web等),通常我们会需要查看当前项目有几个用户在线...或者进行调试之类的<br>
对于此种需求,此工具包提供了控制台模块
>建议你的程序添加此模块,即使使用不到,以备不时之需.
<br>

测试样例 shendi.kit.test.TestConsole<br>

## 给自己的程序增加一个控制台模块
为了使得开发变得简单,提供了注解的方式,且注解方式能满足大部分需求<br>
<br>
将控制台模块添加进自己的程序非常的简单(仅需一行代码!)<br>
控制台有很多种,例如下方的代码将命令行控制台添加进自己的程序
>new CommandConsole().register();

运行程序后,会在控制台中看到输出...<br>
<br>
对于控制台的操作大致分为五部分,分别为
<ol>
	<li>创建类</li>
	<li>创建方法/字段</li>
	<li>给类添加 @ConsoleAnno 注解
		<ol>
			<li>在JDK9模块化后,需要导出此类所在的包,在module-info.java中exports</li>
		</ol>
	</li>
	<li>给方法/字段添加 @CommandAnno(name,info) 注解</li>
	<li>注册控制台(目前只提供了几种控制台,下方会一一列举,后续会增加(当然你也可以自己创建,如何创建请看文档))
		<ul>
			<li>通过控制台对象的 register() 函数来注册</li>
			<li>例如: new CommandConsole().register();</li>
		</ul>
	</li>
</ol>

### 在1.1版本中新增-命令分组
通过给注解添加参数 @ConsoleAnno("group") 来将一个类里所有未设置组的命令设置组<br>
在注册控制台时,通过重载的函数来设置控制台所使用的组<br>
>例如: new CommandConsole().register("group");

如果没有设置组则使用默认组.

## 添加命令
引入注解
>import shendi.kit.annotation.ConsoleAnno;<br>
>import shendi.kit.annotation.CommandAnno;

<b>将 ConsoleAnno 注解添加到类上,此类则为控制台注解类,接收一个参数 group,代表此类中的命令的默认分组,可为空<br>
(此步骤为必须的,否则使用注解创建命令则无法被识别)</b>

使用 @CommandAnno(name,info) 注解来增加一条命令<br>
>其中,name为String类型,代表命令的名称,info也是String类型,代表命令的附加信息<br>
>此注解只能修饰在字段或函数上<br>
>>如果是函数,必须是public修饰的,方法返回值必须为java.lang.String,有一参数为 HashMap<String, String><br>
>>例如 public String test(HashMap<String, String> args) { return null; }

在1.1版本新增设置组功能<br>
>例如: @CommandAnno(name, info, group="group");<br>
>命令中设置组优先于控制台组,如果未设置,则使用控制台组.

## 命令的使用
对于不同控制台,命令使用方法是一致的,因为我将此操作放到了 Console 类中.<br>
一个有效的命令字符串格式为:
>命令名称 [/参数 [参数值]]

例如,使用内置命令 execute(执行java代码)
>execute /c System.out.print("hello,world");

<br>

对于字段形式创建的命令<br>
获取字段内容: 直接输入命令名<br>
设置字段内容: 使用参数 /set<br>
<br>
例如以下代码创建的命令
>@CommandAnno(name="name", info="我的昵称") public String name = null;

输入 name,控制台会输出如下
>Shendi

输入<br>
>name /set Shendi<br>
>name

输出如下,在设置完后也会输出字段的值
>Shendi<br>
>Shendi

## 命令行控制台
实现类: shendi.kit.console.CommandConsole<br>
描述: 提供一个可以在命令行控制台进行交互的功能,功能简单的控制台<br>
自带三个命令 help/reload/exit<br>
help		显示所有命令<br>
reload		重新加载命令(可以动态修改)<br>
exit		退出命令行控制台

## 窗体控制台
实现类: shendi.kit.console.DeskAppConsole<br>
描述: 窗体控制台,内嵌命令行控制台,并提供可以设置实时显示命令的方法<br>
<b>窗体控制台与程序是绑定在一起的,当控制台被关闭,程序也会被关闭</b><br>

## 自定义控制台
如果不满足当前的命令行控制台可以自定义一个,方法如下<br>
新建一个类继承 shendi.kit.console.Console 类<br>
实现以下两个方法
>protected void register(HashMap<String, Command> commands)<br>
>public void destroy()

register代表注册,destroy代表销毁<br>
在register中传递的参数 commands 代表当前已有的所有命令<br>
>命令集合是一个HashMap,键为命令名,值为命令(包含函数/字段),要了解更多可以查阅 shendi.kit.data.Command 类

register函数的职责为启动控制台,并接收命令和返回命令结果,对于处理命令,已封装至 Console.execute 函数中

## 内置命令
1.1版本新增<br>
对于控制台,很多时候都需要自带命令,所以在Console类中提供了 extraCommand() 函数以供子类实现来扩展内置命令<br>

### execute命令
执行输入的 Java 代码<br>
参数<br>
/c - 指定要执行的Java语句<br>
<br>
使用 execute 命令,将会生成代码对应的源文件以及二进制文件(java and class)<br>
文件保存在项目根目录的/temp/source 下,名称为 ExecuteTemp<br>
具体保存路径可以参考 shendi.kit.path.ProjectPath

### 扩展内置命令
首先需要创建一个命令,对于命令,则需要认识 Command 类<br>
一个 Command 类代表了一个命令,拥有函数 execute(HashMap<String, String> args);<br>
执行一个命令时将会调用此函数,通过重写此函数来达到自己想要的效果<br>
<br>
对于 Command 类的创建,可以参考 shendi.kit.console.command.ExecuteCommand 类<br>
<br>
创建完命令后,在控制台的extraCommand()函数中将命令添加进 commands 命令集合

# 非常简单的使用 Properties 配置文件
## 功能介绍
>可以轻松获取到Properties配置文件<br>
>可以达到实时的效果<br>
>可以使用注解的方式标记一个类,从配置文件里获取到对应类和类的全路径(本人用策略模式较多,所以就封装了这样的功能).
## main.properties
>在使用之前我们需要在项目的根目录下新增一个名为 files 的文件夹,并在文件夹下新建一个 main.properties 文件.<br>
>Maven 项目也是放到根目录下<br>
>如果使用的是Web项目,将 files/main.properties 放到 WebContent 下<br>
>main.properties 中存放其他配置文件的路径,例如:
>>有一配置文件 config.properties 在项目根目录下,并且我们想给这个配置文件命名为 config
>>那么 main.properties 的内容为config=/config.properties
## 使用注解的方式
>使用注解的方式可以不用上面那一步,但是只能获取到类的全路径,或者类<br>
>有的时候我们喜欢使用 策略+配置 来取代 if...else,这种情况就可以使用注解的方式来取代对应的配置文件.<br>
### 如何使用?
>这里举个常用的多态取代多分支的例子<br>
<pre>
	class TestMain {
		public static void main(String[] args) {
			// 这里我们可以让用户输入信息,然后通过信息调用对应类
			// 如果我们不用配置文件,我们的代码就如下
			String input = "用户输入的";
			if ("A".equals(input)) {
				System.out.println("A");
			} else if ("B".equals(input)) {
				System.out.println("B");
			}
			// 上面这种方法可以实现效果,但是当需求扩展,用户可以输入C,D,E之类的,上面这串代码也要跟着新增
		}
	}
	// 解决方法就是使用多态 + 配置文件
	interface Test {
		void run();
	}
	class TestA implements Test {
		void run() {
			System.out.println("A");
		}
	}
	class TestB implements Test {
		void run() {
			System.out.println("B");
		}
	}
	class TestMain {
		public static void main(String[] args) {
			String input = "用户输入的";
			// 通过用户输入的从配置文件中获取对应类(这里应有异常和需要强转,请自行操作,伪代码)
			Test test = Class.forName(配置文件获取(input)).newInstance();
			test.run();
			// 上面这种方法较于之前那种方法的区别就是,当需求需要扩展,比如需要新增C,D,E等等类,当前类都不需要改动了
			// 只需要在配置文件里新增一条配置,和新增一个类继承/实现就可以完成扩展了
		}
	}
	// 上面的方法需要配置一下配置文件,我们可以使用注解的方式取而代之.
	// 通过在类上增加注解 @PConfig(config="",name=""),来实现
	// 上面的 config 代表分组,name 代表类在这个组中的名称
	// 同一个组不能有重复 name
	interface TestS {
		void run();
	}
	@PConfig(config = "A", name = "a")
	class TestA implements TestS {
		@Override public void run() {
			System.out.println("A");
		}
	}
	@PConfig(config = "A", name = "b")
	class TestB implements TestS {
		@Override public void run() {
			System.out.println("B");
		}
	}
	// 至于如何获取到指定类,则需要先学习ConfigurationFactory类
</pre>

## shendi.kit.config.ConfigurationFactory
>ConfigurationFactory 是一个配置文件工厂类,里面提供了一些方法让我们非常简单的获取配置文件<br>

### getConfig(String);
>上面说到了在 main.properties 中配置其他配置文件的路径,那么我们如何获取?<br>
>使用 ConfigurationFactory 的 getConfig 方法,是静态的.<br>
>此方法接收一个 String 参数,也就是我们上面在 main.properties 中配置的名称,返回的是对应的 Properties 对象.<br>

>如果使用了上方所说的注解,也可以通过此方法获取,传递的参数为注解的 config 的值,然后可以获取到对应的Properties<br>
>在从 Properties 中获取到类的全路径<br>
>需要注意的是,如果注解与某个配置文件的名 是一样的,则会加载注解的Properties,而不会加载配置文件.<br>
>尽量不要重名<br>
>并且注解的Properties是存在于内存中的.<br>

>我们的main.properties里如果包含中文,可以通过 getConfig 的另一个重载来设置对应编码<br>
>默认编码为 UTF-8,所以不用担心中文问题,当然,这只作用于 main.properties.

### getPConfigAnnoClass(String config,String name)
>在之前说到可以通过注解的方式,获取到指定类,使用的就是此方法了<br>
>参数与注解的两个参数对应,返回值为Class<?>,所以需要自己进行强转.

### getProperty(String config, String name)
>在1.1版本增加了此函数,在之前我们获取Properties配置文件并获取值需要如下代码<br>
>ConfigurationFactroy.getConfig("xxx").getProperty("xx");<br>
>这样显得很长,于是添加了此方法,将 getConfig和getProperty融合.<br>
>返回值为String,并有一重载可以设置编码getProperty(config,name,encode)<br>

# 时间工具包
>shendi.kit.time.TimeUtils<br>
>封装了添加格式,获取格式等操作,对时间的操作封装在内部类 TimeDisposal中.<br>

## Time类
>Time类,用于表示一个时间,一个Time类只能表示一个时间,并且不可改变.<br>
>当一个Time类通过 TimeUtils.createTime 的方式创建则直接拥有字符串,时间戳,Date的表示形式,并且不可改变.<br>

## TimeFormat类
>用于格式化时间的类,比如将字符串格式化成long/Date形式,或者将Date格式化成对应的字符串形式.<br>
>默认提供了几种格式化形式,请参见 TimeUtils.getFormatTime(String) 方法<br>
>也可以自己创建对应的格式,使用 TimeUtils.addTimeFormat(String,String) 方法,有两个参数<br>
>第一个参数为这个格式的名称,第二个为什么样的格式,请参见 java.text.SimpleDateFormat 类

## TimeDisposal类
>用于处理时间,比如获取当前到第二天的距离等.<br>
><b>提供了一个非常好使的方法用于直接获取指定日期--getToTime()</b><br>
>>此方法参数为年,月,日,时分秒毫秒,如果值为-1则代表当前日期<br>
>此类中方法都是静态的,如需了解更多,请参阅JavaDoc.

# 日志工具包
>日志打印类 shendi.kit.log.Log<br>
>
>>用于打印日志和控制日志是否在控制台可见.<br>

>日志管理类 shendi.kit.log.LogManager<br>
>
>>用于获取日志

## Log类
有以下几种级别的日志
<ol>
	<li>调试日志 Log.printDebug();</li>
	<li>普通日志 Log.print();</li>
	<li>警报日志 Log.printAlarm();</li>
	<li>异常日志 Log.printExcept();</li>
	<li>错误日志 Log.printErr();</li>
</ol>

使用 setIsLog(false) 来隐藏日志在控制台的显示<br>
对于某种级别的日志也提供了对应的隐藏方法,例如 setIsLogDebug,setIsLogInfo...<br>

支持格式化输出日志,参考 printf 函数,例如日志内容为12345
>Log.print("1%s345", "2");

## 日志缓存
使用 Log 类进行输出则是没有缓存的,对于需要很详细的日志时(debug),则性能会比较低下,于是提供了缓存类来解决此问题<br>

shendi.kit.log.Alog抽象类,所有日志缓存的父类<br>

有一个子类 DefaultLog,其中实现了一些参数的设置,简化了扩展<br>

DefaultLog也有一个子类 DebugLog 类,使用如下(代码取自 shendi.kit.test.TestDebugLog)
```java
DebugLog log = new DebugLog("测试调试级日志");
		
log.log("Start-开始");

log.log("1. Start get time-开始获取时间");
long time = System.currentTimeMillis();
log.log("Time is %s", TimeUtils.getFormatTime().getString(time));

log.log("2. Time is ok-获取到的时间是否正确");
if (time < 1000000) {
    log.log("Time no ok, < 1000000");
}
log.log("Time ok, > 1000000");

log.log("End-结束");

// 将日志输出并保存到文件中
log.commit();
// close函数本质就是调用了commit函数,出现仅仅为了简化写法,支持try...with...resource
log.close();

// 一般情况下为了避免异常都会使用try,为了避免日志没有commit,所以出现了close函数
// 使用方式可以如下,可以不用编写提交操作(commit)
try (DebugLog tlog = new DebugLog("测试close")) {
    tlog.log("Start-开始");

    tlog.log("1. Start get time-开始获取时间");
    time = System.currentTimeMillis();
    tlog.log("Time is %s", TimeUtils.getFormatTime().getString(time));

    throw new RuntimeException("假设发生异常,日志也会输出并保存到文件中");
//	log.log("2. Time is ok-获取到的时间是否正确");
//	if (time < 1000000) {
//		log.log("Time no ok, < 1000000");
//	}
//	log.log("Time ok, > 1000000");
//	
//	log.log("End-结束");
}
```



使用方法为,创建对应对象,并给此次操作命名

>一般一个类对应一个对象,有对应缓存池,可以处理并发操作,所以不要在局部函数中创建.

然后使用对象的 log 函数进行日志打印(支持格式化输出)<br>
最后使用对象的 commit 函数完成此次操作,如果不使用,则缓存不能被刷新到硬盘中,将会造成内存溢出

### 日志文件
存在于项目根目录的 logs 文件夹下

如果是 Web 项目则为项目的资源路径(WebContent)的logs目录下.



## 数据日志类

shendi.kit.log.data.DataLog

> kit 1.1新增，用于格外的日志数据持久化

功能与 Log 类相似，不同的是可以给日志命名，且会在格外的文件夹中存储

```java
public class TestDataLog {
	public static void main(String[] args) {	
		DataLog dl = new DataLog("给日志文件夹命名,文件夹会保存在项目根目录");
		dl.log("日志信息");
	}
}
```



# 加密工具包
## 加密工厂 shendi.kit.encrypt.EncryptFactory
>通过加密工厂获取对应加密算法类.<br>
>目前提供了两种加密算法,加一算法(速度快,简单,易破)和密码加密算法<br>
>
>>加一算法 EncryptFactory.getEncrypt(EncryptFactory.ADD_ONE);<br>

### 密码加密算法
>通过自己提供的密码来对数据进行操作<br>
>如果没有设置密码,则使用默认的密码<br>
>
>>获取密码加密算法类 EncryptFactory.getEncrypt(EncryptFactory.PWD);<br>

>需要设置密码可以使用 EncryptFactory.getPwdEncrypt(密码) 来设置并获取<br>
>
>>设置后,下次可直接通过 EncryptFactory.getEncrypt(EncryptFactory.PWD); 来获取已经设置密码的类.<br>

### 双密码加密算法
> 同上,更加安全,当数据量大时采用多线程处理<br>
> 使用 EncryptFactory.getTwoPwdEncrypt(密码1,密码2) 来设置并获取

### 求和取余加密算法(不可逆)
>使用 EncryptFactory.getEncrypt(EncryptFactory.SUM_REMAINDER); 来获取

### 添加自己的加密算法到工厂
>新建一个类实现 shendi.kit.encrypt.Encrypt 接口,并使用 EncryptFactory.addEncrypt(获取时的名,新建的加密算法类); 来进行添加

#### 使用注解的方式添加加密算法到加密工厂
>在类上使用 @EncryptAnno 注解,此类需要实现 Encrypt 接口<br>
<pre>
	@EncryptAnno("Test")
	class TestEncrypt implements Encrypt {
		...
	}

	class Test {
		main {
			EncryptFactory.getEncrypt("A");
		}
	}
</pre>

## 加密工具类 shendi.kit.encrypt.EncryptUtils
>这里提供了一个工具类来很简便的对文件进行操作<br>
>通过 EncryptUtils.encryptFiles() 来对指定文件/文件夹进行加密<br>
>如果是对文件夹进行操作,则会将此文件下所有的文件等进行加密,默认加密后的保存路径为当前加密路径的 /Shendi_Encrypt 下<br>
>如果是对文件进行操作,默认保存为 /Shendi_Encrypt_文件名<br>
>也可以自己指定保存的路径,请参阅此类的文档<br>
>同样,也提供了解密的方法 EncryptUtils.decodeFiles()<br>

### 简化加密工厂类的操作
>我们加密一串数据的通常代码形式是这样的<br>
>
>>EncryptFactory.getEncrypt(EncryptFactory.ADD_ONE).encrypt(str.getBytes());<br>

>代码有点过于长了,这不是我的预期结果,于是我在加密工具类中新增了几个方法用于简化操作

#### EncryptUtils.encrypt(name, data);
>通过传递加密方法的名称和要加密的数据就可以得到加密后的数据了<br>
>name 从工厂中获取<br>
>data 是 Object 类型的,所以不用在像上面的例子一样将字符串转换字节在传递了<br>
>返回类型是字节数组

#### EncryptUtils.encryptRS(name, data);
>与 encrypt 不同的是,返回的类型为字符串<br>
>有的时候我们加密后希望得到的是一个字符串类型的数据就可以使用此方法<br>

#### EncryptUtils.decode(name, data);
#### EncryptUtils.decodeRS(name, data);
>与加密的两个方法对应

#### 使用实例
<pre>
String rs = EncryptUtils.encryptRS(EncryptFactory.ADD_ONE, "hello,world");
System.out.println(rs);
</pre>

# 爬虫工具包
> 目前提供微量功能,后续会对此进行扩展<br>
> 对 HTTP 数据获取可参考下方的[HTTP工具类](#HttpUtil)<br>

### 获取网页数据
> 使用 shendi.kit.reptile.Reptile<br>
> 仅需一行代码,可以获取一个 HTML 页面内容<br>
> 代码如下:<br>
>
> > String data = Reptile.index("www.baidu.com");<br>

> 如果想设置请求类型可以在后方加上<br>
>
> > String data = Reptile.index("www.baidu.com", "POST");<br>

### 两行代码获取所有的a标签
> 同样也是使用 Reptile 类<br>
> 通过 List<Element> getElements(name, data) 方法来解析数据获取对应标签<br>
> 例如 获取页面中所有的 a 标签<br>
>> String data = Reptile.index("www.baidu.com");<br>
>> List<Element> e = Reptile.getElements("a", data);<br>

> 当然不只局限与 a 标签,可以自行设置<br>

### 元素标签
> shendi.kit.reptile.Element 代表一个元素<br>
> 其中有如下方法<br>
> String attr(name); 				获取当前元素的对应属性<br>
> String html(); 					当前元素的元素内容<br>
> Set<String> attrNames();			获取所有元素的名称<br>
> HashMap<String, String> attrs()	获取所有元素<br>

# JSON工具包
>位于 shendi.kit.format.json 下<br>
>SK 1.1新增<br>

### JSONObject
>Json对象,格式如下<br>
>
>>{key:value,key:value}<br>

>注: 目前还未完善,key和value都不能使用转义字符,并且value不能为JSONObject或JSONArray<br>
>并且只能取值<br>
><br>
>直接通过字符串创建JSONObject对象<br>
>创建完后可以通过 isJson() 来获取当前JSON是否没有错误<br>
>通过 getString(key) 来获取对应值,其中也可以使用getInt,getDouble等.以及has用于判断是否存在<br>
>通过 convert(Class) 来将 json 转化为对应类,转化的对象数据会自行填充

# id工具包
>位于 shendi.kit.id 下<br>
>SK 1.1新增<br>

### SnowFlake
>雪花算法,生成唯一id,可用于分布式架构,高并发等.<br>
>雪花算法为 Twitter 开源的分布式 id 生成算法<br>
>其核心思想就是: 使用一个 64 位的数字作为全局唯一id.在分布式系统中的应用十分广泛.<br>
>64位,默认配置为,第一位为0(正数),41位表示时间戳,5位机房id,5位机器id,12位序列号

#### 构造方法
>(); 默认构造,使用默认配置<br>
>(int, int); 通过指定的机房id和机器id<br>
>(int tBitNum, int gBitNum, int hBitNum, int sBitNum, int groupId, int hostId);
>
>>自定义设置,通过指定的时间戳位,机房id位,机器id位,序列号位,机房id,机器id创建.

#### 使用
>在创建对象后,通过 spawn() 方法来生成id<br>
<pre>
    SnowFlake snow = new SnowFlake();
    long id = snow.spawn();
</pre>
>此算法生成的id够用70年,默认初始时间戳为2020-12-17(我完成这个类的日期)<br>
>也可以通过 setStartTime(long) 来设置初始时间戳,请勿在使用时设置,并保证设置的值为常量<br>
<pre>
    设置初始时间戳示例
    long t = System.currentTimeMillis();
    t -> 时间戳,例如为 11111111111
    -------------
    SnowFlake snow = new SnowFlake();
    snow.setStartTime(11111111111);
    long id = snow.spawn();
</pre>

# HTTP工具包
shendi.kit.net.http<br>
SK 1.1 新增

#### 工具类
shendi.kit.net.http.HttpUtil

<pre>
	// 首先创建对象,有以下几个重载,具体请参考 API 文档
	/* 
		HttpUtil([String]host),用主机名创建,其余的可以在创建后进行设置
			(host, [int]port),主机名+端口创建
			(host, [String]reqType),主机名+请求类型创建
			(host, port, [String]reqType),主机名+端口+请求类型
			(host, port, [byte[]]data) 将请求类型更换成了一个http的数据,通常此构造用于将获取的http数据发送给服务器使用
	*/
	// host参数可以携带端口等信息,与浏览器url对应
	HttpUtil http = new HttpUtil("http://localhost:8080", "POST");
	// 可以设置一些请求头 端口等信息,端口默认80(setPort)
	http.setReqHead("req", "hh");
	// 然后可以直接获取到数据,例如,获取当前http的全部数据(包含协议头,响应头响应体)
	System.out.print(new String(http.getRespData()));
	// 添加请求参数
	http.addParameter("a", "b");
	// 设置请求参数
	http.setParameters("a=b&c=d");
	// 最终要使用 send 方法来完成请求,注意,设置操作必须在send()之前使用.
	http.sned();
</pre>

#### 响应数据处理接口
shendi.kit.net.http.HttpDataDispose

实现此接口来用以给 HttpUtil 设置处理函数.

包含 boolean dispose(byte[] data) 函数,当有响应数据时会被调用.



#### 文件下载
当我们使用http的方式下载文件时,基础用法已经满足不了需求
>只有所有数据都读取完成时才会完成send(),这时会程序会卡住,当下载的文件过大时则会遇到内存溢出的问题

这时我们需要自行处理响应,于是需要使用到 setDispose() 来设置处理方法

<pre>
	// 创建对象
	HttpUtil http = new HttpUtil("localhost/down");
	/*
		设置响应处理,列举三种使用方式
		HttpDataDispose dispose = new HttpDataDispose() {
			@Override
			public boolean dispose(byte[] data) {
				// 处理数据,data为接收到的数据
				// 需要注意的是,此函数为处理数据函数,当接收完一定大小的响应数据后就会调用此函数来进行下一步操作,所以此函数会被调用多次(根据响应数据大小).
				// return false 代表处理还未完成,将继续读取后续数据
				// return true 代表提前结束响应数据处理,即使还有后续数据也将进行丢弃
				// 当处理数据遇到问题时,应return true来终止此次响应处理.
				return false;
			}
		};
		http.setDispose(dispose);
		// 第二种
		http.setDispose(new HttpDataDispose() {
			@Override
			public boolean dispose(byte[] data) {
				return false;
			}
		});
		// 第三种,lambda表达式
		http.setDispose((data) -> {
			return false;
		});
	这里使用最简洁的lambda表达式
	*/
	// 需要注意的是,当使用了 setDispose 方法设置处理响应,则此 HttpUtil 对象的 respBody和respBodyData将为空(这样做是为了节省开销)
	OutputStream output = new FileOutputStream("C:/1.txt");
	http.setDispose((data) -> {
		try {
			output.write(data);
			output.flush();
		} catch (Exception e) {
			return true;
		}
		return false;
	});
	// 通常我们还需要自行设置超时时间,0为不超时
	http.setTimeout(0);
	// 在 send 的时候可能会发生一些错误,最常见的有 Connection reset
	// 这时可以出错后再次执行一次
	try {
		http.send();
	} catch (Exception e) {
		try {
			http.send();
		} catch (Exception e) {
			// exception dispose
		}
	}
	output.close();
</pre>

#### 重定向与转发
当页面返回结果为重定向内容,我们想要的结果大多都是重定向后的数据,这时需要处理重定向的方法<br>
当我们访问的网页需要登录才能查看,需要保存session/token等信息时,这时需要使用原有的HttpUtil对象<br>
HttpUtil提供了 redirect(url) 函数用以进行重定向/转发

<pre>
	/*
		对于重定向,也就是上面说到的那种情况,可以使用HttpUtil的 setRedirect(true);
		设置过后,当我们执行send()函数,页面需要重定向则会自行重定向,最后的结果为重定向的结果
		有的时候会遇到重定向后又一个重定向的情况(可能无限重定向),则可通过设置最大重定向次数来进行限制
		setRedirectMaxSize(int) 重定向的最大次数,默认为5
		setRedirect 内部实现使用 redirect 函数.
	*/
	HttpUtil http = new HttpUtil("localhost/redirect");
	http.setRedirect(true);
	try {
		http.send();
	} catch (Exception e) {
	}
	// 获取到的结果为重定向后的结果
	System.out.println(http.getRespBody());
	/*
		对于转发,使用对象的 redirect 方法,与重定向一致,也可使用setRedirectMaxSize(int)设置转发次数
	*/
	http.redirect("localhost/index");
	/******************
	转发也可以自行重新send,redirect会保留请求信息并重新请求,拥有转发的效果,重定向默认也使用redirect函数.
	*******************/
</pre>

# 工具类
#### StreamUtils
>流处理工具类<br>
>下面演示此类的少量方法,更多请直接参考此类<br>
<pre>
	// 读取一行,String readLine(InputStream);
	String line = StreamUtils.readLine(input);
	// 读取一行,字节形式 bybte[] readLineRByte(InputStream)
	byte[] bLine = StreamUtils.readLineRByte(input);
	// 读取数据直到指定结尾,byte[] readByEnd(InputStream, byte[]);,其中第二个 参数是要指定的结尾
	byte[] data = StreamUtils.readByEnd(input, "\n".getBytes());
	// 读取一个文件的数据,byte[] getFile(String)或getFile(File)
	byte[] fData = StreamUtils.getFile("C:/1.txt");
</pre>

#### SKClassLoader
>类加载器<br>
>用于将外部class文件定义可使用的类

#### HttpUtil
>Http工具类<br>
>在 SK 1.1 中,此类被移至 shendi.kit.net.http 包中,文档也移动,请参考 [HTTP工具](#HTTP工具包)

#### Math
数学工具类

SK 1.1中新增
```java
// 单位转换,如果后两参数传递的浮点型则返回的结果也带小数
String[] names = {"厘米", "分米", "米"};
// 第一个参数为单位,从小到大,第二个参数为进制,第三个为实际数值
String s = Math.unitConvert(names, 10, 10);
s == "1分米"

String s = Math.unitConvert(names, 10, 1011);
s == "10米 1分米 1厘米"
```



#### IsNullUtil

>判空工具类<br>
>SK 1.1中新增

<pre>
通常在写函数的时候会有很多参数
首先会对其进行判断
例如
if (account == null || "".equals(account)) {}

当参数一多,就要写很长的代码,或者重复很多次上述操作
于是此类就这样诞生了
对于上述操作,判断 null 和 "" 为空的
可以直接通过 {@link #strsIsNull(String...)} 来判断
例如
String account = null;
String password = "hello";
IsNullUtil.strsIsNull(account, password)
== true

account = "world";
IsNullUtil.strsIsNull(account, password)
== false

当然,也可以自己设定判空条件,使用 {@link #isNull(Object[], Object...)} 函数
在写 Java Web 项目通常参数会为 "null"
if (IsNullUtil.isNull(new String[] {"", "null"}, account, password)) {
	...
}
有时我们只需要判断是否为 null,所以提供了此函数的重载 isNull(Object...)
因为有此重载,则此函数传递的第一个参数不能为数组,否则将会被解析成 isNull(Object[], Object...)
此函数等价于调用 isNull(null, Object...);
</pre>



#### ByteUtil

字节工具类

SK 1.1中新增

```java
// 将字节数组插入到另一个字节数组中
// ByteUtil.insert(byte[], int, byte[], int, int);
// 参数分别为,字节数组,数组的开始偏移,被插入的字节数组,插入的位置,插入多少个
byte[] source = {1, 2, 3};
byte[] data = {4, 5};
byte[] result = ByteUtil.insert(data, 0, source, source.length, data.length);
result == {1, 2, 3, 4, 5};
source = {1, 3, 4};
data = {2};
result = ByteUtil.insert(data, 0, source, 1, data.length);
result == {1, 2, 3, 4};

// 查找指定字节数组在字节数组中第一次出现的位置
// ByteUtil.indexOf(byte[], String);
// ByteUtil.indexOf(byte[], byte[]);
byte[] data = "hello,world".getBytes();
int result = indexOf(data, ",".getBytes());
result -> 5
    
result = indexOf(data, "llo");
result -> 2

result = indexOf(data, "what are you doing?");
result -> -1

// 截取字节数组
// ByteUtil.subByte(byte[], int)
// ByteUtil.subByte(byte[], int, int)
byte[] data = "hello,world".getBytes();
byte[] result = subByte(data, 2);
result -> "llo,world"
    
byte[] result = subByte(data, 2, data.length);
result -> "llo,world"

// 将字节数组连接成新数组
// ByteUtil.concat(byte[]...)
byte[] data1 = "hello".getBytes();
byte[] data2 = ",world".getBytes();
byte[] result = concat(data1, data2);
result -> "hello,world"

result = concat("hello".getBytes(), ",Java.".getBytes(), "I'm".getBytes(), " Shendi".getBytes());
result -> "hello,Java.I'm Shendi"

data1 = "I like ".getBytes();  
data2 = new byte[0];
data3 = "this kit!".getBytes();
result = concat(data1, data2, data3);
result -> "I like this kit!"
    
// 将字节数组连接成新数组并插入分隔数组
// ByteUtil.concatSplit(byte[], byte[]...)
byte[] split = ",".getBytes();
byte[] data1 = "hello".getBytes();
byte[] data2 = "world".getBytes();
byte[] result = concatSplit(split, data1, data2);
result -> "hello,world"

result = concatSplit(split, "red".getBytes(), "green".getBytes(), "blue".getBytes(), "shendi".getBytes());
result -> "red,green,blue,shendi"

result = concatStr(split, "hello, world".getBytes());
result -> "hello, world"

data1 = "I like".getBytes();  
data2 = new byte[0];
data3 = "this kit!".getBytes();
result = concatSplit(split, data1, data2, data3);
result -> "I like,this kit!"
    
// 将字节数组按照指定字节数组进行分割
// ByteUtil.split(byte[], byte[])
// ByteUtil.split(byte[], byte[], int)
byte[] data = "hello,world".getBytes();
byte[] split = ",".getBytes();
byte[] result = split(data, split);
result -> ["hello", "world"]

data = "red,green,blue,shendi".getBytes();
result = split(data, split);
result -> ["red", "green", "blue", "shendi"]

data = "I like,,this kit!".getBytes();  
result = split(data, split);
result -> ["I like", "this kit!"]

data = "123,456,789,012".getBytes();
result = split(data, split, 3)
result -> ["123", "456", "789"]
```



#### BitUtil

>位工具类<br>
>SK 1.1中新增

<pre>
/**
 * 获取指定数值占多少位,数字符号位占一位.
 * int sizeOf(long);
 * sizeOf(0) -> 2
 * sizeOf(1) -> 2
 * sizeOf(2) -> 3
 */
int bitNum = BitUtil.sizeOf(1);
/**
 * 获取指定位数的最大十进制值,不带符号位
 * long bitMax(int);
 * bitMax(0) -> 0
 * bitMax(1) -> 1
 * bitMax(2) -> 3
 * bitMax(3) -> 7
 */
 int max = BitUtil.bitMax(1);
</pre>


#### FileUtil

>文件工具类<br>
>SK 1.1中新增

<pre>
// 使用指定数据更新/创建指定文件,update(String, byte[])
FileUtil.update("C:/1.txt", "hello".getBytes());
// 同上,参数一为相对路径,updateByPro(String, byte[])
// 例如将项目下的1.txt内容更改
FileUtil.updateByPro("/1.txt", "hello.getBytes());
</pre>
