package sensor;

import alive.Man;
import SmartHouse.SmartHouse;
import rooms.Room;

public class SensorMan {			//сенсор, который видит человечка
	protected int x, y;					//его местонахождение
	protected int radius=4*SmartHouse.n;				//его зона видимости

	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}

	public SensorMan(){}
	public SensorMan(int x, int y) {
		this.x=x;
		this.y=y;
	}

	private Room whereSens(Room[] flat) {			//в какой комнате сенсор
		if ((this.getX()>flat[0].getX1()&&this.getX()<flat[0].getX2())&&(this.getY()>flat[0].getY1()&&this.getY()<flat[0].getY2()))
			return flat[0];
		if ((this.getX()>flat[1].getX1()&&this.getX()<flat[1].getX2())&&(this.getY()>flat[1].getY1()&&this.getY()<flat[1].getY2()))
			return flat[1];
		if ((this.getX()>flat[2].getX1()&&this.getX()<flat[2].getX2())&&(this.getY()>flat[2].getY1()&&this.getY()<flat[2].getY2()))
			return flat[2];
		else return flat[3];
	}

	public boolean seeMan(Room[] flat, Man man) {
		if (this.whereSens(flat)==man.whereMan()&&man.getA()<=this.getX()+radius&&man.getA()>=this.getX()-radius&&man.getB()<=this.getY()+radius&&man.getB()>=this.getY()-radius)
			return true;
		else
			return false;
	}
}