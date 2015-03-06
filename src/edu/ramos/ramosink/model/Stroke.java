package edu.ramos.ramosink.model;

/**
 * 
 * @author glauberrleite
 *
 */
public class Stroke {

	private long id;
	private long time;
	private long x;
	private long y;

	public Stroke(long id, long time, long x, long y) {
		super();
		this.id = id;
		this.time = time;
		this.x = x;
		this.y = y;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public long getX() {
		return x;
	}

	public void setX(long x) {
		this.x = x;
	}

	public long getY() {
		return y;
	}

	public void setY(long y) {
		this.y = y;
	}

}
