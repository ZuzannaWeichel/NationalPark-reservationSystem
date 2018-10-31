package com.techelevator.campground.model;

import java.time.LocalDate;
import java.util.List;

public interface ReservationDAO {
	
	public int bookReservation(int siteId, LocalDate fromDate, LocalDate toDate, String name);
	
	public List<Reservation> getReservationsWithinThirtyDays(Object choice);

}
