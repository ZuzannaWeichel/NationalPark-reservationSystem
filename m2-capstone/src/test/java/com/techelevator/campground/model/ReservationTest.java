package com.techelevator.campground.model;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class ReservationTest {
	
	Reservation reserve;
	
	@Before
	public void setup() {
		reserve = new Reservation();
	}
	
	@Test
	public void verify_id() {
		reserve.setReservationId(1);
		
		Assert.assertEquals(1, reserve.getReservationId());
	}
	
	@Test
	public void verify_blank_id() {		
		Assert.assertEquals(0, reserve.getReservationId());
	}
	
	@Test
	public void verify_site_id() {
		reserve.setSiteId(2);
		
		Assert.assertEquals(2, reserve.getSiteId());
		
	}
	
	@Test
	public void verify_blank_site_id() {
		Assert.assertEquals(0, reserve.getSiteId());
		
	}
	
	@Test
	public void verify_name() {
		reserve.setName("Tony Pajamas");
		
		Assert.assertEquals("Tony Pajamas", reserve.getName());
	}

	@Test
	public void verify_blank_name() {
		Assert.assertEquals(null, reserve.getName());
	}
	
	@Test
	public void verify_from_date() throws ParseException {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String date = "01/03/2018";
		
		//convert String to LocalDate
		LocalDate localDate = LocalDate.parse(date, formatter);
		reserve.setFromDate(localDate);
		
		Assert.assertEquals(localDate , reserve.getFromDate());	
	}
	
	@Test
	public void verify_blank_from_date() {
		Assert.assertEquals(null, reserve.getFromDate());
	}
	
	@Test
	public void verify_to_date() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		String date = "12/22/2018";
		LocalDate localDate = LocalDate.parse(date, formatter);
		reserve.setToDate(localDate);
		
		Assert.assertEquals(localDate, reserve.getToDate());
	}
	
	@Test
	public void verify_create_date() {
		reserve.setToDate(LocalDate.now());
		
		Assert.assertEquals(LocalDate.now(), reserve.getToDate());
	}
	
	@Test
	public void verify_blank_to_date() {
		Assert.assertEquals(null, reserve.getToDate());
	}
	
	@Test
	public void verify_blank_create_date() {
		Assert.assertEquals(null, reserve.getCreateDate());
	}


}
