package tetris;

public class J extends Piece {
	
	public J(TetrisData data) {
		super(data);
		c[0] = -1;		r[0] =  0;
		c[1] =  1;		r[1] =  0;
		c[2] = -1;		r[2] = -1;
		c[3] =  0;		r[3] =  0;
	}
	
	public int getType() {
		return 2;
	}
	
	public int roteType() {
		return 4;
	}
}
