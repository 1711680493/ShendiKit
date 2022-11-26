# Shendi Kit
**author：** Shendi

**版本：** 1.1.0

**最后更改时间：** 2022-03-23



[引导页面](https://1711680493.github.io)

Java工具包,纯Java制作,使用JDK8

以前版本可在分支中找到



# 文档及测试样例
[在线文档 v1.1](https://1711680493.github.io/doc/SK-1.1)

样例在源码的 shendi.kit.test 包下





# 目录
## [版本变化](#SK-版本变化)
[1.0 Start](#v-10)
[1.1 Small Kit](#v-11)



## [SK 配置](#开始配置)
[SK 配置文件地址](#配置文件地址)

[SK 默认配置文件](#默认配置文件)

[注解扫描配置](#anno_scan.shendi注解扫描配置)



## [控制台](#控制台模块)
[给自己的程序增加一个控制台模块](#给自己的程序增加一个控制台模块)

[添加命令](#添加命令)

[命令的使用](#命令的使用)

[命令行控制台](#命令行控制台)

[窗体控制台](#窗体控制台)

[自定义控制台](#自定义控制台)

[内置命令](#内置命令)

>[execute命令](#execute命令)
>
>[扩展内置命令](#扩展内置命令)



## [配置文件](#非常简单的使用-properties-配置文件)

## [时间工具](#时间工具包)

## [日志工具](#日志工具包)



## [路径工具](#Path工具包)



## [加密工具](#加密工具包)
## [爬虫工具](#爬虫工具包)
>此包待完善,目前只提供了一些简单地功能

[获取网页数据](#获取网页数据)

[两行代码获取所有的a标签](#两行代码获取所有的a标签)

[元素标签](#元素标签)





## [缓存工具](#缓存工具-Cache)

>待完善，目前只提供磁盘缓存，文件读写功能



## [JSON工具](#JSON工具包)
>待完善

[JSONObject](#JSONObject)



## [id工具](#id工具包)
[雪花算法](#SnowFlake)



## [HTTP工具](#HTTP工具包)
[工具类](#工具类)

[响应数据处理接口](#响应数据处理接口)

[文件下载(处理响应体)](#文件下载)

[重定向/转发(保留session等)](#重定向与转发)

[处理请求-http服务器](#处理请求)

[处理响应](#处理响应)



## [线程工具](#线程管理器)



## [简洁实用工具包-shendi.kit.util](#工具类)
>统一在 shendi.kit.util 包下

[流处理工具类](#StreamUtils)

[自定义类加载器](#SKClassLoader)

[HTTP工具类](#HttpUtil)

> 已移动至 shendi.kit.net.http, 具体请参考文档[HTTP工具](#HTTP工具包)



[数学工具类](#Math)

[判空工具类](#IsNullUtil)

[字节工具类](#ByteUtil)

[位工具类](#BitUtil)

[文件工具类](#FileUtil)



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
			<li>
                ConfigurationFactory 类新增函数 getProperty 简化获取配置文件操作<br>
				函数支持参数注入,默认使用的编码为UTF-8
            </li>
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
			<li>新增对HttpUtil 1.1的chunket处理</li>
			<li>在之前POST请求无法带请求参数,现在可以使用addParameter(key,value)或setParameters(param)来直接设置</li>
			<li>增加对HEAD类型支持,以及可从host可携带端口,新增构造 (host, type)</li>
			<li>包内新增HttpDataDispose接口用以处理http响应数据(比如文件下载,具体请参考文档)</li>
			<li>支持重定向与转发</li>
            <li>增加处理请求与响应的函数,可通过函数直接解析http请求/响应数据</li>
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
    <li>新增shendi.kit.thread包,用于处理线程</li>
    <li>新增shendi.kit.cache包,缓存工具</li>
	<li>TimeUtils改进,将Time,TimeFormat从内部类提取,且修复已知BUG</li>
	<li>StreamUtils新增 readAllByte(input) 函数,用以读取输入流中所有的数据</li>
</ol>







# 开始配置（使用注解则此步骤必须）

如果使用到注解，配置工具，则首先需要进行此步骤，未使用到则可忽略此部分



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
	<li>注册/销毁控制台(目前只提供了几种控制台,下方会一一列举,后续会增加(当然你也可以自己创建,如何创建请看文档))
		<ul>
			<li>通过控制台对象的 register() 函数来注册</li>
            <li>通过控制台对象的 destroy() 函数来进行销毁(销毁后还可以通过register来注册,所以一个对象可以重复使用)</li>
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

Properties配置文件工具

当配置文件修改后，获取到的数据为修改后的数据



可以使用注解的方式标记一个类，从配置文件里获取到对应类和类的全路径

(本人用策略模式较多，所以就封装了这样的功能)



## 主配置文件



首先为了知道配置文件存放位置，需要创建一个主配置文件，存放于项目根目录的 files 文件夹下

文件命名为 **main.properties**

> JavaWeb 项目放到WebContent的files文件夹下



其中，主配置文件内内容为

```properties
# 配置文件名称(自己命名,获取时候用到)=配置文件相对地址(相对于项目根目录,JavaWeb项目则为WebContent下)
# 例如 项目结构为
# |-项目
#	|-files
#		|-main.properties
#		|-config.properties
# 其中 config.properties 为需要使用的,命名为config,在 main.properties 添加如下内容即可
config=/files/config.properties
```



## 使用注解的方式
使用注解的方式可以不用上面那一步,但是只能获取到类的全路径,或者类

有的时候我们喜欢使用 策略+配置 来取代 if...else,这种情况就可以使用注解的方式来取代对应的配置文件.



### 如何使用?
这里举个常用的多态取代多分支的例子

```java
class TestMain {
	public static void main(String[] args) {
		// 这里举例实现让用户输入信息,然后通过信息调用对应类
		// 如果不用配置文件,我们的代码一般如下
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
        // 好处是,当需求需要扩展,比如需要新增C,D,E等等类,当前类都不需要改动
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
// 至于如何获取到指定类,则查看ConfigurationFactory类
```



## 配置文件工厂

ConfigurationFactory 是一个配置文件工厂类，简化了配置文件的使用



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



### 获取指定配置文件的指定值，支持参数注入

> SK 1.1 新增



**getProperty() 函数**

简化了1.0获取配置文件的方式，默认编码为 UTF-8，有以下几个重载

```java
(String config, String name)
(String config, String name, String encode)
(String config, String name, Entry<String, String>[] params)
(String config, String name, String encode, Entry<String, String>[] params)

/*
	其中
	config(配置文件名称),name(配置文件内键名),encode(编码),params(注入参数)
	Entry 使用的是 shendi.kit.util.Entry
*/
```



新增此函数可以注入参数，其中语法为 ${注入参数名称}

例如 config.properties 配置文件内容如下

```properties
welcome=你好，我是 ${name}
```



使用以上几个重载获取分别为

```java
ConfigurationFactory.getProperty("config", "welcome");
->你好，我是 ${name}

ConfigurationFactory.getProperty("config", "welcome", "UTF-8");
->你好，我是 ${name}

ConfigurationFactory.getProperty("config", "welcome", new Entry[]{new Entry<String, String>("welcome", "Shendi")});
->你好，我是 Shendi

ConfigurationFactory.getProperty("config", "welcome", "UTF-8", new Entry[]{new Entry<String, String>("welcome", "Test")});
->你好，我是 Test
```

需要注意的是，${} 花括号中间的为命名名称，包括空格等



### 配置文件是否修改

> SK 1.1 新增



在使用配置文件的时候，对于一些需要频繁操作处理的数据，通过缓存的方式能够更加节省开销

于是提供了判断配置文件是否更改了的函数，当更改后再去刷新缓存即可

```java
/*
	boolean ConfigurationFactory.configIsChange(String name);
	当修改后返回 true, 否则 false
*/
// 例如配置文件 config 中有一个 key 为某 token 超时时间,我们获取到的为字符串形式,每一次操作都需要转成 int
// 则可以如下使用
/*
	目录结构
	Project
		|-files
			|-main.properties
			|-config.properties
	
	配置文件内容
	main.properties
		config=/files/config.properties
	config.properties
		timeout=1000
*/
public static final String CONFIG = "config";
// 初始化
long timeout = Integer.parseInt(ConfigurationFactory.getProperty(CONFIG, "timeout"));

public long getTimeout() {
   	// 当配置文件有修改再去更改变量
    if (ConfigurationFactory.configIsChange(CONFIG)) {
        timeout = Integer.parseInt(ConfigurationFactory.getProperty(CONFIG, "timeout"));
    }
	return timeout;
}
```





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

日志打印类 shendi.kit.log.Log

> 用于打印日志和控制日志是否在控制台可见



日志管理类 shendi.kit.log.LogManager

>用于获取日志



## Log类
有以下几种级别的日志
<ol>
	<li>调试日志 Log.printDebug();</li>
	<li>普通日志 Log.print();</li>
	<li>警报日志 Log.printAlarm();</li>
	<li>异常日志 Log.printExcept();</li>
	<li>错误日志 Log.printErr();</li>
</ol>


使用 setIsLog(false) 来隐藏日志在控制台的显示

对于某种级别的日志也提供了对应的隐藏方法,例如 setIsLogDebug,setIsLogInfo...

支持格式化输出日志,参考 printf 函数,例如日志内容为12345

```java
Log.print("1%s345", "2");
```



## 日志缓存
使用 Log 类进行输出则是没有缓存的,对于需要很详细的日志时(debug),则性能会比较低下,于是提供了缓存类来解决此问题

shendi.kit.log.Alog抽象类,所有日志缓存的父类

有一个子类 DefaultLog,其中实现了一些参数的设置,简化了扩展

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



然后使用对象的 log 函数进行日志打印(支持格式化输出)

最后使用对象的 commit 函数完成此次操作,如果不使用,则缓存不能被刷新到硬盘中,将会造成内存溢出



## 调试日志

> SK 1.1 新增

调试日志用于测试环境，当生产环境时，调试日志将不做任何操作

基于 [日志缓存](#日志缓存)

shendi.kit.log.DebugLog



```java
public class TestDebugLog {
	
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
```





## 日志文件
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





# Path工具包

位于 shendi.kit.path 包

SK 1.0时编写，于1.1优化，目前支持获取项目路径和源路径（class）

这里使用 PathFactory 做个简单的演示

```java
// 获取项目根目录下的 test.txt 文件,例如项目根目录在 D:/Project
String path = PathFactory.getPath(PathFactory.PROJECT, "/test.txt");
// path = D:/Project/test.txt
// 打成jar包后 jar包位置则为项目根路径
// 如果是Java Web项目 - Tomcat,根目录在WebContent下,具体参考shendi.kit.path.ProjectPath

// 获取项目源路径（bin目录）
path = PathFactory.getPath(PathFactory.RESOURCE, "/test.txt");
// path = D:/Project/bin/test.txt
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



# 缓存工具 Cache

位于 shendi.kit.cache 下

**SK 1.1新增**





## Cache 接口

缓存工具的所有类都实现此接口，接口拥有以下定义

* init()
* write(byte[])
* rewrite(byte[])
* byte[] readAll()
* clear()





## DiskCache 磁盘缓存

将缓存数据写到硬盘上，从缓存中读取

拥有一个构造

* DiskCache(String name)



其中 name 表示此缓存名称，也表示文件名称

> 文件位置保存在项目根目录的cache文件夹下，例如传递的 name为 test，那么文件地址为
>
> 项目位置/cache/test，关于项目位置，可以参考 PathFactory



在实例化时，会创建对应的目录及文件（如果文件不存在），并且执行 init() 从文件中拿到数据放到 data 中

可通过 getData() 函数获取，虽然此类可以直接使用，但一般都不会直接操作字节，建议编写此类的子类后更加方便使用（关于字符串可参考下方缓存示例实现 - 基于字符串（JSONObject））





## DiskCacheFactory

磁盘缓存工厂，磁盘缓存默认是不关闭流的，这样可以频繁读写，但是当读写并不是很频繁的时候，不关闭流就会多占用一些资源了



使用磁盘缓存工厂创建磁盘缓存，将会监听创建的磁盘缓存，两秒没有读写则关闭流，需要使用时再自动开启

```java
DiskCache cache = DiskCacheFactory.create("同DiskCache构造函数");
```







## 磁盘缓存子类示例

实现一个JSONObject的磁盘缓存类，基于 fastjson，可根据需求自行更改

```java
public class JSONObjectCache extends DiskCache {

	private JSONObject json;
	
	public JSONObjectCache(String name) {
		super(name);
	}
	
	@Override
	public void initAfter() {
		// data 为读取到的文件字节
		if (data.length > 0) json = JSONObject.parseObject(new String(data));
		else json = new JSONObject();
	}
	
	public Object put(String key, Object value) {
		Object put = json.put(key, value);
		rewrite(json.toJSONString().getBytes());
		return put;
	}
	public Object remove(Object key) {
		Object remove = json.remove(key);
		rewrite(json.toJSONString().getBytes());
		return remove;
	}
	public boolean remove(Object key, Object value) {
		boolean remove = json.remove(key, value);
		rewrite(json.toJSONString().getBytes());
		return remove;
	}
	
	public Object get(Object key) {
		return json.get(key);
	}
	public String getString(String key) {
		return json.getString(key);
	}
	public JSONArray getJSONArray(String key) {
		return json.getJSONArray(key);
	}
	public JSONObject getJSONObject(String key) {
		return json.getJSONObject(key);
	}
	public String toJSONString() {
		return json.toJSONString();
	}
    
    public boolean containsKey(Object key) {
		return json.containsKey(key);
	}
	public long getIntValue(String key) {
		return json.getIntValue(key);
	}
	public long getLongValue(String key) {
		return json.getLongValue(key);
	}
    
    /**
	 * 将当前数据写入磁盘,当数据改变但此JSONObjectCache未执行任何函数时需要使用此函数来保存数据.
	 */
	public void save() {
		rewrite(json.toJSONString().getBytes());
	}
	
	public JSONObject getJson() {
		return json;
	}
	
}
```



使用

```java
public class TestDiskCache {

	public static void main(String[] args) throws IOException, InterruptedException {
		
		JSONObjectCache cache = new JSONObjectCache("test.json");
		
		// 添加进磁盘工厂,使得根据情况自动关流
		DiskCacheFactory.add(cache);
		
		JSONArray users = new JSONArray();
		JSONObject user = new JSONObject();
		user.put("id", "1");
		user.put("name", "Shendi");
		user.put("test", "ok");
		
		users.add(user);
		
		cache.put("users", users);
		
		JSONArray arr = cache.getJSONArray("users");
		System.out.println(arr.get(0));
	}
	
}
```



输出结果如下

```json
{"test":"ok","name":"Shendi","id":"1"}
```



代码更改如下继续执行

```java
public class TestDiskCache {

	public static void main(String[] args) throws IOException, InterruptedException {
		
		JSONObjectCache cache = new JSONObjectCache("test.json");
		
		// 添加进磁盘工厂,使得根据情况自动关流
		DiskCacheFactory.add(cache);
		
		/*JSONArray users = new JSONArray();
		JSONObject user = new JSONObject();
		user.put("id", "1");
		user.put("name", "Shendi");
		user.put("test", "ok");
		
		users.add(user);
		
		cache.put("users", users);*/
		
		JSONArray arr = cache.getJSONArray("users");
		System.out.println(arr.get(0));
	}
	
}
```



输出结果如下

```json
{"test":"ok","name":"Shendi","id":"1"}
```



在项目根目录下的 cache/test.json 可以看见文件内容

> 需要注意的是，写入操作只有执行 JSONObjectCache 的一些写入函数才会写入硬盘，例如操作 JSONArray等，需要手动执行 JSONObjectCache 的 save 函数







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



## 工具类
shendi.kit.net.http.HttpUtil

```java
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
// 添加请求参数
http.addParameter("a", "b");
// 设置请求参数
http.setParameters("a=b&c=d");
// 最终要使用 send 方法来完成请求,注意,设置参数等操作必须在send()之前使用.
http.send();
// 然后可以通过 http.getResp 开头的函数获取到各种响应数据
// 例如,获取当前http的全部数据(包含协议头,响应头响应体)
System.out.print(new String(http.getRespData()));
```



## 响应数据处理接口

shendi.kit.net.http.HttpDataDispose

实现此接口来用以给 HttpUtil 设置处理函数.

包含 boolean dispose(byte[] data) 函数,当有响应数据时会被调用.



## 文件下载
当我们使用http的方式下载文件时,基础用法已经满足不了需求
>只有所有数据都读取完成时才会完成send(),这时会程序会卡住,当下载的文件过大时则会遇到内存溢出的问题

这时我们需要自行处理响应,于是需要使用到 setDispose() 来设置处理方法

```java
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
```




## 重定向与转发
当页面返回结果为重定向内容,我们想要的结果大多都是重定向后的数据,这时需要处理重定向的方法<br>
当我们访问的网页需要登录才能查看,需要保存session/token等信息时,这时需要使用原有的HttpUtil对象<br>
HttpUtil提供了 redirect(url) 函数用以进行重定向/转发

```java
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
```



## 处理请求

在 SK1.1 中加入了处理请求的函数，当有HTTP请求数据的字符串或输入流时，则可通过以下函数进行处理

```java
// 提供了无参构造用于只处理请求的情况

// 通过输入流处理请求
boolean disposeReq(InputStream);
// 通过字符串数据处理请求
boolean disposeReq(String);
// 其中处理请求成功(请求有效)则返回 true,否则 false
```



例如编写一个只获取http请求的服务器

```java
ServerSocket server = new ServerSocket(1711);
while (true) {
    try {
        Socket socket = server.accept();
        HttpUtil http = new HttpUtil();
        boolean isOk = http.disposeReq(socket.getInputStream());
        System.out.println(isOk);
        if (isOk) {
            System.out.println("接收到的原封不动的所有数据:");
            System.out.println(new String(http.getData()));

            System.out.println("-------------");
            System.out.println("拼装:");
            System.out.println(http.getReqType() + " " + http.getReqPath() + " " + http.getHttpV());
            System.out.println(http.getReqHeadStr());
            System.out.println(http.getParameters());
            System.out.println("***********************************************");
        }
    } catch (Exception e) { e.printStackTrace(); }
}
```



将以上代码放到main函数运行，打开浏览器，输入 localhost:1711 即可看到浏览器发送的请求数据





## **处理响应**

在 SK1.1 中加入了处理响应的函数，当有HTTP响应数据的字符串或输入流时，则可通过以下函数进行处理

```java
// 提供了无参构造用于只处理响应的情况

// 通过输入流处理响应
disposeResp(InputStream);
// 通过字节数据处理响应
disposeResp(byte[]);
// 因响应数据可能过大或可能是图片数据,所以未提供参数为字符串的函数
```



处理完后通过前缀为 getResp 的函数获取响应内容



```java
// http响应数据
byte[] respData = ...;
// http响应数据流
InputStream input = socket.getInputStream();

HttpUtil http = new HttpUtil();
// 通过响应数据直接解析响应
http.disposeResp(respData);
// 通过响应数据流解析响应,读取超时时间通过socket等进行设置
http.disposeResp(input);

/* 当函数执行成功,则http对象拥有了响应数据,可进行如下操作 */
// 所以响应数据内容(包括分块编码等,原封不动)
byte[] allRespData = http.getRespData();
// 获取响应状态
int state = http.getState();
// 获取响应状态的附加信息,例如 HTTP/1.1 200 OK 这里的OK为附加信息
String stateInfo = httpgetStateInfo();

// 获取所有响应头,字符串形式
String headStr = http.getRespHeadStr();
// 获取所有响应头,Map形式,其中key都为大写形式
Map<String, String> heads = http.getRespHeads();
// 获取指定响应头,例如HOST
String host = http.getRespHead("HOST");

// 获取响应体
String body = http.getRespBody();
```





# 线程管理器

> 此部分诞生是因为本人写程序遇到了一些不能解决的问题，且非代码问题。
>
> 我使用到了 JVisualVM 工具观察到我程序的某个线程状态从正常运行变为了等待（在我程序稳定运行一个月后出现）
>
> 代码内无任何关于线程等待的代码，因为是死循环，所以想到了使用守护线程的方式来监测，当线程等待自动中断让此线程继续运行。



shendi.kit.thread

SK 1.1新增

目前此包仅用于处理线程状态莫名变成等待的情况，后续扩展



```java
// 打开线程管理器
ThreadManager.open();
// 将需要被管理的线程添加进管理器,且设置期望被管理的类型(目前只提供永不等待类型,参考枚举 ThreadType)
Thread t = new Thread(() -> {
    Object obj = new Object();

    try {
        synchronized (obj) {
            // 线程状态变为等待后会被线程管理器中断
            obj.wait();
        }
    } catch (Throwable e) {

    }

    System.out.println("我从等待中出来了");
});
ThreadManager.add("测试线程", t, ThreadType.NO_WATTING);
// 线程管理器是以守护线程的方式运行,所以程序内必须要有一个存活的用户线程,否则将自行结束.
```



# 工具类



## StreamUtils

>流处理工具类<br>
>下面演示此类的少量方法,更多请直接参考此类<br>
```java
// 读取一行,String readLine(InputStream);
String line = StreamUtils.readLine(input);
// 读取一行,字节形式 bybte[] readLineRByte(InputStream)
byte[] bLine = StreamUtils.readLineRByte(input);
/* 读取数据直到指定结尾,byte[] readByEnd(InputStream, byte[]);,其中第二个 参数是要指定的结尾 */
/* 其中支持设置读取的最大数据长度(在网络编程中会接收到一些爬虫等连接,假设一直发送数据则会导致一直读取数据,导致内存占用过高)
 * StreamUtils.readByEnd(input, byte[], int 设置接受数据的最大大小); */
byte[] data = StreamUtils.readByEnd(input, "\n".getBytes());

/* 读取一个文件的数据,byte[] getFile(String)或getFile(File) */
byte[] fData = StreamUtils.getFile("C:/1.txt");

```



## SKClassLoader

>类加载器<br>
>用于将外部class文件定义可使用的类



## HttpUtil

>Http工具类<br>
>在 SK 1.1 中,此类被移至 shendi.kit.net.http 包中,文档也移动,请参考 [HTTP工具](#HTTP工具包)



## Math

数学工具类

SK 1.1中新增



### 单位转换

```java
// 单位转换,如果后两参数传递的浮点型则返回的结果也带小数
String[] names = {"厘米", "分米", "米"};
// 第一个参数为单位,从小到大,第二个参数为进制,第三个为实际数值
String s = Math.unitConvert(names, 10, 10);
s == "1分米"

String s = Math.unitConvert(names, 10, 1011);
s == "10米 1分米 1厘米"
```



### 计算指定字符数的指定最大长度可排列数

```java
// 例如计算指定字符列表可生成的最大随机字符数
// 例如字符0和1, 最大长度2, 可排列为 0,1,00,01,10,11
// 参数1为字符数量,0和1是两个, 参数2为最大长度,两位数就2, 因为计算结果值可能会非常大,所以使用BigDecimal
BigDecimal num = Math.charLenComposeNum(2, 2);
// num == 6

// 计算数字字母(大小写) 32长度可排列的数
String chars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
num = Math.charLenComposeNum(chars.length(), 32);
// num == 2309914571127845629705327490900126861476998063576338098410
```







## IsNullUtil

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



## ByteUtil

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
    
// 字节数组是否以指定数据开头
// ByteUtil.startsWith(byte[], byte[])
// ByteUtil.startsWith(byte[], byte[], int)
byte[] data = {1,2,3,4,5,6,7,1,2,3,4};
byte[] start = {1,2,3};

boolean isStartsWith = ByteUtil.startsWith(data, start);
isStartsWith -> true

isStartsWith = ByteUtil.startsWith(data, start, 0);
isStartsWith -> true

isStartsWith = ByteUtil.startsWith(data, start, 7);
isStartsWith -> true
    
isStartsWith = ByteUtil.startsWith(data, start, -1);
isStartsWith -> false
    
// 字节数组是否以指定数据结尾
// ByteUtil.endsWith(byte[], byte[])
// ByteUtil.endsWith(byte[], int, byte[])
byte[] data = {1,2,3,4,5,6,7,1,2,3,4};
byte[] end = {2,3,4};

boolean isEndWith = ByteUtil.endsWith(data, end);
isEndWith -> true
    
isEndWith = ByteUtil.endsWith(data, data.length, end);
isEndWith -> true
    
isEndWith = ByteUtil.endsWith(data, 4, end);
isEndWith -> true
    
isEndWith = ByteUtil.endsWith(data, -1, end);
isEndWith -> false
```



## BitUtil

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


## FileUtil

>文件工具类<br>
>SK 1.1中新增

<pre>
// 使用指定数据更新/创建指定文件,update(String, byte[])
FileUtil.update("C:/1.txt", "hello".getBytes());
// 同上,参数一为相对路径,updateByPro(String, byte[])
// 例如将项目下的1.txt内容更改
FileUtil.updateByPro("/1.txt", "hello.getBytes());
</pre>
