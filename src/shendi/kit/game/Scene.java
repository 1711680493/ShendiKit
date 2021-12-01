package shendi.kit.game;

/**
 * 代表一个游戏场景.
 * <br>
 * 创建日期 2021年10月9日 下午9:59:50
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 * @version 1.0
 */
public interface Scene {

	/**
	 * 添加物体到场景
	 * @param obj 物体
	 */
	void add(GameObject obj);

	/**
	 * 将指定物体从场景删除
	 * @param obj 物体
	 */
	void del(GameObject obj);
	
}
