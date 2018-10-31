package com.techelevator.campground.model.jdbc;

import java.util.LinkedList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.campground.model.Campground;
import com.techelevator.campground.model.CampgroundDAO;
import com.techelevator.campground.model.Park;

public class JDBCCampgroundDAO implements CampgroundDAO{
	
	JdbcTemplate jdbcTemplate;
	
	public JDBCCampgroundDAO(DataSource dS) {
		jdbcTemplate = new JdbcTemplate(dS);
	}

	@Override
	public List<Object> viewCampgrounds(Object choice) {
		Park park = (Park) choice;
		String sql = "select * from campground "
					+ " where park_id = ?";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, park.getParkId());
		
		return mapRowToCampground(result);
	}
	
	private List<Object> mapRowToCampground(SqlRowSet result) {
		List<Object> camps = new LinkedList<>();
		while (result.next()) {
			Campground camp = new Campground();
			camp.setCampgroundId(result.getInt("campground_id"));
			camp.setParkId(result.getInt("park_id"));
			camp.setName(result.getString("name"));
			String dateFrom = result.getString("open_from_mm");
			camp.setOpenFrom(changeDateToWord(dateFrom));
			String dateTo = result.getString("open_to_mm");
			camp.setOpenTo(changeDateToWord(dateTo));
			camp.setDailyFee(result.getDouble("daily_fee"));
			camps.add(camp);
		}
		return camps;
	}
	
	private String changeDateToWord(String date) {
		String month = "";
		switch(date) {
		case "01" : month = "January";
		break;
		case "02" : month = "February";
		break;
		case "03" : month = "March";
		break;
		case "04" : month = "April";
		break;
		case "05" : month = "May";
		break;
		case "06" : month = "June";
		break;
		case "07" : month = "July";
		break;
		case "08" : month = "August";
		break;
		case "09" : month = "September";
		break;
		case "10" : month = "October";
		break;
		case "11" : month = "November";
		break;
		case "12" : month = "December";
		break;
		}
		return month;
	}

}
