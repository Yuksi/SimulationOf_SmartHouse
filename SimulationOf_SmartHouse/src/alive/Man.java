package alive;

import java.awt.Point;

import rooms.Room;
import SmartHouse.SmartHouse;

public class Man {		//объект человечек, который будет двигаться к точке (в планах добавить управление им с клавитатуры кнопками стрелки)
	private int a;				//местонахождение
	private int b;

	public Man(){}
	public Man(int a, int b) {
		this.a=a;
		this.b=b;
	}
	public int getA() {
		return a;
	}
	public int getB() {
		return b;
	}

	public void stepRight() {			//движение вверх по оси x 
		this.a++;
		SmartHouse.followLights();
		SmartHouse.picPan.repaint();
		//System.out.println(whereManName()+" "+getA()/40+" "+getB()/40);
	}

	public void stepLeft() {		//движение вниз по оси x 
		this.a--;
		SmartHouse.followLights();
		SmartHouse.picPan.repaint();
		//System.out.println(whereManName()+" "+getA()/40+" "+getB()/40);
	}

	public void stepUp() {		//движение влево по оси  y 
		this.b--;
		SmartHouse.followLights();
		SmartHouse.picPan.repaint();
		//System.out.println(whereManName()+" "+getA()/40+" "+getB()/40);
	}

	public void stepDown() {		//движение вправо по оси y  - временно
		this.b++;
		SmartHouse.followLights();
		SmartHouse.picPan.repaint();
		//System.out.println(whereManName()+" "+getA()/40+" "+getB()/40);
	}

	public Room whereMan(){			//определим где человечек
		if ((this.getA()>SmartHouse.getRoom().getX1()&&this.getA()<SmartHouse.getRoom().getX2())&&(this.getB()>SmartHouse.getRoom().getY1()&&this.getB()<SmartHouse.getRoom().getY2()))
			return SmartHouse.getRoom();
		if ((this.getA()>SmartHouse.getBath().getX1()&&this.getA()<SmartHouse.getBath().getX2())&&(this.getB()>SmartHouse.getBath().getY1()&&this.getB()<SmartHouse.getBath().getY2()))
			return SmartHouse.getBath();
		if ((this.getA()>SmartHouse.getKitch().getX1()&&this.getA()<SmartHouse.getKitch().getX2())&&(this.getB()>SmartHouse.getKitch().getY1()&&this.getB()<SmartHouse.getKitch().getY2()))
			return SmartHouse.getKitch();
		else return SmartHouse.getHall();
	}
	
	public String whereManName(){			//определим где человечек
		if ((this.getA()>SmartHouse.getRoom().getX1()&&this.getA()<SmartHouse.getRoom().getX2())&&(this.getB()>SmartHouse.getRoom().getY1()&&this.getB()<SmartHouse.getRoom().getY2()))
			return "bedroom";
		if ((this.getA()>SmartHouse.getBath().getX1()&&this.getA()<SmartHouse.getBath().getX2())&&(this.getB()>SmartHouse.getBath().getY1()&&this.getB()<SmartHouse.getBath().getY2()))
			return "bathroom";
		if ((this.getA()>SmartHouse.getKitch().getX1()&&this.getA()<SmartHouse.getKitch().getX2())&&(this.getB()>SmartHouse.getKitch().getY1()&&this.getB()<SmartHouse.getKitch().getY2()))
			return "kitchen";
		else return "hallway";
	}

	public void goClick() { 						//метод движения человечка
		if (whereMan()!=SmartHouse.getHall())
			goFrom();
		if (whereMan()==SmartHouse.getHall()) 
			goFromHall();
		goPoint(SmartHouse.getClick());
	}

	private void goPoint(Point somepoint) {			//метод движения к любой точке

		while (getB()!=somepoint.getY()) {
			if (getB()<somepoint.getY()) 
				stepDown();
			else if (getB()>somepoint.getY()) 
				stepUp();
			else return;
		}
		while (getA()!=somepoint.getX()) {
			if (getA()<somepoint.getX()) 
				stepRight();
			else if (getA()>somepoint.getX()) 
				stepLeft();
			else return;
		}
	}

	private void goTo(int k, int l) {			//метод движения к любой точке
		
		while (getB()!=l) {
			if (getB()<l) 
				stepDown();
			else if (getB()>l) 
				stepUp();
			else return;
		}
		while (getA()!=k) {
			if (getA()<k) 
				stepRight();
			else if (getA()>k) 
				stepLeft();
			else return;
		}
	}

	private void goFromHall() {		
		if (SmartHouse.getClick().getX()>=SmartHouse.getHall().getK1()&&this.getA()<=SmartHouse.getHall().getK1()&&SmartHouse.getClick().getY()<=SmartHouse.getHall().getL1()) 
			goTo(SmartHouse.getHall().getK1(),SmartHouse.getHall().getL1());
		if (SmartHouse.getClick().getX()<=SmartHouse.getHall().getK1()&&this.getA()>=SmartHouse.getHall().getK1()) 
			goTo(SmartHouse.getHall().getK1(),SmartHouse.getHall().getL1());

		if (!whereMan().isRoomClick(SmartHouse.getClick())) {
			goTo(SmartHouse.whereClick().getK2(),SmartHouse.whereClick().getL2());
			goTo(SmartHouse.whereClick().getK1(),SmartHouse.whereClick().getL1());

		}
	}
	private void goFrom() {							//путь из комнаты
		if (!whereMan().isRoomClick(SmartHouse.getClick())) {
			goTo(whereMan().getK1(),whereMan().getL1());
			goTo(whereMan().getK2(),whereMan().getL2());
		}
	}
}