package com.techelevator.campground.view;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import com.techelevator.campground.model.Campground;
import com.techelevator.campground.model.Park;
import com.techelevator.campground.model.Reservation;
import com.techelevator.campground.model.Site;

public class Menu {
	private PrintWriter out;
	private Scanner in;

	public Menu(InputStream input, OutputStream output) {
		this.out = new PrintWriter(output);
		this.in = new Scanner(input);
	}

	public Object getChoiceFromOptions(List<Object> options, boolean quit) {
		return getChoiceFromOptions(options, quit, false, false);
	}
	public Object getChoiceFromOptions(List<Object> options, boolean quit, boolean reservation) {
		return getChoiceFromOptions(options, quit, reservation, false);
	}
	
	public Object getChoiceFromOptions(List<Object> options, boolean quit, boolean reservation, boolean sites) {
		Object choice = null;
		while(choice == null) {
			displayMenuOptions(options, quit, reservation, sites);
			choice = getChoiceFromUserInput(options);
		}
		return choice;
	}
	
	public void parkInfo(Object choice) {
		Park park = (Park) choice;
		String f ="%1$-20s %2$5s"; 
		
		out.println("\n"+park.toString());
		out.println(String.format(f, "Location:", park.getLocation()));
		out.println(String.format(f, "Established:", park.getEstablishDate()));
		out.println(String.format(f, "Area:" , park.getArea()));
		out.println(String.format(f, "Annual Visitors: " , park.getVisitors()+"\n"));

		printDescription(park.getDescription());
		out.flush();
	}

	public void printCampgrounds(List <Object> campgrounds, Object choice ) {
		List<Campground> camps = new LinkedList<>();
		Park park = (Park)choice;
		for(int i = 0 ; i < campgrounds.size(); i++) {
			camps.add((Campground)campgrounds.get(i));
		}
		String f = "%1$-3s %2$-31s %3$10s %4$15s %5$15s";
		out.println();
		out.println(park+" Campgrounds");
		out.println();
		out.println(String.format(f,"", "Name", "Open", "Close", "Daily Fee"));
		out.println("------------------------------------------------------------------------------------------");
		int count = 1; 
		for( Campground camp : camps) {
			String name = camp.getName();
			String open = camp.getOpenFrom();
			String close = camp.getOpenTo();
			String fee = "$"+camp.getDailyFee()+"0";
			out.println(String.format(f,"#"+count, name, open, close, fee));	
			count++;
		}
	}
	
	public String getDatefromUser(String type) {
		out.print("\nWhat is your "+type+" date? enter dd/mm/yyyy >>> ");
		out.flush();
		return  in.nextLine();
	}
	
	public String askForName() {
		out.print("\nWhat name should the reservation be made under? enter name >>> ");
		out.flush();
		String name = in.nextLine();
		return name;
	}
	
	public void confirmReservation(int reservationNum) {
		out.println("\nThe reservation has been made and the confirmation id is "+reservationNum);	
		out.println("<<<  Thank you for supporting National Parks  >>>");
		out.flush();
	}
	
	public void invalidDate() {
		out.println("<<< Invalid date, please try again >>>");
		out.flush();		
	}
	
	private Object getChoiceFromUserInput(List<Object> options) {
		Object choice = null;
		String userInput = in.nextLine();
		try {
			if (userInput.equals("Q")) {
				return "Q";	
			}
			int selectedOption = Integer.valueOf(userInput);
			
			if(selectedOption <= options.size()) {
				choice = options.get(selectedOption - 1);
			}
		} catch(NumberFormatException e) {
		}
		if(choice == null) {
			out.println("\n*** "+userInput+" is not a valid option ***\n");
		}
		return choice;
	}
	
	private void displayMenuOptions(List<Object> options, boolean quit, boolean reservation, boolean sites) {
		out.println();
		if(reservation) {
			printCampsAsOptions(options);
		}else if(sites) {
			printSitesAsOptions(options);
		}else {
			for(int i = 0; i < options.size(); i++) {
			int optionNum = i+1;	
			out.println(optionNum+")  "+options.get(i));
			}
		}
		if(quit) {
			out.println("Q)  quit");
		}
		out.print("\nPlease choose an option >>> ");
		out.flush();
	}
	
	private void printSitesAsOptions(List<Object> options) {
		String f = "%1$-3s %2$-10s %3$10s %4$15s %5$15s %6$10s %7$10s";
		String fn = "%1$-4s %2$-10d %3$-10d %4$6s %5$14s %6$16s %7$14s";

		out.println("\nResults matching your search criteria ");
		out.println(String.format(f,"","Site No", "Max Occup.", "Accessible?", "Max RV Length","Utility", "Cost" ));
		out.println("------------------------------------------------------------------------------------------");
		for(int i = 0; i < options.size(); i++) {
			Site site = (Site)options.get(i);
			int optionNum = i+1;
			int siteN = site.getSiteId();
			int max = site.getMaxOccupancy();
			String acc = site.isAccessable()? "Yes": "No";
			String maxRv = site.getMaxRvLength() == 0 ? "N/A": ""+site.getMaxRvLength();
			String utt = site.isUtilities()? "Yes": "N/A";
			String cost = "$"+site.getCost()+"0";
			out.println(String.format(fn, optionNum+")",siteN, max, acc, maxRv, utt, cost));
		}
	}

	private void printCampsAsOptions(List<Object> options) {
		String f = "%1$-3s %2$-31s %3$10s %4$15s %5$15s";	
		out.println(String.format(f,"", "Name", "Open", "Close", "Daily Fee"));
		out.println("------------------------------------------------------------------------------------------");
		for(int i = 0; i < options.size(); i++) {
			Campground camp = (Campground)options.get(i);
			int optionNum = i+1;
			String name = camp.getName();
			String open = camp.getOpenFrom();
			String close = camp.getOpenTo();
			String fee = "$"+camp.getDailyFee()+"0";
			out.println(String.format(f, optionNum+")", name, open, close, fee));
		}
	}
	
	private void printDescription(String description) {
		String[] words = description.split(" ");
		StringBuilder sb = new StringBuilder();
		int nextBreak = 75;
		int rowLength = 0;
		for(int i = 0; i < words.length; i++){
			if(rowLength >= nextBreak) {
				sb.append("\n");
				rowLength = 0 ;
			}
			rowLength += words[i].length();
			sb.append(words[i]+" ");
		}
		out.println(sb.toString());
		out.flush();	
	}

	public void noResultsToDisplay() {
		out.println("<<< No sites available in selected time >>>\n<<< Please choose different dates >>>");
		out.flush();
	}

	public String advanceSearch() {
		out.print("\nNeed more advanced search ? enter (Y)es or (N)o >>> ");
		out.flush();
		return in.nextLine();
	}

	public List<String> askAdvancedSearchQuestions() {
		List<String> restrictions = new LinkedList<>();
		out.print("\nWhat's your party size? enter number >>> ");out.flush();
		String size = in.nextLine();
		out.print("Do you require wheelchair accessibility? Yes/No >>> ");out.flush();
		String accessible = in.nextLine();
		out.print("What's you RV size? enter 0 if N/A >>> ");out.flush();
		String rvLength = in.nextLine();
		out.print("Do you require utility hookup? Yes/No >>> ");out.flush();
		String utility = in.nextLine();
		
		restrictions.add(size);
		restrictions.add(accessible);
		restrictions.add(rvLength);
		restrictions.add(utility);
		
		return restrictions;
	}

	public void displayBookingForNext30Days(Object choice, List<Reservation> reservations) {
		Park park = (Park) choice;
		String f = "%1$-4s %2$-15s %3$5s %4$15s %5$15s";
		int counter = 1;
		out.println("\nBooked sites in "+park+" (next 30 days)");	
		if(reservations.isEmpty()) {
			out.println(">>> No results to display <<<");
		}else {
			out.println(String.format(f, "","Campground", "Site", "From", "To"));
			for(Reservation r : reservations) {
			out.println(String.format(f, "#"+counter, r.getName(), r.getSiteId(), r.getFromDate().toString(),r.getToDate().toString()));
			counter++;
			}	
		}
		out.flush();	
	}
}
