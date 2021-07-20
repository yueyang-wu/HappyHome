package happyhome.model;

public class County {
	protected String fipsCountyCode;
	protected String state;
	protected String countyName;
	protected int popularityIndex;
	
	public County(String fipsCountyCode, String state, String countyName, int popularityIndex) {
		this.fipsCountyCode = fipsCountyCode;
		this.state = state;
		this.countyName = countyName;
		this.popularityIndex = popularityIndex;
	}

	public County(String fipsCountyCode) {
		this.fipsCountyCode = fipsCountyCode;
	}

	public String getFipsCountyCode() {
		return fipsCountyCode;
	}

	public void setFipsCountyCode(String fipsCountyCode) {
		this.fipsCountyCode = fipsCountyCode;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountyName() {
		return countyName;
	}

	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}

	public int getPopularityIndex() {
		return popularityIndex;
	}

	public void setPopularityIndex(int popularityIndex) {
		this.popularityIndex = popularityIndex;
	}
}
