package com.techelevator.campground.model;

public class Site {
	
	private int siteId;
	private int campgroundId;
	private int siteNumber;
	private int maxOccupancy;
	private boolean accessable;
	private int maxRvLength;
	private boolean utilities;
	private double cost;
	
	
	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}
	public int getSiteId() {
		return this.siteId;
	}
	
	public void setCampgroundId(int campgroundId) {
		this.campgroundId = campgroundId;
	}
	public int getCampgroundId() {
		return this.campgroundId;
	}
	
	public void setSiteNumber(int siteNumber) {
		this.siteNumber = siteNumber;
	}
	public int getSiteNumber() {
		return this.siteNumber;
	}
	
	public void setMaxOccupancy(int maxOccupancy) {
		this.maxOccupancy = maxOccupancy;
	}
	public int getMaxOccupancy() {
		return this.maxOccupancy;
	}
	
	public void setAccessable(boolean accessable) {
		this.accessable = accessable;
	}
	public boolean isAccessable() {
		return this.accessable;
	}
	
	public void setMaxRvLength(int maxRvLength) {
		this.maxRvLength = maxRvLength;
	}
	public int getMaxRvLength() {
		return this.maxRvLength;
	}
	
	public void setUtilities(boolean utilities) {
		this.utilities = utilities;
	}
	public boolean isUtilities() {
		return this.utilities;
	}
	
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
}
