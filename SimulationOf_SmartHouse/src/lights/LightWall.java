package lights;

public class LightWall extends Light{		//свет настенный - должен по-своему светить
	
	public LightWall(){}
	public LightWall(int x, int y, int rad) {
		this.x = x;
		this.y = y;
		this.rad = rad;
	}
	
	public void turnOn() {
		this.flare=true;
	}
	public void turnOf() {
		this.flare=false;
	}
}