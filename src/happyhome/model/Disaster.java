package happyhome.model;

public class Disaster {

	protected int disasterId;
	protected County county;
	protected int year;
	protected String disasterType;
	protected String description;
	
	public Disaster(int disasterId, County county, int year, String disasterType, String description) {
		this.disasterId = disasterId;
		this.county = county;
		this.year = year;
		this.disasterType = disasterType;
		this.description = description;
	}
	
	public Disaster(int disasterId) {
		this.disasterId = disasterId;
	}
	
	public Disaster(County county, int year, String disasterType, String description) {
		this.county = county;
		this.year = year;
		this.disasterType = disasterType;
		this.description = description;
	}

	public int getDisasterId() {
		return disasterId;
	}

	public void setDisasterId(int disasterId) {
		this.disasterId = disasterId;
	}

	public County getCounty() {
		return county;
	}

	public void setCounty(County county) {
		this.county = county;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getDisasterType() {
		return disasterType;
	}

	public void setDisasterType(String disasterType) {
		this.disasterType = disasterType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
