package main.java.com.mycompany.app;

public class MinutesCounter {
	private int minutes;
	
	public MinutesCounter () {
		this.minutes = 0;
	}
	
	public int getMinutes() {
		return this.minutes;
	}
	
	public void incrementOneMinute() {
		this.minutes++;
	}
}
