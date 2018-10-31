package com.techelevator.campground.model.jdbc;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.*;
import com.techelevator.campground.model.Park;
public class JDBCParkDAOIntegrationTest extends DAOIntegrationTest{
	
	private JDBCParkDAO parkDAO;
	private JdbcTemplate jdbcTemplate;
	
	@Before 
	public void setup() {
		parkDAO = new JDBCParkDAO(dataSource);
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Test
	public void get_all_parks() {
		String sql = "select count(*) from park ";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
		result.next();
		int count = result.getInt("count");
		
		Assert.assertEquals(count, parkDAO.getParks().size());
		
	}
	
	@Test
	public void verify_content_of_park() {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String insertSql = "insert into park(name, location, establish_date, area, visitors, description) " +
							"values ('Zap Cod', 'Maine', '2005-01-02','12240', '56550', 'A lovely land.')";
		jdbcTemplate.update(insertSql);
		
		List<Park> parks = parkDAO.getParks();
		int size = parks.size();
		
		Assert.assertTrue(size > 0);
		Assert.assertEquals("Zap Cod", parks.get(size - 1).getName());
		Assert.assertEquals("Maine", parks.get(size - 1).getLocation());
		Assert.assertEquals("A lovely land.", parks.get(size - 1).getDescription());
	}

}
