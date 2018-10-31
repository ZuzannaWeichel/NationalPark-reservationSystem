package com.techelevator.campground.model.jdbc;

import java.util.LinkedList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.campground.model.Park;
import com.techelevator.campground.model.ParkDAO;

public class JDBCParkDAO implements ParkDAO{
	
	JdbcTemplate jdbcTemplate;
	
	public JDBCParkDAO(DataSource dS) {
		jdbcTemplate = new JdbcTemplate(dS);
	}
	@Override
	public List<Park> getParks() {
		String sql = "select * from park "
				+ "order by name";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
		
		return mapRowToPark(result);
	}
	
	private List<Park> mapRowToPark(SqlRowSet result) {
		List<Park> parks = new LinkedList<Park>();
		while(result.next()) {
			Park park = new Park();
			park.setParkId(result.getInt("park_id"));
			park.setName(result.getString("name"));
			park.setLocation(result.getString("location"));
			park.setEstablishDate(result.getDate("establish_date").toLocalDate());
			park.setArea(result.getInt("area"));
			park.setVisitors(result.getInt("visitors"));
			park.setDescription(result.getString("description"));
			parks.add(park);
		}
		return parks;
	}

}
