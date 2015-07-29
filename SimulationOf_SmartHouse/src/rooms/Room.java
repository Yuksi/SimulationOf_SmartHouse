package rooms;

import java.awt.Point;

public class Room {						//объект комната
	protected int x1, x2;						//границы комнаты по оси x
	protected int y1, y2;						//границы комнаты по оси y
	protected int k1, l1;						//координаты точки перед дверью
	protected int k2, l2;						//координаты точки за двеью
	
	private Point preDoor=new Point(k1,l1);		//точка перед дверью
	private Point preDoorHall=new Point(k2,l2);	//точка за дверью
	
	
	public Room() {}
	public Room(int x1, int x2, int y1, int y2, int k1, int l1, int k2, int l2){
		this.x1=x1;
		this.x2=x2;
		this.y1=y1;
		this.y2=y2;
		this.k1=k1;
		this.l1=l1;
		this.k2=k2;
		this.l2=l2;
	}
	public int getX1() {
		return x1;
	}
	public int getX2() {
		return x2;
	}
	public int getY1() {
		return y1;
	}
	public int getY2() {
		return y2;
	}
	public int getK1() {
		return k1;
	}
	public int getL1() {
		return l1;
	}
	public int getK2() {
		return k2;
	}
	public int getL2() {
		return l2;
	}
	
	public boolean isRoomClick(Point click) {			//проверка тут ли точка
		if (click.getX()>this.getX1()&&click.getX()<this.getX2()&&click.getY()>this.getY1()&&click.getY()<this.getY2())
			return true;
		else return false;
	}
}