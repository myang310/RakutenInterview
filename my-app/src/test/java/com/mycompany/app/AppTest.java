package test.java.com.mycompany.app;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import main.java.com.mycompany.app.BallClock;

/**
 * Unit test for simple App.
 */
public class AppTest {
	
    @Test
    public void checkStateAfter325Minutes() {
    	BallClock clock = new BallClock(30);
    	
    	String expectedResult = "{\"Min\":[],\"Hour\":[6,12,17,4,15],\"Main\":[11,5,26,18,2,30,19,8,24,10,29,20,16,21,28,1,23,14,27,9],\"FiveMin\":[22,13,25,3,7]}";
    	assertEquals(expectedResult, clock.getStateAfterMinutes(325));
    }
    
    @Test
    public void run30BallClockUntilCycle() {
    	BallClock clock = new BallClock(30);
    	String result = clock.daysUntilCycle();
    	
        assertEquals(result, "30 balls cycle after 15 days.");
    }
    
    @Test
    public void run45BallClockUntilCycle() {
    	BallClock clock = new BallClock(45);
    	String result = clock.daysUntilCycle();
    	
        assertEquals(result, "45 balls cycle after 378 days.");
    }
}
