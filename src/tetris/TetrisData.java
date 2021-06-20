package tetris;

public class TetrisData {
	public static final int ROW = 20;
	public static final int COL = 10;

	private int data[][]; 	// ROW x COL 의 배열
	private int line; 		// 지운 줄 수

	long time;
	
	public TetrisData() {
		data = new int[ROW][COL];
		time = System.currentTimeMillis();
	}

	public int getAt(int x, int y) {
		if (x < 0 || x >= ROW || y < 0 || y >= COL)
			return 0;
		return data[x][y];
	}

	public void setAt(int x, int y, int v) {
		data[x][y] = v;
	}

	public int getLine() {
		return line;
	}

	public synchronized void removeLines() {
		NEXT: for (int i = ROW - 1; i >= 0; i--) {
			boolean done = true;
			for (int k = 0; k < COL; k++) {
				if (data[i][k] == 0) {
					done = false;
					continue NEXT;
				}
			}
			if (done) {
				line++;
				for (int x = i; x > 0; x--) {
					for (int y = 0; y < COL; y++) {
						data[x][y] = data[x - 1][y];
					}
				}

				if (i != 0) {
					for (int y = 0; y < COL; y++) {
						data[0][y] = 0;
					}
				}
			}
		}
	}

	// data 배열 초기화
	public void clear() {
		for (int i = 0; i < ROW; i++) {
			for (int k = 0; k < COL; k++) {
				data[i][k] = 0;
			}
		}
	}

	// data 배열 내용 출력
	public void dump() {
		System.out.println("======================");
		for (int i = 0; i < ROW; i++) {
			for (int k = 0; k < COL; k++) {
				System.out.print(data[i][k] + " ");
			}
			System.out.println();
		}
	}
	
	// 시간 제어 함수: 이 함수를 사용하면 
	// 특정 시간 간격이 지났을 때만 어떠한 기능을 수행하게 할 수 있음
	// 매개변수: delay(기능을 실행할 시간 주기, 즉 딜레이. 단위: 초)
	public boolean timeCtrl(long delay) {
		long systemTime =  System.currentTimeMillis();
		if (time + delay * 1000 <= systemTime) {
			time = System.currentTimeMillis();
			return true;
		}
		else {
			return false;
		}
	}
}