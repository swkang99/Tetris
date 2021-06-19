package tetris;

import java.awt.Point;

public abstract class Piece {
	final int DOWN = 0; 		// 방향 지정
	final int LEFT = 1;
	final int RIGHT = 2;
	protected int r[]; 			// Y축 좌표 배열(row)
	protected int c[]; 			// X축 좌표 배열(column)
	protected TetrisData data; 	// 테트리스 내부 데이터
	protected Point center; 	// 조각의 중심 좌표
	public boolean copied = false;
	public Piece(TetrisData data) {
		r = new int[4];
		c = new int[4];
		this.data = data;
		center = new Point(5, 0);
	}

	public abstract int getType();

	public abstract int roteType();

	public int getX() {
		return center.x;
	}

	public int getY() {
		return center.y;
	}

	// 값 복사
	public boolean copy() { 
		boolean value = false;
		int x = getX();
		int y = getY();

		// 게임 종료 상황
		if (getMinY() + y <= 0) {
			value = true;
		}

		if (!copied) {
			for (int i = 0; i < 4; i++) {
				data.setAt(y + r[i], x + c[i], getType());
			}
			copied = true;
		}
		
		return value;
	}

	// 다른 조각과 겹치는지 파악
	public boolean isOverlap(int dir) { 
		int x = getX();
		int y = getY();
		switch (dir) {
		case 0: // 아래
			for (int i = 0; i < r.length; i++) {
				if (data.getAt(y + r[i] + 1, x + c[i]) != 0) {
					return true;
				}
			}
			break;
		case 1: // 왼쪽
			for (int i = 0; i < r.length; i++) {
				if (data.getAt(y + r[i], x + c[i] - 1) != 0) {
					return true;
				}
			}
			break;
		case 2: // 오른쪽
			for (int i = 0; i < r.length; i++) {
				if (data.getAt(y + r[i], x + c[i] + 1) != 0) {
					return true;
				}
			}
			break;
		}
		return false;
	}

	public int getMinX() {
		int min = c[0];
		for (int i = 1; i < c.length; i++) {
			if (c[i] < min) {
				min = c[i];
			}
		}
		return min;
	}

	public int getMaxX() {
		int max = c[0];
		for (int i = 1; i < c.length; i++) {
			if (c[i] > max) {
				max = c[i];
			}
		}
		return max;
	}

	public int getMinY() {
		int min = r[0];
		for (int i = 1; i < r.length; i++) {
			if (r[i] < min) {
				min = r[i];
			}
		}
		return min;
	}

	public int getMaxY() {
		int max = r[0];
		for (int i = 1; i < r.length; i++) {
			if (r[i] > max) {
				max = r[i];
			}
		}
		return max;
	}

	// 아래로 이동
	public boolean moveDown() { 
		if (center.y + getMaxY() + 1 < TetrisData.ROW) {
			if (isOverlap(DOWN) != true) {
				center.y++;
			} else {
				return true;
			}
		} else {
			return true;
		}
		return false;
	}

	// 왼쪽으로 이동
	public void moveLeft() { 
		if (center.x + getMinX() - 1 >= 0)
			if (isOverlap(LEFT) != true) {
				center.x--;
			} else
				return;
	}

	// 오른쪽으로 이동
	public void moveRight() { 
		if (center.x + getMaxX() + 1 < TetrisData.COL)
			if (isOverlap(RIGHT) != true) {
				center.x++;
			} else
				return;
	}

	// 조각 회전
	public void rotate() { 
		int rc = roteType();
		if (rc <= 1)
			return;

		if (rc == 2) {
			rotate4();
		} else {
			rotate4();
		}
	}

	// 조각 회전
	public void rotate4() {
		if (center.x > 0 && center.x < TetrisData.COL - 1) {	// 양쪽 끝에서 모든 조각의 회전을 제한 
			
			// 오른쪽 끝에서 Bar 조각의 회전을 제한
			if (center.x == TetrisData.COL - 2) {
				if (getMinX() == 0 && getMinY() == -2)
					return;
			}
			
			// 왼쪽 끝에서 Bar 조각의 회전을 제한
			if (center.x == 1) {
				if (getMinX() == 0 && getMaxY() == 2)
					return;
			}
			
			for (int i = 0; i < 4; i++) {
				int temp = c[i];
				c[i] = -r[i];
				r[i] = temp;
			}
		}
	}
	
	public void moveBottom() {
		for(int i = 0; i < TetrisData.ROW - 1; i++) {
			this.moveDown();
		}
	}
}
