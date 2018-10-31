package com.techelevator.campground.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ParkTest {
	
	Park park;
	
	@Before
	public void setup() {
		park = new Park();
	}
	
	@Test
	public void verify_pari_id() {
		park.setParkId(2);
		
		Assert.assertEquals(2, park.getParkId());
	}
	
	@Test
	public void verify_name() {
		park.setName("DownTrodden");
		
		Assert.assertEquals("DownTrodden", park.getName());
	}
	
	@Test
	public void verify_location() {
		park.setLocation("Canada");
		
		Assert.assertEquals("Canada", park.getLocation());
	}
	
	@Test
	public void verify_establish_date() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		String date = "12/22/1995";
		LocalDate localDate = LocalDate.parse(date, formatter);
		park.setEstablishDate(localDate);
		
		Assert.assertEquals(localDate, park.getEstablishDate());
	}
	
	@Test
	public void verify_area() {
		park.setArea(1450);
		
		Assert.assertEquals(1450, park.getArea());
	}
	
	@Test
	public void verify_visitors() {
		park.setVisitors(45300);
		
		Assert.assertEquals(45300, park.getVisitors());		
	}
	
	@Test
	public void verify_description() {
		park.setDescription("Beautiful serene park, with many bees.");
		
		Assert.assertEquals("Beautiful serene park, with many bees.", park.getDescription());
	}

}
