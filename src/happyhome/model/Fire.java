package happyhome.model;

public class Fire extends Disaster {

	protected DangerLevel dangerLevel;
	
	public enum DangerLevel {
		redFlag, low, moderate, high, veryHigh, extreme
	}

	public Fire(int disasterId, County county, int year, String disasterType, String description,
			DangerLevel dangerLevel) {
		super(disasterId, county, year, disasterType, description);
		this.dangerLevel = dangerLevel;
	}
	
	public Fire(int disasterId) {
		super(disasterId);
	}
	
	public Fire(County county, int year, String disasterType, String description,
			DangerLevel dangerLevel) {
		super(county, year, disasterType, description);
		this.dangerLevel = dangerLevel;
	}

	public DangerLevel getDangerLevel() {
		return dangerLevel;
	}

	public void setDangerLevel(DangerLevel dangerLevel) {
		this.dangerLevel = dangerLevel;
	}
}
