package happyhome.model;

public class Drought extends Disaster {

	protected Classification classification;
	
	public enum Classification {
		abnormal, moderate, severe, extreme, exceptional
	}

	public Drought(int disasterId, County county, int year, String disasterType, String description,
			Classification classification) {
		super(disasterId, county, year, disasterType, description);
		this.classification = classification;
	}
	
	public Drought(int disasterId) {
		super(disasterId);
	}

	public Classification getClassification() {
		return classification;
	}

	public void setClassification(Classification classification) {
		this.classification = classification;
	}
}
