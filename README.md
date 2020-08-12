# 简介
>Project Name:Shendi Kit<br>
>author:Shendi<br>
>version: 1.0<br>
>QQ:1711680493
# 测试样例
>样例在源码的 shendi.kit.test 包下.
# 非常简单的使用 Properties 配置文件
## 功能介绍
>可以轻松获取到Properties配置文件,并且修改配置文件后可以及时看到结果<br>
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


# 时间工具类
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
>此类中方法都是静态的,如需了解更多,请参阅JavaDoc.

# 日志工具类
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

# 加密
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
### 求和取余加密算法(不可逆)
>使用 EncryptFactory.getEncrypt(EncryptFactory.SUM_REMAINDER); 来获取
### 添加自己的加密算法到工厂
>新建一个类实现 shendi.kit.encrypt.Encrypt 接口,并使用 EncryptFactory.addEncrypt(获取时的名,新建的加密算法类); 来进行添加

## 加密工具类 shendi.kit.encrypt.EncryptUtils
>这里提供了一个工具类来很简便的对文件进行操作<br>
>通过 EncryptUtils.encryptFiles() 来对指定文件/文件夹进行加密<br>
>如果是对文件夹进行操作,则会将此文件下所有的文件等进行加密,默认加密后的保存路径为当前加密路径的 /Shendi_Encrypt 下<br>
>如果是对文件进行操作,默认保存为 /Shendi_Encrypt_文件名<br>
>也可以自己指定保存的路径,请参阅此类的文档<br>
>同样,也提供了解密的方法 EncryptUtils.decodeFiles()<br>
