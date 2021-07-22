package happyhome.model;

public class Typhoon extends Disaster {

	protected Category category;
	
	public enum Category {
		one, two, three, four, five
	}

	public Typhoon(int disasterId, County county, int year, String disasterType, String description,
			Category category) {
		super(disasterId, county, year, disasterType, description);
		this.category = category;
	}
	
	public Typhoon(int disasterId) {
		super(disasterId);
	}
	
	public Typhoon(County county, int year, String disasterType, String description,
			Category category) {
		super(county, year, disasterType, description);
		this.category = category;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
}
