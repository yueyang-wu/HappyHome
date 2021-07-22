package happyhome.model;

public class Hurricane extends Disaster {

	protected Category category;
	
	public enum Category {
		one, two, three, four, five
	}

	public Hurricane(int disasterId, County county, int year, String disasterType, String description,
			Category category) {
		super(disasterId, county, year, disasterType, description);
		this.category = category;
	}
	
	public Hurricane(int disasterId) {
		super(disasterId);
	}
	
	public Hurricane(County county, int year, String disasterType, String description,
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
