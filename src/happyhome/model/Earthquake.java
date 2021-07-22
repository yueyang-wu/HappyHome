package happyhome.model;

public class Earthquake extends Disaster {

	protected ClassType classType;
	
	public enum ClassType {
		minor, light, moderate, strong, major, great
	}

	public Earthquake(int disasterId, County county, int year, String disasterType, String description,
			ClassType classType) {
		super(disasterId, county, year, disasterType, description);
		this.classType = classType;
	}
	
	public Earthquake(int disasterId) {
		super(disasterId);
	}
	
	public Earthquake(County county, int year, String disasterType, String description,
			ClassType classType) {
		super(county, year, disasterType, description);
		this.classType = classType;
	}

	public ClassType getClassType() {
		return classType;
	}

	public void setClassType(ClassType classType) {
		this.classType = classType;
	}
}
