package com.techelevator.campground.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CampgroundTest {
	
	Campground camp;
	
	@Before
	public void setup() {
		camp = new Campground();
	}
	
	@Test
	public void verify_campground_id() {
	camp.setCampgroundId(1);
	
	Assert.assertEquals(1, camp.getCampgroundId());
	}
	
	@Test
	public void verify_blank_campground_id() {
		Assert.assertEquals(0, camp.getCampgroundId());
	}
	
	@Test
	public void verify_park_id() {
		camp.setParkId(3);
		
		Assert.assertEquals(3, camp.getParkId());
	}
	
	@Test
	public void verify_name() {
		camp.setName("Berle");
		
		Assert.assertEquals("Berle", camp.getName());
	}
	
	@Test
	public void verify_blank_name() {
		Assert.assertEquals(null, camp.getName());
	}
	
	@Test
	public void verify_open_from_mm() {
		camp.setOpenFrom("January");
		
		Assert.assertEquals("January", camp.getOpenFrom());
	}
	
	@Test
	public void verify_open_to_mm() {
		camp.setOpenFrom("April");
		
		Assert.assertEquals("April", camp.getOpenFrom());
	}
	
	@Test
	public void verify_daily_fee() {
		camp.setDailyFee(45);
		
		Assert.assertEquals(45, camp.getDailyFee(), 0.0);
	}

}
