package happyhome.model;

public class Flood extends Disaster {
	
	protected Stage stage;
	
	public enum Stage {
		action, minor, moderate, major, record
	}

	public Flood(int disasterId, County county, int year, String disasterType, String description, Stage stage) {
		super(disasterId, county, year, disasterType, description);
		this.stage = stage;
	}
	
	public Flood(int disasterId) {
		super(disasterId);
	}
	
	public Flood(County county, int year, String disasterType, String description, Stage stage) {
		super(county, year, disasterType, description);
		this.stage = stage;
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}
}
