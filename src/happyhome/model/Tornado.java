package happyhome.model;

public class Tornado extends Disaster {
	
	protected Classification classification;
	
	public enum Classification {
		weak, strong, violent
	}

	public Tornado(int disasterId, County county, int year, String disasterType,
			String description, Classification classification) {
		super(disasterId, county, year, disasterType, description);
		this.classification = classification;
	}
	
	public Tornado(int disasterId) {
		super(disasterId);
	}
	
	public Tornado(County county, int year, String disasterType,
			String description, Classification classification) {
		super(county, year, disasterType, description);
		this.classification = classification;
	}

	public Classification getClassification() {
		return classification;
	}

	public void setClassification(Classification classification) {
		this.classification = classification;
	}
}
