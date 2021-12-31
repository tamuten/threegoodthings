package com.example.demo.domain.model;

public class Week {
	private int[] weekday = new int[7];

	public int getWeekday(int index) {
		return weekday[index];
	}

	public int[] getWeekday() {
		return weekday;
	}

	public void setWeekday(int index, int weekday) {
		this.weekday[index] = weekday;
	}

}
