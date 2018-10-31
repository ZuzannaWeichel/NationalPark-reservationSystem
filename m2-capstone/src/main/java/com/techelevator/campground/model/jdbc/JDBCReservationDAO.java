package com.techelevator.campground.model.jdbc;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.campground.model.Park;
import com.techelevator.campground.model.Reservation;
import com.techelevator.campground.model.ReservationDAO;

public class JDBCReservationDAO implements ReservationDAO {
	
	private JdbcTemplate jdbcTemplate;
	
	public JDBCReservationDAO (DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public int bookReservation(int siteId, LocalDate fromDate, LocalDate toDate, String name) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String from = fromDate.toString();
		String to = toDate.toString();
		LocalDate fDate = LocalDate.parse(from, formatter);
		LocalDate tDate = LocalDate.parse(to, formatter);
		LocalDate now = LocalDate.now();
		String sql = "INSERT INTO reservation VALUES (default, ?, ?, ?, ?, ?) RETURNING reservation_id";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, siteId, name, fDate, tDate, now );
		result.next();
		int reservationId = result.getInt("reservation_id");
		
		return reservationId;
	}
	
	@Override
	public List<Reservation> getReservationsWithinThirtyDays(Object choice) {
		Park park = (Park) choice;
		String sql = "select c.name, s.site_number, from_date, to_date from campground c " + 
				"join site s on c.campground_id = s.campground_id " + 
				"left join reservation r on s.site_id = r.site_id " + 
				"where s.site_id in (select s.site_id from site s join reservation r on s.site_id = r.site_id where from_date between current_date and (current_date + 30))" + 
				"and park_id = ?;";
		
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, park.getParkId());
		
		return mapRowToReservation(result);
	}
	
	private LinkedList<Reservation> mapRowToReservation(SqlRowSet result){
		LinkedList<Reservation> reservations = new LinkedList<>();
		while(result.next()) {
			Reservation reservation = new Reservation();
			reservation.setParkName(result.getString("name"));
			reservation.setSiteNumber(result.getInt("site_number"));
			reservation.setFromDate(result.getDate("from_date").toLocalDate());
			reservation.setToDate(result.getDate("to_date").toLocalDate());
			reservations.add(reservation);
		}
		return reservations;
	}
}
