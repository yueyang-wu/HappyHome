package happyhome.model;

public class Volcano extends Disaster {

	protected AlertLevel alertLevel;
	
	public enum AlertLevel {
		minorUnrest, moderateUnrest, minorEruption, moderateEruption, majorEruption
	}

	public Volcano(int disasterId, County county, int year, String disasterType, String description,
			AlertLevel alertLevel) {
		super(disasterId, county, year, disasterType, description);
		this.alertLevel = alertLevel;
	}
	
	public Volcano(int disasterId) {
		super(disasterId);
	}
	
	public Volcano(County county, int year, String disasterType, String description,
			AlertLevel alertLevel) {
		super(county, year, disasterType, description);
		this.alertLevel = alertLevel;
	}

	public AlertLevel getAlertLevel() {
		return alertLevel;
	}

	public void setAlertLevel(AlertLevel alertLevel) {
		this.alertLevel = alertLevel;
	}
}
