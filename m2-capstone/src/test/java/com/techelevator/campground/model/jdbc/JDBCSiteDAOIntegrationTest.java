package com.techelevator.campground.model.jdbc;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import com.techelevator.DAOIntegrationTest;
import com.techelevator.campground.model.Campground;
import com.techelevator.campground.model.Site;

public class JDBCSiteDAOIntegrationTest extends DAOIntegrationTest{
	
	private JDBCSiteDAO siteDAO;
	private JdbcTemplate jdbcTemplate;
	
	@Before 
	public void setup() {
		siteDAO = new JDBCSiteDAO(dataSource);
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Test
	public void verify_get_available_sites() {
		String from = "12/01/2018";
		String to = "17/01/2018";
		Campground camp = new Campground();
		camp.setCampgroundId(2);
		camp.setParkId(1);
		camp.setOpenFrom("01");
		camp.setOpenTo("12");
		camp.setDailyFee(30);
		LinkedList<Site> sites = siteDAO.getAvailableSites(camp, convertDate(from, to));
		int size = sites.size();
		
		Assert.assertEquals(size, siteDAO.getAvailableSites(camp, convertDate(from, to)).size());
	}
	
	@Test
	public void verify_get_available_sites_with_restrictions() {
		String from = "12/01/2018";
		String to = "17/01/2018";
		List<String> restrictions = new LinkedList<String>();
		String sql = "insert into site "+
					"values(default, 7, 1000, 8, true, 100, true)";
		jdbcTemplate.update(sql);
		restrictions.add("6");
		restrictions.add("Yes");
		restrictions.add("0");
		restrictions.add("No");
		Campground camp = new Campground();
		camp.setCampgroundId(2);
		camp.setParkId(1);
		camp.setOpenFrom("01");
		camp.setOpenTo("12");
		camp.setDailyFee(30);
		
		List<Site> sites = siteDAO.getAvailableSitesWithRestrictions(camp, convertDate(from, to), restrictions);
		int size = sites.size();
		
		Assert.assertTrue(size >= 0);
		Assert.assertFalse(sites == siteDAO.getAvailableSites(camp, convertDate(from, to)));
		
	}
	private List<LocalDate> convertDate(String from, String to){
		List<LocalDate> dates = new LinkedList<>();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String fromDate = from;
		LocalDate fDate = LocalDate.parse(fromDate, formatter);
		dates.add(fDate);
		String toDate = to;
		LocalDate tDate = LocalDate.parse(toDate, formatter);
		dates.add(tDate);
		return dates; 
	}

}
