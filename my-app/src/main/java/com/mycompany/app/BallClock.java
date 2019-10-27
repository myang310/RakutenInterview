package main.java.com.mycompany.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Stack;

import org.json.simple.JSONValue;


public class BallClock {
	private Queue<Integer> queue = new LinkedList<>();
	private Stack<Integer> hours = new Stack<>();
	private Stack<Integer> fiveMinutes = new Stack<>();
	private Stack<Integer> minutes = new Stack<>();
	private MinutesCounter counter = new MinutesCounter();
	
	public BallClock(int ballCount) {
		for (int i = 1; i <= ballCount; i++) {
			queue.add(i);
		}
	}
	
	private String getStateAsJSON() {
		Map<String, List<Integer>> map = new HashMap<>();
		List<Integer> minutes = new ArrayList<>(this.minutes);
		List<Integer> fiveMinutes = new ArrayList<>(this.fiveMinutes);
		List<Integer> hours = new ArrayList<>(this.hours);
		List<Integer> queue = new ArrayList<>(this.queue);
		map.put("Min", minutes);
		map.put("FiveMin", fiveMinutes);
		map.put("Hour", hours);
		map.put("Main", queue);
		
		return JSONValue.toJSONString(map);
	}
	
	private void runOneMinute() {
		try {
			Integer ballNumber = this.queue.remove();
			
			if (this.minutes.size() == 4) {
				while (this.minutes.size() > 0) {
					this.queue.add(this.minutes.pop());
				}
				
				if (this.fiveMinutes.size() == 11) {
					while (this.fiveMinutes.size() > 0) {
						this.queue.add(this.fiveMinutes.pop());
					}
					
					if (this.hours.size() == 11) {
						while (this.hours.size() > 0) {
							this.queue.add(this.hours.pop());
						}
						this.queue.add(ballNumber);
					} else {
						this.hours.push(ballNumber);
					}
				} else {
					this.fiveMinutes.push(ballNumber);
				}
			} else {
				this.minutes.push(ballNumber);
			}
			
			this.counter.incrementOneMinute();
			
		} catch(NoSuchElementException exception) {
			exception = null;
		}
	}
	
	private int getBallCount() {
		return this.queue.size() + this.hours.size() + this.fiveMinutes.size() + this.minutes.size();
	}
	
	private int minutesToDays(int minutes) {
		return minutes / (60 * 24);
	}
	
	public String daysUntilCycle() {
		String startState = this.getStateAsJSON();
		
		this.runOneMinute();
		String currentState = this.getStateAsJSON();
		
		while (!currentState.equals(startState)) {
			this.runOneMinute();
			currentState = this.getStateAsJSON();
		}
		
		int days = this.minutesToDays(this.counter.getMinutes());
		
		return String.format("%d balls cycle after %d days.", this.getBallCount(), days);
	}
	
	public String getStateAfterMinutes(int minutes) {
		for (int i = 0; i < minutes; i++) {
			this.runOneMinute();
		}
		
		return this.getStateAsJSON();
	}
	
}
