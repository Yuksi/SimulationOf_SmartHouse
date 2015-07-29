package controllers;

import sensor.SensorMan;
import alive.Man;
import lights.LightCeil;
import rooms.Room;

public class Controller {	
	
	private SensorMan[] sensorsMan;				//та группа сенсоров, к которой подключён данный контроллер
	private LightCeil[] lightsCeil;				//та группа света, которую данный контроллер включает
	
	public Controller(){}
	public Controller(SensorMan[] sensorsMan, LightCeil[] lCHall) {
		this.sensorsMan=sensorsMan;
		this.lightsCeil=lCHall;
	}
	
	public SensorMan[] getSens() {
		return sensorsMan;
	}
	
	public LightCeil[] getLight() {
		return lightsCeil;
	}
	
	public void turnLineCeil(Room[] flat, Man man) {			//метод управления группой света на основании данных сенсоров
		if (this.getSens()[0].seeMan(flat, man)||this.getSens()[this.getSens().length-1].seeMan(flat, man)) {
			for (int i=0; i<this.getLight().length; i++) 
				this.getLight()[i].turnOn();
		}
		else {
			for (int i=0; i<this.getLight().length; i++) 
				this.getLight()[i].turnOf();
		}
	}
	public void turnONlineCeil() {					//влключаем свет, к которому подключен контроллер
		for (int i=0; i<this.getLight().length; i++) 
			this.getLight()[i].turnOn();
	}
	public void turnOFlineCeil() {					//выключаем свет
		for (int i=0; i<this.getLight().length; i++) 
			this.getLight()[i].turnOf();
	}
}