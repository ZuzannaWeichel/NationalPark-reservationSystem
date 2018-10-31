package com.techelevator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

import javax.security.auth.callback.LanguageCallback;
import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.metadata.GenericTableMetaDataProvider;

import com.techelevator.campground.model.CampgroundDAO;
import com.techelevator.campground.model.Park;
import com.techelevator.campground.model.ParkDAO;
import com.techelevator.campground.model.Reservation;
import com.techelevator.campground.model.ReservationDAO;
import com.techelevator.campground.model.Site;
import com.techelevator.campground.model.SiteDAO;
import com.techelevator.campground.model.jdbc.JDBCCampgroundDAO;
import com.techelevator.campground.model.jdbc.JDBCParkDAO;
import com.techelevator.campground.model.jdbc.JDBCReservationDAO;
import com.techelevator.campground.model.jdbc.JDBCSiteDAO;
import com.techelevator.campground.view.Menu;

public class CampgroundCLI {
	private Menu menu;
	private ParkDAO parkDAO;
	private CampgroundDAO campgroundDAO;
	private SiteDAO siteDAO;
	private ReservationDAO reservationDAO;
		
	public CampgroundCLI(DataSource dataSource, Menu menu) {
		this.menu = new Menu(System.in, System.out);	
		this.parkDAO = new JDBCParkDAO(dataSource);
		this.campgroundDAO = new JDBCCampgroundDAO(dataSource);
		this.siteDAO = new JDBCSiteDAO(dataSource);
		this.reservationDAO = new JDBCReservationDAO(dataSource);
			
	}
	
	private List<Object> setParkInfoMenu(boolean viewCamp) {
		List<Object> parkInfoMenu = new LinkedList<>();;
		if(viewCamp) {
			parkInfoMenu.add("View Campgrounds");
		}
		parkInfoMenu.add("Search for Reservation");
		parkInfoMenu.add("Return to Previous Screen");
		return parkInfoMenu;
	}
	
	public void run() {
		List<Object> choices = new LinkedList<Object>();
		choices.addAll(parkDAO.getParks());
		while(true) {
			Object choice = menu.getChoiceFromOptions(choices, true);
			if (choice.equals("Q")) break;
			
			while(true) {
				menu.parkInfo(choice);
				Object selection = menu.getChoiceFromOptions(setParkInfoMenu(true),false);
				
				if (selection.equals("View Campgrounds")) {
					menu.printCampgrounds(campgroundDAO.viewCampgrounds(choice), choice);
					Object pick = menu.getChoiceFromOptions(setParkInfoMenu(false), false);
				
					if (pick.equals("Search for Reservation")) {
						Object campground = menu.getChoiceFromOptions(campgroundDAO.viewCampgrounds(choice), true, true);
					
						if(campground.equals("Q")) break;
						
						if(makeReservation(campground).equals("Q")); break;
					}
					if(pick.equals("Return to Previous Screen")) continue;		
				}
				if (selection.equals("Search for Reservation")) {
					List<Reservation> reservations = reservationDAO.getReservationsWithinThirtyDays(choice);
					menu.displayBookingForNext30Days(choice,reservations);
				}
				if (selection.equals("Return to Previous Screen")) break;
			}	
		}
	}

	private String makeReservation(Object campground) {
		List<Object> sites = new LinkedList<>();
		List<LocalDate> dates = null;
		while(sites.isEmpty()) {
			dates = askForReservation();
			
			if(menu.advanceSearch().equals("Y")){
				List<String> restrictions = menu.askAdvancedSearchQuestions();
				siteDAO.getAvailableSitesWithRestrictions(campground, dates, restrictions);
			}
			
			sites.addAll(siteDAO.getAvailableSites(campground, dates));
			if(sites.isEmpty()) {
				menu.noResultsToDisplay();
			}
		}
		Object pick = menu.getChoiceFromOptions(sites, true, false, true);
		if(pick.equals("Q")) {
			return "Q";
		}
		Site site = (Site) pick;
		String name = menu.askForName();
		int reservationNum = reservationDAO.bookReservation(site.getSiteId(), dates.get(0), dates.get(1), name);
		menu.confirmReservation(reservationNum);
		return "";
	}
	//private void makeParkWideSearch
	
	private List<LocalDate> askForReservation() {	
		List<LocalDate> dates = new LinkedList<>();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate fDate = null;
		LocalDate tDate = null;
		LocalDate now = LocalDate.now();
		while( fDate == null || tDate == null || fDate.isBefore(now) || tDate.isBefore(now)) {
			try{
				String fromDate = menu.getDatefromUser("arrival");
				fDate = LocalDate.parse(fromDate, formatter);
				String toDate = menu.getDatefromUser("departure");
				tDate = LocalDate.parse(toDate, formatter);
			}catch(Exception e) {
			menu.invalidDate();
			}	
		}	
		dates.add(fDate);
		dates.add(tDate);
		return dates; 
	}
	
	
	public static void main(String[] args) {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/campground");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");
		
		Menu menu = new Menu(System.in, System.out);
		CampgroundCLI application = new CampgroundCLI(dataSource, menu);
		application.run();
	}
	
}
