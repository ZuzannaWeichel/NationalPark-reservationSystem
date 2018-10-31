package com.techelevator.campground.model.jdbc;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.DAOIntegrationTest;
import com.techelevator.campground.model.Park;

public class JDBCCampgroundDAOIntegrationTest extends DAOIntegrationTest{
	
	private JDBCCampgroundDAO campgroundDAO;
	private JdbcTemplate jdbcTemplate;
	
	@Before
	public void setup() {
		campgroundDAO = new JDBCCampgroundDAO(dataSource);
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Test
	public void get_selection_campgrounds() {
		Park park = new Park();
		park.setParkId(2);
		List<Object> camp = campgroundDAO.viewCampgrounds(park);
		List<Object> camps = camp;
		int count = camp.size();
		
		Assert.assertEquals(count, campgroundDAO.viewCampgrounds(park).size());
		Assert.assertFalse(camps.isEmpty());
	}


}
