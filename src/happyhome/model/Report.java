package happyhome.model;

import java.util.Date;

public class Report {
	protected int reportId;
	protected County county;
	protected double index;
	protected String recommendation;
	protected Date created;
	
	public Report(int reportId, County county, double index, String recommendation, Date created) {
		this.reportId = reportId;
		this.county = county;
		this.index = index;
		this.recommendation = recommendation;
		this.created = created;
	}

	public Report(int reportId) {
		this.reportId = reportId;
	}

	public Report(County county, double index, String recommendation, Date created) {
		this.county = county;
		this.index = index;
		this.recommendation = recommendation;
		this.created = created;
	}

	public int getReportId() {
		return reportId;
	}

	public void setReportId(int reportId) {
		this.reportId = reportId;
	}

	public County getCounty() {
		return county;
	}

	public void setCounty(County county) {
		this.county = county;
	}

	public double getIndex() {
		return index;
	}

	public void setIndex(double index) {
		this.index = index;
	}

	public String getRecommendation() {
		return recommendation;
	}

	public void setRecommendation(String recommendation) {
		this.recommendation = recommendation;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}
}
