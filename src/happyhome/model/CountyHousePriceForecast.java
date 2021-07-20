package happyhome.model;

public class CountyHousePriceForecast extends County {

	protected double homePriceForecast;

	public CountyHousePriceForecast(String fipsCountyCode, String state,
			String countyName, int popularityIndex, double homePriceForecast) {
		super(fipsCountyCode, state, countyName, popularityIndex);
		this.homePriceForecast = homePriceForecast;
	}
	
	public CountyHousePriceForecast(String fipsCountyCode) {
		super(fipsCountyCode);
	}

	public double getHomePriceForecast() {
		return homePriceForecast;
	}

	public void setHomePriceForecast(double homePriceForecast) {
		this.homePriceForecast = homePriceForecast;
	}
}
