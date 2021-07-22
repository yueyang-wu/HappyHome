package happyhome.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import happyhome.model.*;

public class TsunamiDao extends DisasterDao {

	protected ConnectionManager connectionManager;
	private static TsunamiDao instance = null;
	
	protected TsunamiDao() {
		connectionManager = new ConnectionManager();
	}
	
	public static TsunamiDao getInstance() {
		if (instance == null) {
			instance = new TsunamiDao();
		}
		return instance;
	}
	
	public Tsunami create(Tsunami tsunami) throws SQLException {
		Disaster disaster = create(new Disaster(tsunami.getCounty(), tsunami.getYear(),
				tsunami.getDisasterType(), tsunami.getDescription()));
		tsunami.setDisasterId(disaster.getDisasterId());
		
		String insertTsunami = "INSERT INTO Tsunami"
				+ "(DisasterId,AlertLevel)"
				+ " VALUES(?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertTsunami);
			insertStmt.setInt(1, tsunami.getDisasterId());
			insertStmt.setString(2,  tsunami.getAlertLevel().name());
			insertStmt.executeUpdate();			
			return tsunami;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (connection != null) connection.close();
			if (insertStmt != null) insertStmt.close();
		}
	}
	
	public Tsunami getTsunamiByDisasterId(int disasterId) throws SQLException {
		String selectTsunami =
				"SELECT Tsunami.DisasterId as DisasterId, FipsCountyCode, Year,"
				+ " DisasterType, Description, AlertLevel"
				+ " FROM Tsunami INNER JOIN Disaster"
				+ "  ON Tsunami.DisasterId = Disaster.DisasterId"
				+ " WHERE Tsunami.DisasterId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectTsunami);
			selectStmt.setInt(1, disasterId);
			results = selectStmt.executeQuery();
			CountyDao countyDao = CountyDao.getInstance();
			
			if(results.next()) {
				int resultDisasterId = results.getInt("DisasterId");
				String fipsCountyCode = results.getString("FipsCountyCode");
				int year = results.getInt("Year");
				String disasterType = results.getString("DisasterType");
				String description = results.getString("Description");
				Tsunami.AlertLevel alertLevel = 
						Tsunami.AlertLevel.valueOf(results.getString("AlertLevel"));
				
				County county = countyDao.getCountyByFipsCountyCode(fipsCountyCode);
				Tsunami tsunami = new Tsunami(resultDisasterId,
						county, year, disasterType, description, alertLevel);
				return tsunami;
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
	
	public Tsunami delete(Tsunami tsunami) throws SQLException {
		String deleteTsunami = "DELETE FROM Tsunami WHERE DisasterId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteTsunami);
			deleteStmt.setInt(1, tsunami.getDisasterId());
			deleteStmt.executeUpdate();
			super.delete(tsunami);

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
