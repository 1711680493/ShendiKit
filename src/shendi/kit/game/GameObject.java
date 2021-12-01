package shendi.kit.game;

import java.util.List;

/**
 * 代表一个游戏对象,在场景中的所有物体都继承此类.
 * <br>
 * 创建日期 2021年10月9日 下午10:01:41
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 * @version 1.0
 */
public abstract class GameObject {

	/** 父级元素 */
	public GameObject parent;
	
	/** 子级元素列表 */
	public List<GameObject> child;
	
//	private List<Com>
	
}
