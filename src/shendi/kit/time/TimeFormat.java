package shendi.kit.time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import shendi.kit.log.Log;

/**
 * 用于处理时间的格式化,拥有将字符串,Date,long处理为时间的函数.<br>
 * 通常我们需要获取当前时间的字符串表示形式,或者将字符串表示为一个Date,或者表示为一串数字<br>
 * 提供了 <b>getString(Date),getDate(String),getNum(Date or String)</b> 三个函数<br>
 * 如果我们需要代表一个时间(比如存储时间的表示形式等) 请关注 {@link Time}<br>
 * 如果我们需要处理时间(比如获取现在到第二天零点的时间差...) 则请关注 {@link TimeDisposal}
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 * @version 1.0
 * @since ShendiKit 1.0
 * @see Time
 * @see TimeDisposal
 * @see TimeUtils#getFormatTime
 * @see TimeUtils#addTimeFormat(String, String)
 */
public final class TimeFormat {
	/**
	 * 格式化的内容.
	 */
	private final SimpleDateFormat format;
	
	/**
	 * 使用一个日期格式来创造 TimeFormat.
	 * @param format 日期的字符串格式形式
	 */
	TimeFormat(String format) {
		this.format = new SimpleDateFormat(format);
	}
	
	/**
	 * 获取 时间 的字符串表示形式.
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param date 日期
	 * @return 字符串的表示形式
	 */
	public String getString(Date date) {
		return format.format(date);
	}
	
	/**
	 * 获取 时间 的字符串表示形式.
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param date 日期的时间表示形式
	 * @return 字符串的表示形式
	 */
	public String getString(long date) {
		return format.format(date);
	}
	
	/**
	 * 获取字符串的 Date 表示形式.
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param time 的时间表示形式
	 * @return 字符串表示的 Date
	 */
	public Date getDate(long time) {
		return new Date(time);
	}
	
	/**
	 * 获取字符串的 Date 表示形式.
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param time 的字符串表示形式
	 * @return 字符串表示的 Date 如果为null 则代表转换Date失败
	 */
	public Date getDate(String time) {
		try {
			return format.parse((String) time);
		} catch (ParseException e) {
			e.printStackTrace();
			Log.printErr("字符串转换时间失败 转换的字符串:" + time);
			return null;
		}
	}
	
	/**
	 * 获取日期的数字表示形式
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param date 日期的字符串表示形式
	 * @return 日期表示的数字形式 -1则代表失败
	 */
	public long getNum(String date) {
		try {
			return format.parse(date).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
			Log.printErr("字符串转换日期的数字表示失败 转换的字符串:" + date);
		}
		return -1;
	}
	
	/**
	 * 获取日期的数字表示形式
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @param date 日期
	 * @return 日期表示的数字形式
	 */
	public long getNum(Date date) {
		return date.getTime();
	}
}
