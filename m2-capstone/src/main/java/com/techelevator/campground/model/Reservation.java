package com.techelevator.campground.model;

import java.time.LocalDate;


public class Reservation {
	
	private int reservationId;
	private int siteId;
	private int siteNumber;
	private String parkName;
	private String name;
	private LocalDate fromDate;
	private LocalDate toDate;
	private LocalDate createDate;
	
	public void setReservationId(int reservationId) {
		this.reservationId = reservationId;
	}
	
	public int getReservationId() {
		return this.reservationId;
	}
	
	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}
	
	public int getSiteId() {
		return this.siteId;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setFromDate(LocalDate date) {
		this.fromDate = date;
	}
	
	public LocalDate getFromDate() {
		return this.fromDate;
	}
	
	public void setToDate(LocalDate toDate) {
		this.toDate = toDate;
	}
	
	public LocalDate getToDate() {
		return this.toDate;
	}
	
	public void setCreateDate(LocalDate createDate) {
		this.createDate = createDate;
	}
	
	public LocalDate getCreateDate() {
		return this.createDate;
	}

	public int getSiteNumber() {
		return siteNumber;
	}

	public void setSiteNumber(int siteNumber) {
		this.siteNumber = siteNumber;
	}

	public String getParkName() {
		return parkName;
	}

	public void setParkName(String parkName) {
		this.parkName = parkName;
	}
}
