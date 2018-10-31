package com.techelevator.campground.model.jdbc;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.DAOIntegrationTest;
import com.techelevator.campground.model.Park;
import com.techelevator.campground.model.Reservation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class JDBCReservationDAOIntegrationTest  extends DAOIntegrationTest{
	
	private JDBCReservationDAO reservationDAO;
	private JdbcTemplate jdbcTemplate;
	
	@Before
	public void setup() {
		reservationDAO = new JDBCReservationDAO(dataSource);
		jdbcTemplate = new JdbcTemplate(dataSource);
		
	}
	
	@Test
	public void verify_book_reservation() {
		String from = "13/10/2018";
		String to = "17/11/2018";
		List<LocalDate> dates = convertDate(from, to);
		int id = reservationDAO.bookReservation(300, dates.get(0), dates.get(1), "Smithy Miners Corp");
		String sql = "select * from reservation where name like 'Smith%'";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
		List<Reservation> reserve = mapRowToReservation(result);
		int size = reserve.size();
		
		Assert.assertTrue(size > 0);
		Assert.assertTrue(id == reserve.get(size - 1).getReservationId());
		Assert.assertEquals("Smithy Miners Corp", reserve.get(size - 1).getName());
		Assert.assertEquals(dates.get(0), reserve.get(size - 1).getFromDate());
		Assert.assertEquals(dates.get(1), reserve.get(size - 1).getToDate());
		Assert.assertEquals(LocalDate.now(), reserve.get(size - 1).getCreateDate());
	}
	
	@Test
	public void verify_booked_reservations_within_30_days() {
		String from = "13/11/2018";
		String to = "17/11/2018";
		List<LocalDate> dates = convertDate(from, to);
		int id = reservationDAO.bookReservation(300, dates.get(0), dates.get(1), "Smithy Miners Corp");
		Park park = new Park();
		park.setParkId(1);
		List<Reservation> reservations = reservationDAO.getReservationsWithinThirtyDays(park);
		int size = reservations.size();
		
		Assert.assertTrue(size > 0);
		Assert.assertTrue(id > 0);
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
	

	private LinkedList<Reservation> mapRowToReservation(SqlRowSet result){
		LinkedList<Reservation> reservations = new LinkedList<>();
		while(result.next()) {
			Reservation reservation = new Reservation();
			reservation.setReservationId(result.getInt("reservation_id"));
			reservation.setSiteId(result.getInt("site_id"));
			reservation.setName(result.getString("name"));
			reservation.setFromDate(result.getDate("from_date").toLocalDate());
			reservation.setToDate(result.getDate("to_date").toLocalDate());
			reservation.setCreateDate(result.getDate("create_date").toLocalDate());
			reservations.add(reservation);
		}
		return reservations;
	}

}
