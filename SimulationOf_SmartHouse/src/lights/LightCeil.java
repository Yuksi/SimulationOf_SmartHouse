package lights;

public class LightCeil extends Light{		//свет потолочный - должен по-своему светить
	
	public LightCeil(){}
	public LightCeil(int x, int y, int rad) {
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