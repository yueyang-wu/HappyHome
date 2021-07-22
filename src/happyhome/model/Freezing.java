package happyhome.model;

public class Freezing extends Disaster {

	protected Category category;
	
	public enum Category {
		one, two, three, four, five
	}

	public Freezing(int disasterId, County county, int year, String disasterType, String description,
			Category category) {
		super(disasterId, county, year, disasterType, description);
		this.category = category;
	}
	
	public Freezing(int disasterId) {
		super(disasterId);
	}
	
	public Freezing(County county, int year, String disasterType, String description,
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
