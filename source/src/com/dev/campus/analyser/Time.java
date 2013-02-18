package src.com.dev.campus.analyser;

public class Time {
	private int hours = -1;
	private int minutes = -1;
	
	public Time(int h, int m){
		hours = h;
		minutes = m;
	}
	
	public Time(String time) {
		if(time.matches(".*[0-2]?[0-9][h:][0-5][0-9].*")) {
			String[] strings = time.split(".*^[0-9]");
			hours = Integer.parseInt(strings[0]);
			minutes = Integer.parseInt(strings[1]);
		} else {
			System.err.println("The string did not contain a valid date");
		}
	}
	
	public int getHours() {
		return hours;
	}
	
	public int getMinutes() {
		return minutes;
	}
	
	public String toString() {
		return this.hours + ":" + this.minutes;
	}
}
