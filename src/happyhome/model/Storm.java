package happyhome.model;

public class Storm extends Disaster {

	protected Type type;
	
	public enum Type {
		severe, severeIce, coastal
	}

	public Storm(int disasterId, County county, int year, String disasterType, String description, Type type) {
		super(disasterId, county, year, disasterType, description);
		this.type = type;
	}
	
	public Storm(int disasterId) {
		super(disasterId);
	}
	
	public Storm(County county, int year, String disasterType, String description, Type type) {
		super(county, year, disasterType, description);
		this.type = type;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
}
