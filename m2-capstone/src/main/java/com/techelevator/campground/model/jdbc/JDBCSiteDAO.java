package com.techelevator.campground.model.jdbc;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.campground.model.Campground;
import com.techelevator.campground.model.Site;
import com.techelevator.campground.model.SiteDAO;

public class JDBCSiteDAO implements SiteDAO {

	private JdbcTemplate jdbcTemplate;
	
	public JDBCSiteDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public LinkedList<Site> getAvailableSites(Object camp, List<LocalDate> dates) {
		Campground campground = (Campground)camp;
		String query = "select * from site " + 
				"where site_id not in(select s.site_id from site s join reservation r on s.site_id = r.site_id where to_date > ? and from_date < ?) " + 
				"and campground_id = ? LIMIT 5";
		SqlRowSet result = jdbcTemplate.queryForRowSet(query, dates.get(0), dates.get(1), campground.getCampgroundId());
		
		return mapRowToSite(result, dates, camp);
	}
	
	@Override
	public List<Site> getAvailableSitesWithRestrictions(Object camp, List<LocalDate> dates, List<String> restrictions) {
		Campground campground = (Campground)camp;
		String query = "select * from site " + 
				"where site_id not in(select s.site_id from site s join reservation r on s.site_id = r.site_id where to_date > ? and from_date < ?) " + 
				"and campground_id = ?";
		query += " and max_occupancy >= ? " +
				"and accessible = ? " +
				"and max_rv_length >= ? " +
				"and utilities = ? " +
				"limit 5";
		int occupancy = Integer.parseInt(restrictions.get(0));
		boolean access = Boolean.parseBoolean(restrictions.get(1));
		int rvLength = Integer.parseInt(restrictions.get(2));
		boolean utility = Boolean.parseBoolean(restrictions.get(3));
		
		SqlRowSet result = jdbcTemplate.queryForRowSet(query, dates.get(0), dates.get(1), campground.getCampgroundId(), occupancy, access, rvLength, utility);
		return mapRowToSite(result, dates, campground);
	}
	
	private double calculateCost(List<LocalDate> dates, Object camp) {
		Campground campground = (Campground)camp;
		LocalDate dateFrom = dates.get(0);
		LocalDate dateTo =dates.get(1);
		long intervalDays = ChronoUnit.DAYS.between(dateFrom, dateTo);
		double cost = campground.getDailyFee() * intervalDays;
		return cost;

	}
	
	private LinkedList<Site> mapRowToSite(SqlRowSet result, List<LocalDate> dates, Object camp){
		LinkedList<Site> sites = new LinkedList<>();
		while(result.next()) {
			Site site = new Site();
			site.setSiteId(result.getInt("site_id"));
			site.setCampgroundId(result.getInt("campground_id"));
			site.setSiteNumber(result.getInt("site_number"));
			site.setMaxOccupancy(result.getInt("max_occupancy"));
			site.setAccessable(result.getBoolean("accessible"));
			site.setMaxRvLength(result.getInt("max_rv_length"));
			site.setUtilities(result.getBoolean("utilities"));
			site.setCost(calculateCost(dates, camp));
			sites.add(site);
		}
		return sites;
	}
}
