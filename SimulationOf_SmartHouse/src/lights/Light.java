package lights;

public abstract class Light {		//абстрактный свет
	protected int x, y;
	protected int rad;
	protected boolean flare;
	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getRad() {
		return rad;
	}
	public boolean getFlare() {
		return flare;
	}
	
	abstract public void turnOn();
	abstract public void turnOf();
}