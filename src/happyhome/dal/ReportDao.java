package happyhome.dal;

import happyhome.model.*;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;

public class ReportDao {
	protected ConnectionManager connectionManager;

	private static ReportDao instance = null;
	
	protected ReportDao() {
		connectionManager = new ConnectionManager();
	}
	public static ReportDao getInstance() {
		if(instance == null) {
			instance = new ReportDao();
		}
		return instance;
	}

	public Report create(Report report) throws SQLException {
		String insertReport = "INSERT INTO Report(FipsCountyCode, `Index`, Recommendation, Created) VALUES(?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertReport,
				Statement.RETURN_GENERATED_KEYS);
			insertStmt.setString(1, report.getCounty().getFipsCountyCode());
			insertStmt.setDouble(2, report.getIndex());
			insertStmt.setString(3, report.getRecommendation());
			insertStmt.setTimestamp(4, new Timestamp(report.getCreated().getTime()));
			insertStmt.executeUpdate();
			
			resultKey = insertStmt.getGeneratedKeys();
			int reportId = -1;
			if(resultKey.next()) {
				reportId = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			report.setReportId(reportId);
			return report;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(insertStmt != null) {
				insertStmt.close();
			}
			if(resultKey != null) {
				resultKey.close();
			}
		}
	}

	public Report getReportByReportId(int reportId) throws SQLException {
		String selectReport = "SELECT ReportId, FipsCountyCode, `Index`, Recommendation, Created FROM Report WHERE ReportId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectReport);
			selectStmt.setInt(1, reportId);
			results = selectStmt.executeQuery();
			CountyDao countyDao = CountyDao.getInstance();
			if(results.next()) {
				int resultReportId = results.getInt("ReportId");
				String fipsCountyCode = results.getString("FipsCountyCode");
				double index = results.getDouble("Index");
				String recommendation = results.getString("Recommendation");
				Date created = new Date(results.getTimestamp("Created").getTime());
				County county = countyDao.getCountyByFipsCountyCode(fipsCountyCode);
				Report report = new Report(resultReportId, county, index, recommendation, created);
				return report;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(selectStmt != null) {
				selectStmt.close();
			}
			if(results != null) {
				results.close();
			}
		}
		return null;
	}
	
	public Report getReportForCounty(County county) throws SQLException {
		String selectReport = "SELECT ReportId, FipsCountyCode, `Index`, Recommendation, Created FROM Report WHERE FipsCountyCode=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectReport);
			selectStmt.setString(1, county.getFipsCountyCode());
			results = selectStmt.executeQuery();
			if(results.next()) {
				int reportId = results.getInt("ReportId");
				double index = results.getDouble("Index");
				String recommendation = results.getString("Recommendation");
				Date created = new Date(results.getTimestamp("Created").getTime());
				Report report = new Report(reportId, county, index, recommendation, created);
				return report;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(selectStmt != null) {
				selectStmt.close();
			}
			if(results != null) {
				results.close();
			}
		}
		return null;
	}

	public Report updateIndex(Report report, double newIndex) throws SQLException {
		String updateReport = "UPDATE Report SET Index=? WHERE ReportId=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateReport);
			updateStmt.setDouble(1, newIndex);
			updateStmt.setInt(2, report.getReportId());
			updateStmt.executeUpdate();
			
			report.setIndex(newIndex);
			return report;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(updateStmt != null) {
				updateStmt.close();
			}
		}
	}
	
	public Report updateRecommendation(Report report, String newRecommendation) throws SQLException {
		String updateReport = "UPDATE Report SET Recommendation=? WHERE ReportId=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateReport);
			updateStmt.setString(1, newRecommendation);
			updateStmt.setInt(2, report.getReportId());
			updateStmt.executeUpdate();
			
			report.setRecommendation(newRecommendation);
			return report;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(updateStmt != null) {
				updateStmt.close();
			}
		}
	}
	
	public Report delete(Report report) throws SQLException {
		String deleteReport = "DELETE FROM Report WHERE ReportId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteReport);
			deleteStmt.setInt(1, report.getReportId());
			deleteStmt.executeUpdate();

			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(deleteStmt != null) {
				deleteStmt.close();
			}
		}
	}
}
