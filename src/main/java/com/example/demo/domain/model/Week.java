package com.example.demo.domain.model;

import java.util.Date;

public class Week {
	private Date[] weekday = new Date[7];

	public Date getWeekday(int index) {
		return weekday[index];
	}

	public Date[] getWeekday() {
		return weekday;
	}

	public void setWeekday(int index, Date weekday) {
		this.weekday[index] = weekday;
	}

}
