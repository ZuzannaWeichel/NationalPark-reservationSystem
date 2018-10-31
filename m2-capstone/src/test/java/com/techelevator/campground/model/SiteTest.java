package com.techelevator.campground.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SiteTest {
	
	Site site;
	
	@Before
	public void setup() {
		site = new Site();
	}
	
	@Test
	public void verify_site_id() {
		site.setSiteId(1);
		
		Assert.assertEquals(1, site.getSiteId());
	}
	
	@Test
	public void verify_campground_id() {
		site.setCampgroundId(2);
		
		Assert.assertEquals(2, site.getCampgroundId());
	}
	
	@Test
	public void verify_site_number() {
		site.setSiteNumber(453);
		
		Assert.assertEquals(453, site.getSiteNumber());
	}
	
	@Test
	public void verify_max_occupancy() {
		site.setMaxOccupancy(6);
		
		Assert.assertEquals(6, site.getMaxOccupancy());
	}
	
	@Test
	public void verify_accesibility() {
		site.setAccessable(true);
		
		Assert.assertTrue(site.isAccessable());
	}
	
	@Test
	public void verify_unset_accessibility() {
		Assert.assertFalse(site.isAccessable());
	}
	
	@Test
	public void verify_max_rv_length() {
		site.setMaxRvLength(12);
		
		Assert.assertEquals(12, site.getMaxRvLength());
	}
	
	@Test
	public void verify_utilities() {
		site.setUtilities(true);
		
		Assert.assertTrue(site.isUtilities());
	}
	
	@Test
	public void verify_unset_utilities() {
		Assert.assertFalse(site.isUtilities());
	}
	
	@Test
	public void verify_cost() {
		site.setCost(12.00);
		
		Assert.assertEquals(12.00, site.getCost(), 0.0);
	}
}
