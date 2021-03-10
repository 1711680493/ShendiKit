package shendi.kit.console;

import java.awt.AWTException;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TextArea;
import java.awt.TrayIcon;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import shendi.kit.console.command.Command;
import shendi.kit.log.Log;

/**
 * 是一个窗体控制台.
 * <br>
 * 此控制台与程序绑定,关闭此控制台则会一并把程序关闭.
 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
 * @version 1.0
 */
public class DeskAppConsole extends Console {
	
	/** 窗体 */
	private JFrame frame = new JFrame("控制台");
	
	/** 当前窗体的托盘 */
	private TrayIcon tray;
	
	/** 命令对话框 */
	private JDialog commandDialog;
	
	/** 命令对话框的面板 */
	private JPanel dialogPanel;
	
	/** 实时显示面板 */
	private JPanel panel;
	
	/** 命令输入框 */
	private JTextField commandInput;
	
	/** 命令控制台 */
	private TextArea console;
	
	/** 实时监控列表组件 components */
	private final HashMap<Command, JTextField> COMS = new HashMap<>();
	
	/** 当前面板的显示数量,用于绝对布局组件的定位 */
	private int num = 0;
	
	/** 当前控制台是否被销毁,循环标志位 */
	private boolean isDestroy;
	
	/**
	 * @wbp.parser.entryPoint
	 */
	@Override protected void register(HashMap<String, Command> commands) {
		try {
			BufferedImage img = ImageIO.read(DeskAppConsole.class.getResourceAsStream("/img/logo.png"));
			frame.setIconImage(img);
			tray = new TrayIcon(img);
			tray.setImageAutoSize(true);
			tray.setToolTip("控制台 -SK");
			PopupMenu p = new PopupMenu();
			p.add("退出");
			
			p.addActionListener((e) -> {
				String name = e.getActionCommand();
				switch (name) {
				case "退出": destroy(); break;
				
				}
			});
			tray.setPopupMenu(p);
			
			tray.addActionListener((e) -> frame.setVisible(true));
			tray.addMouseListener(new MouseAdapter() {
				@Override public void mouseClicked(MouseEvent e) {
					if (1 == e.getButton()) frame.setVisible(true);
				}
			});
			
			try {
				if (SystemTray.isSupported()) SystemTray.getSystemTray().add(tray);
				else Log.printErr("操作系统不支持托盘,创建托盘失败.");
			} catch (AWTException e) { Log.printErr("添加托盘到系统托盘失败: " + e.getMessage()); }
		} catch (IOException e) { Log.printErr("窗体控制台注册,图标未找到-托盘创建失败: " + e.getMessage()); }
		
		
		frame.getContentPane().setLayout(null);
		frame.setBounds(100, 100, 600, 600);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu menu = new JMenu("菜单");
		menuBar.add(menu);
		
		// 命令对话框
		commandDialog = new JDialog(frame, "命令实时显示选择");
		commandDialog.setBounds(100, 100, 340, 600);
		commandDialog.getContentPane().setLayout(null);
		
		dialogPanel = new JPanel();
		dialogPanel.setBounds(0, 0, 320, 560);
		dialogPanel.setLayout(null);
		JScrollPane dialogScroll = new JScrollPane(dialogPanel);
		dialogScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		dialogScroll.setBounds(0, 0, 320, 560);
		commandDialog.getContentPane().add(dialogScroll);
		
		JMenuItem commandItem = new JMenuItem("命令");
		
		// 弹出列表选择框
		commandItem.addActionListener((e) -> commandDialog.setVisible(true));
		menu.add(commandItem);
		
		JMenuItem reloadItem = new JMenuItem("重新加载");
		reloadItem.addActionListener((e) -> rel());
		menu.add(reloadItem);
		
		console = new TextArea();
		console.setText("控制台\r\n可通过菜单->命令来设置需要实时显示的命令\r\n自带命令: help-命令列表,reload-重新加载命令,clear-清空,exit-退出\r\n可输入命令直接按Enter键执行");
		console.setEditable(false);
		console.setBounds(10, 270, 564, 230);
		frame.getContentPane().add(console);
		
		commandInput = new JTextField();
		commandInput.setToolTipText("");
		commandInput.setBounds(10, 507, 479, 21);
		commandInput.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) { if (e.getKeyChar() == KeyEvent.VK_ENTER) execute(); }
		});
		
		frame.getContentPane().add(commandInput);
		commandInput.setColumns(10);
		
		// 实时显示面板
		panel = new JPanel();
		panel.setBounds(10, 10, 564, 254);
		panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane(panel);
		scrollPane.setBounds(10, 10, 564, 254);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		frame.getContentPane().add(scrollPane);
		
		JButton exeBtn = new JButton("执行");
		exeBtn.setBounds(499, 506, 75, 23);
		exeBtn.addActionListener((e) -> execute());
		frame.getContentPane().add(exeBtn);
		
		// 设置命令对话框的内容
		rel();
		
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosed(WindowEvent e) { System.exit(0); }
		});
		frame.setVisible(true);
		
		while (true) {
			try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
			
			if (isDestroy) return;
			if (!frame.isVisible()) continue;
			
			synchronized (COMS) {
				COMS.forEach((command, v) -> {
					StringBuilder build = new StringBuilder(50);
					
					build.append('['); build.append(command.getInfo()); build.append("] ");
					build.append(command.execute()); build.append('\n');
					
					COMS.get(command).setText(build.toString());
				});
			}
		}
	}

	/**
	 * 重新加载命令.
	 * <br>
	 * 会刷新命令对话框的列表,清空当前实时显示内容.
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 */
	private void rel() {
		reload();
		int checkNum = 0;
		
		// 面板清空
		dialogPanel.removeAll();
		panel.removeAll();
		
		Iterator<Entry<String, Command>> it = commands.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, Command> next = it.next();
			StringBuilder build = new StringBuilder(20);
			build.append('['); build.append(next.getKey()); build.append(']');
			build.append(next.getValue().getInfo());
			
			JCheckBox check = new JCheckBox(build.toString());
			JTextField text = new JTextField();
			text.setSize(540, 30);
			text.setEditable(false);
			
			check.addActionListener((e) -> {
				synchronized (COMS) {
					if (check.isSelected()) {
						COMS.put(next.getValue(), text);
						text.setLocation(0, num++ * 30);
						panel.add(text);
					} else {
						COMS.remove(next.getValue());
						num--;
						panel.remove(text);
						Component[] com = panel.getComponents();
						for (int i = 0;i < num;i++) com[i].setLocation(0, i * 30);
					}
				}
				panel.setPreferredSize(new Dimension(540, 30 * num));
				panel.updateUI();
			});
			check.setBounds(0, checkNum++ * 30, 300, 30);
			dialogPanel.setPreferredSize(new Dimension(300, 30 * checkNum));
			dialogPanel.add(check);
		}
		
		// 面板刷新
		dialogPanel.validate();
		dialogPanel.repaint();
		panel.validate();
		panel.repaint();
	}
	
	/**
	 * 执行命令输入框的命令
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 */
	private void execute() {
		String text = commandInput.getText();
		commandInput.setText("");
		
		// 控制台的字数不能多于 5000 个
		if (console.getText().length() > 5000) { console.setText(""); }
		
		if ("help".equals(text)) {
			console.append("\r\n1.help\t\t显示命令列表");
			console.append("\r\n2.reload\t\t重新加载命令");
			console.append("\r\n3.clear\t\t清空控制台");
			console.append("\r\n4.exit\t\t关闭控制台");

			int num = 5;
			Iterator<Entry<String, Command>> it = commands.entrySet().iterator();
			while (it.hasNext()) {
				Entry<String, Command> next = it.next();
				console.append("\r\n");
				console.append(String.valueOf(num));
				num++;
				console.append(".");
				console.append(next.getKey());
				console.append("\t\t");
				console.append(next.getValue().getInfo());
			}
			console.append("\r\n*------------------------*");
		} else if ("reload".equals(text)) {
			rel();
			console.append("\r\n重新加载完毕.");
		} else if ("clear".equals(text)) {
			console.setText("");
		} else if ("exit".equals(text)) {
			destroy();
		} else {
			String result = execute(text);
			if (result == null) {
				console.append("\r\n*--没有此命令");
			} else {
				console.append("\r\n");
				console.append(result);
			}
		}
	}
	
	@Override public void destroy() {
		isDestroy = true;
		commandDialog.dispose();
		frame.dispose();
		// 去掉托盘
		if (SystemTray.isSupported()) SystemTray.getSystemTray().remove(tray);
	}

	/**
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @return 当前控制台的托盘
	 */
	public TrayIcon getTray() { return tray; }
	
	/**
	 * @author Shendi <a href='tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin=1711680493'>QQ</a>
	 * @return 当前控制台窗体
	 */
	public JFrame getFrame() { return frame; }
}
