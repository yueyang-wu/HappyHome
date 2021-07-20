package happyhome.model;

public class CountyHousePrice extends County{

	protected int currentPrice;

	public CountyHousePrice(String fipsCountyCode, String state,
			String countyName, int popularityIndex, int currentPrice) {
		super(fipsCountyCode, state, countyName, popularityIndex);
		this.currentPrice = currentPrice;
	}
	
	public CountyHousePrice(String fipsCountyCode) {
		super(fipsCountyCode);
	}

	public int getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(int currentPrice) {
		this.currentPrice = currentPrice;
	}
	
	
}
