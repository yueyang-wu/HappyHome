package happyhome.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import happyhome.model.*;

public class SnowDao extends DisasterDao {

	protected ConnectionManager connectionManager;
	private static SnowDao instance = null;
	
	protected SnowDao() {
		connectionManager = new ConnectionManager();
	}
	
	public static SnowDao getInstance() {
		if (instance == null) {
			instance = new SnowDao();
		}
		return instance;
	}
	
	public Snow create(Snow snow) throws SQLException {
		Disaster disaster = create(new Disaster(snow.getCounty(), snow.getYear(),
				snow.getDisasterType(), snow.getDescription()));
		snow.setDisasterId(disaster.getDisasterId());
		
		String insertSnow = "INSERT INTO Snow"
				+ "(DisasterId,EmergencyAdvisoryLevel)"
				+ " VALUES(?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertSnow);
			insertStmt.setInt(1, snow.getDisasterId());
			insertStmt.setString(2,  snow.getEmergencyAdvisoryLevel().name());
			insertStmt.executeUpdate();			
			return snow;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (connection != null) connection.close();
			if (insertStmt != null) insertStmt.close();
		}
	}
	
	public Snow getSnowByDisasterId(int disasterId) throws SQLException {
		String selectSnow =
				"SELECT Snow.DisasterId as DisasterId, FipsCountyCode, Year,"
				+ " DisasterType, Description, EmergencyAdvisoryLevel"
				+ " FROM Snow INNER JOIN Disaster"
				+ "  ON Snow.DisasterId = Disaster.DisasterId"
				+ " WHERE Snow.DisasterId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectSnow);
			selectStmt.setInt(1, disasterId);
			results = selectStmt.executeQuery();
			CountyDao countyDao = CountyDao.getInstance();
			
			if(results.next()) {
				int resultDisasterId = results.getInt("DisasterId");
				String fipsCountyCode = results.getString("FipsCountyCode");
				int year = results.getInt("Year");
				String disasterType = results.getString("DisasterType");
				String description = results.getString("Description");
				Snow.EmergencyAdvisoryLevel emergencyAdvisoryLevel = 
						Snow.EmergencyAdvisoryLevel.valueOf(results.getString("EmergencyAdvisoryLevel"));
				
				County county = countyDao.getCountyByFipsCountyCode(fipsCountyCode);
				Snow snow = new Snow(resultDisasterId,
						county, year, disasterType, description, emergencyAdvisoryLevel);
				return snow;
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
	
	public Snow delete(Snow snow) throws SQLException {
		String deleteSnow = "DELETE FROM Snow WHERE DisasterId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteSnow);
			deleteStmt.setInt(1, snow.getDisasterId());
			deleteStmt.executeUpdate();
			super.delete(snow);

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
