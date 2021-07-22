package happyhome.model;

public class Snow extends Disaster {

	protected EmergencyAdvisoryLevel emergencyAdvisoryLevel;
	
	public enum EmergencyAdvisoryLevel {
		level1, level2, level3
	}

	public Snow(int disasterId, County county, int year, String disasterType, String description,
			EmergencyAdvisoryLevel emergencyAdvisoryLevel) {
		super(disasterId, county, year, disasterType, description);
		this.emergencyAdvisoryLevel = emergencyAdvisoryLevel;
	}
	
	public Snow(int disasterId) {
		super(disasterId);
	}
	
	public Snow(County county, int year, String disasterType, String description,
			EmergencyAdvisoryLevel emergencyAdvisoryLevel) {
		super(county, year, disasterType, description);
		this.emergencyAdvisoryLevel = emergencyAdvisoryLevel;
	}

	public EmergencyAdvisoryLevel getEmergencyAdvisoryLevel() {
		return emergencyAdvisoryLevel;
	}

	public void setEmergencyAdvisoryLevel(EmergencyAdvisoryLevel emergencyAdvisoryLevel) {
		this.emergencyAdvisoryLevel = emergencyAdvisoryLevel;
	}
}
