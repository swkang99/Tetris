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
		colors = new Color[8]; // ��Ʈ���� ��� �� ���� ��
		colors[0] = new Color(80, 80, 80); // ����(����ȸ��)
		colors[1] = new Color(255, 0, 0); // ������
		colors[2] = new Color(0, 255, 0); // ���
		colors[3] = new Color(0, 200, 255); // �����
		colors[4] = new Color(255, 255, 0); // �ϴû�
		colors[5] = new Color(255, 150, 0); // Ȳ���
		colors[6] = new Color(210, 0, 240); // �����
		colors[7] = new Color(40, 0, 240); // �Ķ���
	}

	public void start() { // ���� ���� 33 data.clear();
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

		for (int i = 0; i < TetrisData.ROW; i++) { // ���� ������ �׸���
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

		if (current != null) { // ���� �������� �ִ� ��Ʈ���� ���� �׸���
			for (int i = 0; i < 4; i++) {
				g.setColor(colors[current.getType()]);
				g.fill3DRect(margin / 2 + w * (current.getX() + current.c[i]),
						margin / 2 + w * (current.getY() + current.r[i]), w, w, true);
			}
		}
	}

	public Dimension getPreferredSize() { // ��Ʈ���� ���� ũ�� ����
		int tw = w * TetrisData.COL + margin;
		int th = w * TetrisData.ROW + margin;
		return new Dimension(tw, th);
	}

	public void run() {
		while (!stop) {
			try {
				if (makeNew) { // ���ο� ��Ʈ���� ���� �����
					int random = (int) (Math.random() * Integer.MAX_VALUE) % 7;

					switch (random) {
					case 0:
						current = new Bar(data);
						break;
					case 1:
						current = new Tee(data);
						break;
					case 2:
						current = new El(data);
						break;
					case 3:
// �߰��� �ۼ��� ����
					case 4:
// �߰��� �ۼ��� ����
					case 5:
// �߰��� �ۼ��� ����
					case 6:
// �߰��� �ۼ��� ����
					default:
						if (random % 2 == 0)
							current = new Tee(data);
						else
							current = new El(data);
					}
					makeNew = false;
				} else { // ���� ������� ��Ʈ���� ���� �Ʒ��� �̵�
					if (current.moveDown()) {
						makeNew = true;
						if (current.copy()) {
							stop();
							int score = data.getLine() * 175 * level;
							JOptionPane.showMessageDialog(this, "���ӳ�\n���� : " + score);
						}
						current = null;
					}
					data.removeLines();
				}
				repaint();
				Thread.currentThread().sleep(interval / level);
			} catch (Exception e) {
			}
		}
	}

// Ű���带 �̿��ؼ� ��Ʈ���� ���� ����
	public void keyPressed(KeyEvent e) {
		if (current == null)
			return;

		switch (e.getKeyCode()) {
		case 37: // ���� ȭ��ǥ
			current.moveLeft();
			repaint();
			break;
		case 39: // ������ ȭ��ǥ
			current.moveRight();
			repaint();
			break;
		case 38: // ���� ȭ��ǥ
			current.rotate();
			repaint();
			break;
		case 40: // �Ʒ��� ȭ��ǥ
			boolean temp = current.moveDown();
			if (temp) {
				makeNew = true;
				if (current.copy()) {
					stop();
					int score = data.getLine() * 175 * level;
					JOptionPane.showMessageDialog(this, "���ӳ�\n���� : " + score);
				}
			}
			data.removeLines();
			repaint();
		}
	}

	public void keyReleased(KeyEvent e) {
	}

	public void keyTyped(KeyEvent e) {
	}
}