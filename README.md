 简介
>Project Name:Shendi Kit<br>
>author:Shendi<br>
>version: 1.0<br>
>QQ:1711680493

# 测试样例
>样例在源码的 shendi.kit.test 包下.

# 目录
### [控制台](#控制台模块)<br>
>>[给自己的程序增加一个控制台模块](#给自己的程序增加一个控制台模块)<br>
>>[添加命令](#添加命令)<br>
>>[命令行控制台](#命令行控制台)<br>
>>[窗体控制台](#窗体控制台)<br>
>>[自定义控制台](#自定义控制台)<br>
### [配置文件](#非常简单的使用-properties-配置文件)<br>
### [时间工具](#时间工具包)<br>
### [日志工具](#日志工具包)<br>
### [加密工具](#加密工具包)<br>
### [爬虫工具](#爬虫工具包)<br>
>此包待完善,目前只提供了一些简单地功能<br>
>>[获取网页数据](#获取网页数据)<br>
>>[两行代码获取所有的a标签](#两行代码获取所有的a标签)<br>
>>[元素标签](#元素标签)<br>
### [简洁实用工具包-shendi.kit.util](#工具类)<br>
>>统一在 shendi.kit.util 包下<br>
>>[流处理工具类](#StreamUtils)<br>
>>[自定义类加载器](#SKClassLoader)<br>
>>[HTTP工具类](#HttpUtil)<br>


# 开始配置
>1.首先需要在项目的根目录(web项目在WebContent下,SpringBoot等项目都在根目录)新建一个文件夹为files<br>
>2.在这个文件夹下新建一个文件 anno_scan.shendi(大小写要完全一致)<br>
>3.在此文件中配置使用了当前工具包注解的 jar 包名,格式为如下<br>
>>jar1.jar;jar2.jar;jar3.jar;jar4.jar<br>
>当然为了避免重复,可以加上jar包所在的相对路径,如下<br>
>>/lib/jar1.jar;/lib/jar2.jar<br>
>使用分号分隔,配置完成之后就可以随意使用此工具包的注解了<br>
>根据jar包后缀进行判断,所以如果需要扫描所有jar,配置文件内容可以为 .jar<br>
>>但是这不可避免会出现一些错误,比如被扫描的jar包有一些类有问题等,例如mysql的jdbc驱动...<br>
>注: 当前项目如果打包成jar也需要加入进去<br>
>如果不想扫描,可以将文件内容改为 No(只有N和o这两个字母)

# 控制台模块
>有的时候我们需要给自己的程序添加一个后端控制来增强交互,这时就可以使用此模块<br>
>我自己也有这一需求,所以此模块就这样诞生了.<br><br>
>>会不断完善,后续可能会开发出可以实时修改变量的功能,请期待<br>
>提供了一个测试类 shendi.kit.test.TestConsole <br><br>
## 给自己的程序增加一个控制台模块
>好处就是增强了交互,时刻观察到当前软件状态等<br><br>
>为了使得开发变得简单,我只提供了注解的方式来使用<br><br>
>分为五个步骤<br>
>1.创建类<br>
>2.创建方法/字段<br>
>3.给类添加 @ConsoleAnno 注解<br>
>4.给方法/字段添加 @CommandAnno(name,info) 注解<br>
>5.注册控制台
>>目前只提供了几种控制台,下方会一一列举,后续会增加<br>
>>通过控制台对象的 register() 函数来注册<br>
>>例如: new CommandConsole().register();<br>

## 添加命令
>引入注解 import shendi.kit.annotation.CommandAnno;<br>
>使用 @CommandAnno(name,info) 注解来增加一条命令<br>
>>其中,name为String类型,代表命令的名称,info也是String类型,代表命令的附加信息<br>
>>要求: 只能修饰在字段/方法上<br>
>>>如果是方法,必须是public修饰的,方法返回值必须为java.lang.String,必须无参<br>
>><b>并且拥有命令的类必须使用 @ConsoleAnno 注解</b><br>

## 命令行控制台
>实现类: shendi.kit.console.CommandConsole<br>
>描述: 提供一个可以在命令行控制台进行交互的功能,功能简单的控制台<br>
>自带三个命令 help/reload/exit<br>
>help		显示所有命令<br>
>reload		重新加载命令(可以动态修改)<br>
>exit		退出命令行控制台<br>

## 窗体控制台
>实现类: shendi.kit.console.DeskAppConsole<br>
>描述: 窗体控制台,内嵌命令行控制台,并提供可以设置实时显示命令的方法<br>
><b>窗体控制台与程序是绑定在一起的,当控制台被关闭,程序也会被关闭<b><br>

## 自定义控制台
>如果不满足当前的命令行控制台可以自定义一个,方法如下<br>
>新建一个类继承 shendi.kit.console.Console 类<br>
>实现以下两个方法<br>
>>protected void register(HashMap<String, Command> commands)<br>
>>public void destroy()<br>
>register代表注册,destroy代表销毁<br>
>在register中传递的参数 commands 代表当前已有的所有命令<br>
>>是一个HashMap,键为命令名,值为命令(包含函数/字段),要了解更多可以查阅 shendi.kit.data.Command 类

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


# 时间工具包
>对时间的操作我封装成了一个类 shendi.kit.time.TimeUtils<br>
>此类提供了一个静态方法供获取对象 TimeUtils.getTime();<br>
>通过对象,可以创建时间-shendi.kit.time.Time类,创建时间格式等.<br>
>里面有几个内部类
## Time类
>第一个就是Time类,用于表示一个时间,一个Time类只能表示一个时间,并且不可改变.<br>
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
>>用于打印日志和控制日志是否在控制台可见.<br>
>日志管理类 shendi.kit.log.LogManager<br>
>>用于获取日志
## Log类
>使用方法比较简单,目前有以下三种日志形式<br>
>>普通日志 Log.print();<br>
>>警报日志 Log.printAlarm();<br>
>>错误日志 Log.printErr();<br>
>使用 setIsLog(false) 来隐藏日志在控制台的显示<br>
### 日志文件
>存在于项目根目录的 logs 文件夹下<br>
>如果是 Web 项目则为项目的资源路径(WebContent)的logs目录下.<br>

# 加密工具包
## 加密工厂 shendi.kit.encrypt.EncryptFactory
>通过加密工厂获取对应加密算法类.<br>
>目前提供了两种加密算法,加一算法(速度快,简单,易破)和密码加密算法<br>
>>加一算法 EncryptFactory.getEncrypt(EncryptFactory.ADD_ONE);<br>
### 密码加密算法
>通过自己提供的密码来对数据进行操作<br>
>如果没有设置密码,则使用默认的密码<br>
>>获取密码加密算法类 EncryptFactory.getEncrypt(EncryptFactory.PWD);<br>
>需要设置密码可以使用 EncryptFactory.getPwdEncrypt(密码) 来设置并获取<br>
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
>> String data = Reptile.index("www.baidu.com");<br>
> 如果想设置请求类型可以在后方加上<br>
>> String data = Reptile.index("www.baidu.com", "POST");<br>

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
<pre>
	// 首先创建对象,有四个重载
	// HttpUtil([String]host),(host, [int]port),(host, port, [String]reqType),(host, port, [byte[]]data)
	// 第一个为用主机名创建,其余的可以在创建后进行设置
	// 第二个为主机名+端口创建,第三个为主机名+端口+请求类型
	// 第四个将请求类型更换成了一个http的数据,通常此构造用于将获取的http数据发送给服务器使用
	HttpUtil http = new HttpUtil("shop.shendi.xyz");
	// 可以设置一些请求头 端口等信息,端口默认80(setPort)
	http.setReqHead("req", "hh");
	// 最终要使用 send 方法来完成请求
	http.sned();
	// 然后可以直接获取到数据,例如,获取当前http的全部数据(包含协议头,响应头响应体)
	System.out.print(new String(http.getRespData()));
</pre>
