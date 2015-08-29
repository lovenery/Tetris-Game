package Tetris;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.Timer;

public class TetrisMain extends Canvas implements Runnable {

	final static JFrame frame = new JFrame("Tetris");
	static JMenuBar bar = new JMenuBar();
	static JMenu file = new JMenu("File");
	static JMenuItem newGame = new JMenuItem("Start");
	static JMenuItem highScore = new JMenuItem("HighScore");
	static JMenuItem exit = new JMenuItem("Exit");
	static JMenuItem options = new JMenuItem("Options");

	final static TetrisMain tm = new TetrisMain();
	static TimeClock tc;
	static Timer timer;

	public static final int WIDTH = 700, HEIGHT = 700;
	private Image[] tetrisBlocks;
	private Image b1, b2, shadow, space;
	private int[][] map = new int[20][10];
	public int next, hold, type, state, x, y, y1, change, visit;
	private boolean[] first = new boolean[7];
	Controller control;
	private final int[][][] shape = {
			// I
			{ { 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0 }, { 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0 } },
			// S
			{ { 0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0 }, { 0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 } },
			// Z
			{ { 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0 }, { 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0 } },
			// J
			{ { 1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 1, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 }, { 1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 1, 0, 0, 0, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0 } },
			// O
			{ { 0, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 } },
			// L
			{ { 0, 0, 1, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0 }, { 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 1, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 } },
			// T
			{ { 0, 1, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 1, 0, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0 }, { 0, 1, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 } } };

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// TetrisMain tm = new TetrisMain();
		tm.setBounds(0, 25, WIDTH, HEIGHT - 25);

		frame.add(tm);
		file.add(newGame);
		file.add(highScore);
		file.add(options);
		file.add(exit);

		bar.add(file);
		frame.add(bar);
		frame.setVisible(true);

		tm.threadstart();
	}

	public TetrisMain() {
		frame.setSize(WIDTH, HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // close
		frame.setLocationRelativeTo(null); // setting frame display on center
		frame.setResizable(false);
		frame.setLayout(null); // for setBounds()

		KeyGetter.LoadKeys(); // KeyGetter.java
		try {
			Config.loadConfig(); // Config.java
		} catch (Exception e) {
			e.printStackTrace();
		}

		bar.setBounds(0, 0, WIDTH, 25);

		file.setBounds(0, 0, 45, 24);

		newGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("newGame");

				try {
					Music.music("src/TetrisMusic/54321.au");
					Thread.sleep(3600);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				tc = new TimeClock();
				timer = new Timer(400, tc);
				timer.start();

				Music BGM = new Music();
				Thread bgm = new Thread(BGM);
				bgm.start();

				control = new Controller(tm);
				tm.addKeyListener(control);

				newGame.setEnabled(false);
			}
		});

		highScore.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("highScore");
				int highscore = 0;
				final JFrame alert = new JFrame("High Score");
				alert.setSize(300, 300);
				alert.setLocationRelativeTo(null);
				alert.setLayout(null);

				JLabel score = new JLabel("The Highscore is: " + highscore);
				score.setBounds(0, 0, 200, 50);

				JButton okayButton = new JButton("Okay");
				okayButton.setBounds(100, 150, 100, 30);
				okayButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						alert.dispose(); // 釋放系統資源 並停止繪圖
					}
				});

				alert.add(score);
				alert.add(okayButton);
				alert.setResizable(false);
				alert.setVisible(true);
			}
		});

		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("exit");
				System.exit(0);
			}
		});

		options.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Config.openConfig(frame); // Config.java
			}
		});

	}

	public void threadstart() {
		// 主執行緒
		Thread t = new Thread(this);
		t.setPriority(Thread.MAX_PRIORITY);
		t.start();

	}

	public void run() {

		init();
		//
		initMap();
		next = (int) (Math.random() * 7);
		hold = -1;
		change = 0;
		visit = 0;
		x = 3;
		y = y1 = 0;
		newBlock();
		//

		boolean running = true;
		while (running) {
			update();

			BufferStrategy buf = getBufferStrategy();
			if (buf == null) {
				createBufferStrategy(3);
				continue;
			}

			Graphics2D g = (Graphics2D) buf.getDrawGraphics();
			render(g);
			buf.show();
		}
	}

	public void init() { // 圖片初始化

		requestFocus(); // 重新監聽JFrame

		tetrisBlocks = new Image[7];
		for (int i = 0; i < 7; i++) {
			tetrisBlocks[i] = Toolkit.getDefaultToolkit().getImage("src/TetrisImg/" + i + ".png");
		}
		b1 = Toolkit.getDefaultToolkit().getImage("src/TetrisImg/background1.png");
		b2 = Toolkit.getDefaultToolkit().getImage("src/TetrisImg/background2.png");
		shadow = Toolkit.getDefaultToolkit().getImage("src/TetrisImg/shadow.png");
		space = Toolkit.getDefaultToolkit().getImage("src/TetrisImg/space.png");
	}

	public void initMap() {
		for (int i = 0; i < 20; i++)
			for (int j = 0; j < 10; j++)
				map[i][j] = 0;
		for (int i = 0; i < 7; i++)
			first[i] = false;
		hold = -1;
		change = 0;
		visit = 0;

	}

	public void update() {
	}

	public void newBlock() {
		type = next;
		first[type] = true;
		next = (int) (Math.random() * 7);
		while (visit < 6 && first[next] == true) {
			next = (int) (Math.random() * 7);
		}
		visit++;
		x = 3;
		y = 0;
		state = 0;
		if (Judge(x, y, type, state) == false) { // 死掉了要重來
			initMap();
			newBlock();

			System.out.println("GameOver");
		}
	}

	public void setBlock() {
		for (int i = 0; i < 16; i++)
			if (shape[type][state][i] == 1)
				map[y + i / 4][x + i % 4] = type + 1;
	}

	public boolean down_shift() { // 往下 不能往下就新方塊
		if (Judge(x, y + 1, type, state))
			y++;
		else {
			setBlock();
			newBlock();
			delLine();
			return false;
		}
		return true;
	}

	public void delLine() { // 處理方塊刪除
		int temp = 19;
		for (int i = 19; i >= 0; i--) {
			int cnt = 0;
			for (int j = 0; j < 10; j++)
				if (map[i][j] != 0)
					cnt++;
			if (cnt == 10)
				for (int j = 0; j < 10; j++)
					map[i][j] = 0;
			else {
				for (int j = 0; j < 10; j++)
					map[temp][j] = map[i][j];
				temp--;
			}
		}
	}

	public class TimeClock implements ActionListener { // 一秒下降一次
		public void actionPerformed(ActionEvent e) {
			down_shift();
		}
	}

	public boolean Judge(int x, int y, int type, int state) {
		for (int i = 0; i < 16; i++) {
			if (shape[type][state][i] == 1) {
				if (x + i % 4 >= 10 || y + i / 4 >= 20 || x + i % 4 < 0 || y + i / 4 < 0)
					return false;
				if (map[y + i / 4][x + i % 4] != 0)
					return false;
			}
		}
		return true;
	}

	public void shadow_effect() { // 陰影
		y1 = y + 1;
		while (shadow_shift())
			;
	}

	public boolean shadow_shift() { // 陰影移動
		if (Judge(x, y1 + 1, type, state))
			y1++;
		else
			return false;
		return true;
	}

	public void render(Graphics2D g) {
		//
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);

		g.setColor(Color.WHITE);
		g.setFont(new Font("", Font.BOLD, 50));
		g.drawString("HOLD", 35, 50);

		g.setColor(Color.ORANGE);
		g.setFont(new Font("", Font.BOLD, 30));
		g.drawString("(Shift,C)", 35, 80);

		g.setColor(Color.WHITE);
		g.setFont(new Font("", Font.BOLD, 50));
		g.drawString("NEXT", 525, 50);

		g.setColor(Color.ORANGE);
		g.setFont(new Font("", Font.BOLD, 60));
		g.drawString("P", 25, 250);

		g.setColor(Color.WHITE);
		g.setFont(new Font("", Font.BOLD, 50));
		g.drawString("ause", 60, 250);

		g.setColor(Color.ORANGE);
		g.setFont(new Font("", Font.BOLD, 30));
		g.drawString("(P)", 75, 290);

		g.setColor(Color.WHITE);
		g.setFont(new Font("", Font.BOLD, 50));
		g.drawString("Click", 35, 400);
		g.setColor(Color.RED);
		g.setFont(new Font("", Font.BOLD, 50));
		g.drawString("File&", 35, 450);
		g.setColor(Color.RED);
		g.setFont(new Font("", Font.BOLD, 50));
		g.drawString("Start", 35, 500);
		g.setColor(Color.WHITE);
		g.setFont(new Font("", Font.BOLD, 50));
		g.drawString("First", 35, 550);

		g.setColor(Color.GREEN);
		g.drawRect(30, 350, 130, 210);

		g.setColor(Color.BLUE);
		g.setFont(new Font("", Font.BOLD, 50));
		g.drawString("Space", 525, 400);

		g.drawImage(space, 525, 425, null);
		//

		// 背景的棋盤格
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 10; j++) {
				if (map[i][j] == 0) {
					if ((i + j) % 2 == 0)
						g.drawImage(b1, j * 32 + 190, i * 32, null);
					else
						g.drawImage(b2, j * 32 + 190, i * 32, null);
				} else
					g.drawImage(tetrisBlocks[map[i][j] - 1], j * 32 + 190, i * 32, null);
			}
		}

		shadow_effect();

		// droping
		for (int i = 0; i < 16; i++) {
			if (Judge(x, y, type, state))
				if (shape[type][state][i] == 1)
					g.drawImage(tetrisBlocks[type], (x + i % 4) * 32 + 190, (y + i / 4) * 32, null);
			if (Judge(x, y1, type, state))
				if (shape[type][state][i] == 1)
					g.drawImage(shadow, (x + i % 4) * 32 + 190, (y1 + i / 4) * 32, null);
		}
		// HOLD
		if (hold >= 0) {
			for (int i = 0; i < 16; i++) {
				if (shape[hold][0][i] == 1) {
					g.drawImage(tetrisBlocks[hold], (i % 4) * 32 + 30, (i / 4) * 32 + 100, null);
				}
			}
		}
		// NEXT
		for (int i = 0; i < 16; i++) {
			if (shape[next][0][i] == 1) {
				g.drawImage(tetrisBlocks[next], (i % 4) * 32 + 540, (i / 4) * 32 + 100, null);
			}
		}
	}
}
