package com.techelevator.campground.model;

import java.time.LocalDate;
import java.util.List;

public interface SiteDAO {
	
	public List<Site> getAvailableSites(Object camp, List<LocalDate> dates);
	
	public List<Site> getAvailableSitesWithRestrictions(Object camp, List<LocalDate> dates, List<String> restrictions);
}
