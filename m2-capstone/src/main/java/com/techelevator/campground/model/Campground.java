package com.techelevator.campground.model;

public class Campground {

	private int campgroundId;
	private int  parkId;
	private String name;
	private String openFrom;
	private String openTo;
	private double dailyFee;
	
		
	public int getCampgroundId() {
		return campgroundId;
	}
	public int getParkId() {
		return parkId;
	}
	public String getName() {
		return name;
	}
	public String getOpenFrom() {
		return openFrom;
	}
	public String getOpenTo() {
		return openTo;
	}
	public double getDailyFee() {
		return dailyFee;
	}
	
	public void setCampgroundId(int campgroundId) {
		this.campgroundId = campgroundId;
	}
	public void setParkId(int parkId) {
		this.parkId = parkId;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setOpenFrom(String openFrom) {
		this.openFrom = openFrom;
	}
	public void setOpenTo(String openTo) {
		this.openTo = openTo;
	}
	public void setDailyFee(double dailyFee) {
		this.dailyFee = dailyFee;
	}
}
