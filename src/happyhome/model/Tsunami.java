package happyhome.model;

public class Tsunami extends Disaster {

	protected AlertLevel alertLevel;
	
	public enum AlertLevel {
		informationStatement, watch, advisory, warning
	}

	public Tsunami(int disasterId, County county, int year, String disasterType, String description,
			AlertLevel alertLevel) {
		super(disasterId, county, year, disasterType, description);
		this.alertLevel = alertLevel;
	}
	
	public Tsunami(int disasterId) {
		super(disasterId);
	}
	
	public Tsunami(County county, int year, String disasterType, String description,
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
