package main.java.com.mycompany.app;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

import org.json.simple.JSONValue;


public class BallClock {
	private Queue<Integer> queue = new LinkedList<>();
	private Stack<Integer> hours = new Stack<>();
	private Stack<Integer> fiveMinutes = new Stack<>();
	private Stack<Integer> minutes = new Stack<>();
	private MinutesCounter counter = new MinutesCounter();
	
	private int MAX_MINUTES = 4;
	private int MAX_FIVE_MINUTES = 11;
	private int MAX_HOURS = 11;
	
	public BallClock(Integer ballCount) {
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
	
	private void emptyToQueue(Stack<Integer> stack) {
		while(!stack.empty()) {
			this.queue.add(stack.pop());
		}
	}
		
	private void runOneMinute() {
		Integer ballNumber = this.queue.remove();
		Collection<Integer> insertInto = this.minutes;
		
		if (this.minutes.size() == this.MAX_MINUTES) {
			this.emptyToQueue(this.minutes);
			insertInto = this.fiveMinutes;
			
			if (this.fiveMinutes.size() == this.MAX_FIVE_MINUTES) {
				this.emptyToQueue(this.fiveMinutes);
				insertInto = this.hours;
				
				if (this.hours.size() == this.MAX_HOURS) {
					this.emptyToQueue(this.hours);
					insertInto = this.queue;
				}
			}
		}

		insertInto.add(ballNumber);
		this.counter.incrementOneMinute();
	}
	
	private Integer getBallCount() {
		return this.queue.size() + this.hours.size() + this.fiveMinutes.size() + this.minutes.size();
	}
	
	private Integer minutesToDays(int minutes) {
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
