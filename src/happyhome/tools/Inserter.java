package happyhome.tools;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import happyhome.dal.*;
import happyhome.model.*;

public class Inserter {
	public static void main(String[] args) throws SQLException {
		CountyDao countyDao = CountyDao.getInstance();
		DisasterDao disasterDao = DisasterDao.getInstance();
		
		County county = new County("6037", "CA", "Los Angeles County", 0);
		county = countyDao.create(county);
		
		Disaster disaster = new Disaster(county, 2000, "type", "description");
		disaster = disasterDao.create(disaster);
		Disaster disaster2 = new Disaster(county, 2000, "type", "description");
		disaster2 = disasterDao.create(disaster2);
		
		County c = countyDao.getCountyByCountyNameAndState("Los Angeles County", "CA");
		
		System.out.println(c.getFipsCountyCode() + c.getCountyName() + c.getState());
		
		List<Disaster> disasters = disasterDao.getDisastersForCounty(county);
		
		for (Disaster d : disasters) {
			System.out.println(d.getDisasterId());
		}
	}

}
