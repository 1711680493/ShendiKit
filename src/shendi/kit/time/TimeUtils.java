package shendi.kit.time;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import shendi.kit.log.Log;

/**
 * 时间工具类,拥有处理和表示时间等功能.
 * 拥有格式化的池子,带有去重处理(池子内元素 名称不重复)<br>
 * 如需添加时间格式,请使用 {@link #addTimeFormat(String, String)}<br>
 * 拥有内部类 {@link TimeDisposal} 用于方便处理时间.
 * <table border='1'>
 * 	<tr>
 * 		<th>类名</th>
 * 		<th>描述</th>
 * 		<th>获取方式</th>
 * 	<tr>
 * 	<tr>
 * 		<td>{@link Time}</td>
 * 		<td>用于代表一个时间</td>
 * 		<td>
 * 			通过 {@link #createTime(Date, TimeFormat)}.
 * 		</td>
 * 	<tr>
 * 	<tr>
 * 		<td>{@link TimeFormat}</td>
 * 		<td>用于格式化时间</td>
 * 		<td>
 * 			{@link #getFormatTime()} 来获取默认的TimeFormat对象 格式为(2020-02-03 22:23:24)<br>
 * 			{@link #getFormatTime(String name)}获取此类指定对象
 * 		</td>
 * 	<tr>
 * 	<tr>
 * 		<td>{@link TimeDisposal}</td>
 * 		<td>用于处理时间,比如计算到第二天的时间差...</td>
 * 		<td>此类不需要对象,静态,如要使用,直接 TimeDisposal.方法名 即可</td>
 * 	<tr>
 * </table>
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 * @since ShendiKit 1.0
 * @version 1.0
 * @see Time
 * @see TimeFormat
 * @see TimeDisposal
 */
public final class TimeUtils {
	
	/** 一天的时间戳 */
	public static final long TIMESTAMP_ONE_DAY = 86400000;
	
	/** 代表一个精确到秒的Time */
	public static final String DATE_TIME = "date_time";
	/** 代表一个精确到秒的Time 适用于文件名 */
	public static final String F_DATE_TIME = "f_date_time";
	/** 代表一个精确到天的Time */
	public static final String DATE = "date";
	
	/** 存储时间格式化的池子 享元模式,名称=格式 */
	private static final HashMap<String,TimeFormat> TIME_FORMATS = new HashMap<>();
	
	/** 存储 TIME_FORMATS 对应的字符串格式,格式字符串=名称 */
	private static final HashMap<String,String> FORMATS = new HashMap<>();
	
	static {
		//初始化池子
		TIME_FORMATS.put(DATE_TIME, new TimeFormat("yyyy-MM-dd HH:mm:ss"));
		FORMATS.put("yyyy-MM-dd HH:mm:ss", DATE_TIME);
		
		TIME_FORMATS.put(F_DATE_TIME, new TimeFormat("yyyy-MM-dd HH-mm-ss"));
		FORMATS.put("yyyy-MM-dd HH-mm-ss", F_DATE_TIME);
		
		TIME_FORMATS.put(DATE, new TimeFormat("yyyy-MM-dd"));
		FORMATS.put("yyyy-MM-dd", DATE);
	}
	
	private TimeUtils() {}
	
	/**
	 * 添加一个时间格式.<br>
	 * 如果名称重复则不添加并提示.<br>
	 * 如果格式重复则添加的为引用 = 多名称对应一格式.
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param name 格式代表的名称
	 * @param format 与SimpleDateFormat的构造参数一致 例如 yyyy-MM-dd HH:mm:ss
	 * @see SimpleDateFormat
	 */
	public static void addTimeFormat(String name,String format) {
		// 判断名称池是否存在
		if (TIME_FORMATS.containsKey(name)) {
			Log.printErr("添加的时间格式的名称已存在,请更改名称 name=" + name);
			return;
		}
		// 判断格式是否重复 重复则执行将名称=引用 不重复则名称=new Time
		if (FORMATS.containsKey(format)) {
			//添加引用到Time池
			TIME_FORMATS.put(name, TIME_FORMATS.get(FORMATS.get(format)));
		} else {
			TIME_FORMATS.put(name,new TimeFormat(format));
		}
		// 添加名称格式进格式池
		FORMATS.put(format, name);
	}
	
	/**
	 * 获取一个默认的 TimeFormat 对象.<br>
	 * 类 TimeUtils 的 getFormatTime() 方法的效果等同于 getFormatTime(TimeUtils.DATE_TIME);
	 * @author <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>Shendi QQ</a>
	 * @return 默认的Time
	 * @see #getFormatTime(String)
	 */
	public static TimeFormat getFormatTime() {
		return getFormatTime(DATE_TIME);
	}
	
	/**
	 * 获取一个指定的 TimeFomat 对象.<br>
	 * 默认存在的一些TimeFormat对象格式<br>
	 * <table border='1'>
	 * 	<tr>
	 * 		<th>field</th>
	 * 		<th>time</th>
	 * 		<th>style</th>
	 * 		<th>descript</th>
	 * 	</tr>
	 * 	<tr>
	 * 		<td>TimeUtils.DATE_TIME</td>
	 * 		<td>yyyy-MM-dd HH:mm:ss</td>
	 * 		<td>2020-04-06 22:00:00</td>
	 * 		<td>处理的结果为年月日 时分秒 数据库等地方都可以用到</td>
	 * 	</tr>
	 * 	<tr>
	 * 		<td>TimeUtils.F_DATE_TIME</td>
	 * 		<td>yyyy-MM-dd HH-mm-ss</td>
	 * 		<td>2020-04-06 22-00-00</td>
	 * 		<td>处理的结果可用于作为文件名</td>
	 * 	</tr>
	 * 	<tr>
	 * 		<td>TimeUtils.DATE</td>
	 * 		<td>yyyy-MM-dd</td>
	 * 		<td>2020-04-06</td>
	 * 		<td>处理的结果精确到当天</td>
	 * 	</tr>
	 * </table>
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param name time对象的名称
	 * @return 指定的Time 如果为null则代表无此Time
	 */
	public static TimeFormat getFormatTime(String name) {
		return TIME_FORMATS.get(name);
	}

	/**
	 * 通过字符串创建时间.
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param time 时间的字符串表示形式
	 * @param format 格式化样式 
	 * @return Time
	 */
	public static Time createTime(String time,TimeFormat format) {
		return new Time(time, format);
	}
	
	/**
	 * 通过 Date 创建时间.
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param time 时间的字符串表示形式
	 * @param format 格式化样式 
	 * @return Time
	 */
	public static Time createTime(Date time,TimeFormat format) {
		return new Time(time, format);
	}
	
	/**
	 * 通过 long 创建时间.
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param time 时间的字符串表示形式
	 * @param format 格式化样式 
	 * @return Time
	 */
	public static Time createTime(long time,TimeFormat format) {
		return new Time(time, format);
	}
	
	/**
	 * 时间处理类,用于处理计算时间,日期等信息.<br>
	 * 通常,我们需要计算一些时间 日期差,此类提供了对应的封装<br>
	 * 直接通过类名使用此类 基本上方法都是静态的 例如 TimeDisposal.nowToTomorrow() 获取现在到第二天零点的时间差<br>
	 * 使用 {@link TimeDisposal#getToTime(int, int, int, int, int, int, int)} 来方便的获取指定时间,-1为今天<br>
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @since ShendiKit 1.0
	 * @version 1.0
	 * @see Time
	 */
	public static class TimeDisposal {
		
		/**
		 * 没有对象可言.
		 */
		private TimeDisposal() {}
		
		/**
		 * 获取当前时间到第二天0点的时间差.<br>
		 * 相当于调用 noToTime(获取第二天零点的时间);
		 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
		 * @return 指定时间的数字表示形式 &lt; 1天,单位毫秒(1000 = 1秒)
		 * @exception IllegalArgumentException 如果传递的参数类型不为Date,Long或者参数不能被转换则抛出
		 * @see TimeDisposal#nowToTime(Date)
		 */
		public static long nowToTomorrow() { return nowToTime(getToTime(-1, -1, getDate() + 1, 0, 0, 0, 0)); }
		
		/**
		 * 获取当前到指定时间的差值.<br>
		 * 等价于调用 timeToTime(new Date(),date);
		 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
		 * @param date 指定时间.
		 * @return 返回当前时间到指定时间的差值
		 * @see #timeToTime(Date, Date)
		 */
		public static long nowToTime(Date date) { return timeToTime(new Date(),date); }
		
		/**
		 * 获取当前到指定时间的差值.<br>
		 * 等价于调用 timeToTime(new Date(),date);
		 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
		 * @param date 指定时间的字符串表示形式.
		 * @return 返回当前时间到指定时间的差值
		 * @see TimeDisposal#timeToTime(Date, String)
		 */
		public static long nowToTime(String date) { return timeToTime(new Date(),date); }
		
		/**
		 * 获取当前到指定时间的差值.<br>
		 * 等价于调用 timeToTime(new Date(),date);
		 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
		 * @param date 指定时间的数字表示形式
		 * @return 返回当前时间到指定时间的差值
		 * @see #timeToTime(Date, long)
		 */
		public static long nowToTime(long date) { return timeToTime(new Date(),date); }
		
		/**
		 * 计算指定时间到指定时间的差值.<br>
		 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
		 * @param sTime 要计算的开始时间 日期的字符串表示形式
		 * @param eTime 要计算的结束时间 日期的字符串表示形式
		 * @return 计算好的差值 返回的单位为毫秒(1000 = 1秒)
		 */
		public static long timeToTime(String sTime,String eTime) { return TimeUtils.getFormatTime().getNum(eTime) - TimeUtils.getFormatTime().getNum(sTime); }
		/**
		 * 计算指定时间到指定时间的差值.<br>
		 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
		 * @param sTime 要计算的开始时间 Date
		 * @param eTime 要计算的结束时间 Date
		 * @return 计算好的差值 返回的单位为毫秒(1000 = 1秒)
		 */
		public static long timeToTime(Date sTime,Date eTime) {
			return TimeUtils.getFormatTime().getNum(eTime) - TimeUtils.getFormatTime().getNum(sTime);
		}
		/**
		 * 计算指定时间到指定时间的差值.<br>
		 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
		 * @param sTime 要计算的开始时间 日期的时间表示形式
		 * @param eTime 要计算的结束时间 日期的时间表示形式
		 * @return 计算好的差值 返回的单位为毫秒(1000 = 1秒)
		 */
		public static long timeToTime(long sTime,long eTime) {
			return eTime - sTime;
		}
		/**
		 * 计算指定时间到指定时间的差值.<br>
		 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
		 * @param sTime 要计算的开始时间 日期的字符串表示形式
		 * @param eTime 要计算的结束时间 Date
		 * @return 计算好的差值 返回的单位为毫秒(1000 = 1秒)
		 */
		public static long timeToTime(String sTime,Date eTime) {
			return TimeUtils.getFormatTime().getNum(eTime) - TimeUtils.getFormatTime().getNum(sTime);
		}
		/**
		 * 计算指定时间到指定时间的差值.<br>
		 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
		 * @param sTime 要计算的开始时间 日期的字符串表示形式
		 * @param eTime 要计算的结束时间 日期的时间表示形式
		 * @return 计算好的差值 返回的单位为毫秒(1000 = 1秒)
		 */
		public static long timeToTime(String sTime,long eTime) {
			return eTime - TimeUtils.getFormatTime().getNum(sTime);
		}
		/**
		 * 计算指定时间到指定时间的差值.<br>
		 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
		 * @param sTime 要计算的开始时间 Date
		 * @param eTime 要计算的结束时间 日期的字符串表示形式
		 * @return 计算好的差值 返回的单位为毫秒(1000 = 1秒)
		 */
		public static long timeToTime(Date sTime,String eTime) {
			return TimeUtils.getFormatTime().getNum(eTime) - TimeUtils.getFormatTime().getNum(sTime);
		}
		/**
		 * 计算指定时间到指定时间的差值.<br>
		 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
		 * @param sTime 要计算的开始时间 Date
		 * @param eTime 要计算的结束时间 日期的时间表示形式
		 * @return 计算好的差值 返回的单位为毫秒(1000 = 1秒)
		 */
		public static long timeToTime(Date sTime,long eTime) {
			return eTime - TimeUtils.getFormatTime().getNum(sTime);
		}
		/**
		 * 计算指定时间到指定时间的差值.<br>
		 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
		 * @param sTime 要计算的开始时间 日期的时间表示形式
		 * @param eTime 要计算的结束时间 日期的字符串表示形式
		 * @return 计算好的差值 返回的单位为毫秒(1000 = 1秒)
		 */
		public static long timeToTime(long sTime,String eTime) {
			return TimeUtils.getFormatTime().getNum(eTime) - sTime;
		}
		/**
		 * 计算指定时间到指定时间的差值.<br>
		 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
		 * @param sTime 要计算的开始时间 日期的时间表示形式
		 * @param eTime 要计算的结束时间 Date
		 * @return 计算好的差值 返回的单位为毫秒(1000 = 1秒)
		 */
		public static long timeToTime(long sTime,Date eTime) {
			return TimeUtils.getFormatTime().getNum(eTime) - sTime;
		}
		
		/**
		 * 获取指定的时间 精确到毫秒 根据 年月日 时分秒 毫秒.<br>
		 * 传递的参数为-1就代表为当前的值
		 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
		 * @param year 指定年 在1-9999之间 如果为-1代表当前年
		 * @param month 指定月 在0-11之间 如果为-1代表本月
		 * @param day 指定日期 如果为-1代表今天
		 * @param hour 指定小时 如果为-1则代表当前时间
		 * @param minute 指定分 如果为-1则代表当前时间
		 * @param second 指定秒 如果为-1则代表当前时间
		 * @param milliSecond 指定毫秒 如果为-1则代表当前时间
		 * @return 指定时间的毫秒表示形式
		 */
		public static long getToTime(int year,int month,int day,int hour,int minute,int second,int milliSecond) {
			Calendar c = Calendar.getInstance();
			if (year != -1) c.set(Calendar.YEAR, year);
			if (month != -1) c.set(Calendar.MONTH, month);
			if (day != -1) c.set(Calendar.DATE, day);
			if (hour != -1) c.set(Calendar.HOUR_OF_DAY, hour);
			if (minute != -1) c.set(Calendar.MINUTE, minute);
			if (second != -1) c.set(Calendar.SECOND, second);
			if (milliSecond != -1) c.set(Calendar.MILLISECOND, milliSecond);
			return c.getTimeInMillis();
		}
		
		/**
		 * 将指定的毫秒转换为秒
		 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
		 * @param millisecond 毫秒
		 * @return 毫秒转换为秒
		 */
		public static long millisecondConvertSecond(long millisecond) {
			return millisecond / 1000;
		}
		
		/**
		 * 将指定的毫秒转换为分.
		 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
		 * @param millisecond 毫秒
		 * @return 毫秒转换为分钟
		 */
		public static int millisecondConvertMinute(long millisecond) {
			return (int) (millisecond / 60000);
		}
		
		/**
		 * 将指定的毫秒转换为小时.
		 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
		 * @param millisecond 毫秒
		 * @return 毫秒转换为小时
		 */
		public static int millisecondConvertHour(long millisecond) {
			return (int) (millisecond / 3600000);
		}
		
		/**
		 * 将指定的毫秒转换为天.
		 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
		 * @param millisecond 毫秒
		 * @return 毫秒转换天
		 */
		public static int millisecondConvertDay(long millisecond) {
			return (int) (millisecond / 86400000);
		}
		
		/**
		 * 获取本年.
		 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
		 * @return 今年
		 */
		@SuppressWarnings("deprecation")
		public static int getYear() {
			//经测试 Date比Calendar效率要高 所以用Date 尽管已过时
			return new Date().getYear() + 1900;
		}
		
		/**
		 * 获取本月.
		 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
		 * @return 值范围 1-12 返回本月
		 */
		@SuppressWarnings("deprecation")
		public static int getMonth() {
			//经测试 Date比Calendar效率要高 所以用Date 尽管已过时
			return new Date().getMonth() + 1;
		}
		
		/**
		 * 获取今天.
		 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
		 * @return 返回当天
		 */
		@SuppressWarnings("deprecation")
		public static int getDate() {
			//经测试 Date比Calendar效率要高 所以用Date 尽管已过时
			return new Date().getDate();
		}
		
		/**
		 * 获取当天小时.
		 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
		 * @return 返回当前的时间
		 */
		@SuppressWarnings("deprecation")
		public static int getHour() {
			//经测试 Date比Calendar效率要高 所以用Date 尽管已过时
			return new Date().getHours();
		}
		
		/**
		 * 获取当天分钟.
		 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
		 * @return 返回当前的时间
		 */
		@SuppressWarnings("deprecation")
		public static int getMinute() {
			//经测试 Date比Calendar效率要高 所以用Date 尽管已过时
			return new Date().getMinutes();
		}
		
		/**
		 * 获取分钟的秒.
		 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
		 * @return 返回当前的时间
		 */
		@SuppressWarnings("deprecation")
		public static int getSecond() {
			//经测试 Date比Calendar效率要高 所以用Date 尽管已过时
			return new Date().getSeconds();
		}
		
		/**
		 * 获取秒的毫秒.
		 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
		 * @return 返回当前的时间
		 */
		public static int getMilliSecond() {
			return Calendar.getInstance().get(Calendar.MILLISECOND);
		}
		
		/**
		 * 获取今天是本周的第几天.<br>
		 * 周日是第一天 也就是0,1=周一,2=周二,3=...6=周六
		 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
		 * @return 今天是这周的第几天
		 */
		@SuppressWarnings("deprecation")
		public static int getDayOfWeek() {
			return new Date().getDay();
		}
	}
	
}
