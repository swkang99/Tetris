package tetris;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class TetrisCanvas extends JPanel implements Runnable, KeyListener {
	protected Thread worker;
	protected Color colors[];
	protected int w = 25;
	protected TetrisData data;
	protected int margin = 20;
	protected boolean stop, makeNew;
	protected Piece current;
	protected int interval = 2000;
	protected int level = 2;

	public TetrisCanvas() {
		data = new TetrisData();

		addKeyListener(this);
		colors = new Color[8]; 				// 테트리스 배경 및 조각 색
		colors[0] = new Color(80, 80, 80); 	// 배경색(검은회색)
		colors[1] = new Color(255, 0, 0); 	// 빨간색
		colors[2] = new Color(0, 255, 0); 	// 녹색
		colors[3] = new Color(0, 200, 255); // 노란색
		colors[4] = new Color(255, 255, 0); // 하늘색
		colors[5] = new Color(255, 150, 0); // 황토색
		colors[6] = new Color(210, 0, 240); // 보라색
		colors[7] = new Color(40, 0, 240); 	// 파란색
	}

	// 게임 시작
	public void start() {
		data.clear();
		worker = new Thread(this);
		worker.start();
		makeNew = true;
		stop = false;
		requestFocus();
		repaint();
	}

	public void stop() {
		stop = true;
		current = null;
	}

	public void paint(Graphics g) {
		super.paint(g);

		// 쌓인 조각들 그리기
		for (int i = 0; i < TetrisData.ROW; i++) {
			for (int k = 0; k < TetrisData.COL; k++) {
				if (data.getAt(i, k) == 0) {
					g.setColor(colors[data.getAt(i, k)]);
					g.draw3DRect(margin / 2 + w * k, margin / 2 + w * i, w, w, true);
				} else {
					g.setColor(colors[data.getAt(i, k)]);
					g.fill3DRect(margin / 2 + w * k, margin / 2 + w * i, w, w, true);
				}
			}
		}

		// 현재 내려오고 있는 테트리스 조각 그리기
		if (current != null) {
			for (int i = 0; i < 4; i++) {
				g.setColor(colors[current.getType()]);
				g.fill3DRect(margin / 2 + w * (current.getX() + current.c[i]),
						margin / 2 + w * (current.getY() + current.r[i]), w, w, true);
			}
		}
	}

	// 테트리스 판의 크기 지정
	public Dimension getPreferredSize() {
		int tw = w * TetrisData.COL + margin;
		int th = w * TetrisData.ROW + margin;
		return new Dimension(tw, th);
	}

	public void run() {
		while (!stop) {
			if (makeNew) {
				int random = (int) (Math.random() * Integer.MAX_VALUE) % 7;
				switch (random) {
				case 0:
					current = new Bar(data);
					break;
				case 1:
					current = new J(data);
					break;
				case 2:
					current = new El(data);
					break;
				case 3:
					current = new O(data);
					break;
				case 4:
					current = new S(data);
					break;
				case 5:
					current = new Tee(data);
					break;
				case 6:
					current = new Z(data);
					break;
				}
				makeNew = false;
			}
			// 현재 만들어진 테트리스 조각 아래로 이동
			else {
				if (data.timeCtrl(1)) {
					Down();
				}
				repaint();
			}
			// Thread.currentThread().sleep(interval / level);
		}
	}

	// 키보드를 이용해서 테트리스 조각 제어
	public void keyPressed(KeyEvent e) {
		if (current == null)
			return;

		switch (e.getKeyCode()) {
		case 37: // 왼쪽 화살표
			current.moveLeft();
			repaint();
			break;
		case 39: // 오른쪽 화살표
			current.moveRight();
			repaint();
			break;
		case 38: // 윗쪽 화살표
			current.rotate();
			repaint();
			break;
		case 40: // 아랫쪽 화살표
			Down();
			repaint();
			break;
		}
	}

	public void keyReleased(KeyEvent e) {
	}

	public void keyTyped(KeyEvent e) {
	}
	
	public void Down() {
		if (current.moveDown()) {
			makeNew = true;
			if (current.copy()) {
				stop();
				int score = data.getLine() * 175 * level;
				JOptionPane.showMessageDialog(this, "게임끝\n점수 : " + score);
			}
			data.removeLines();
		}
	}
}