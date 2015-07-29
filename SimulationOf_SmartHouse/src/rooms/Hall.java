package rooms;

import java.awt.Point;

public class Hall extends Room {			//объект коридор - чуток сложнее комнаты
	private int x3, y3;							//ещё одна граница коридора, указывающая криволинейность
	private Point midPath1 = new Point(k1,l1);	//точки, через которые идти в определённых условиях
	private Point midPath2 = new Point(k2,l2);
	
	public Hall() {}
	public Hall(int x1, int x2, int x3, int y1, int y2, int y3, int k1, int l1, int k2, int l2) {
		super(x1,x2,y1,y2,k1,l1,k2,l2);
		this.x3=x3;
		this.y3=y3;
	}
	
	public int getX3() {
		return x3;
	}
	public int getY3() {
		return y3;
	}
	
	public boolean isRoomClick(Point click) {			//проверка тут ли точка
		if ((click.getY()>this.getY2()&&click.getY()<this.getY3()&&click.getX()>this.getX1()&&click.getX()<=this.getX2())||(click.getY()>this.getY1()&&click.getY()<this.getY3()&&click.getX()>this.getX2()&&click.getX()<this.getX3()))
			return true;
		else return false;
	}
}
