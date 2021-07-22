package happyhome.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import happyhome.model.*;

public class VolcanoDao extends DisasterDao {

	protected ConnectionManager connectionManager;
	private static VolcanoDao instance = null;
	
	protected VolcanoDao() {
		connectionManager = new ConnectionManager();
	}
	
	public static VolcanoDao getInstance() {
		if (instance == null) {
			instance = new VolcanoDao();
		}
		return instance;
	}
	
	public Volcano create(Volcano volcano) throws SQLException {
		Disaster disaster = create(new Disaster(volcano.getCounty(), volcano.getYear(),
				volcano.getDisasterType(), volcano.getDescription()));
		volcano.setDisasterId(disaster.getDisasterId());
		
		String insertVolcano = "INSERT INTO Volcano"
				+ "(DisasterId,AlertLevel)"
				+ " VALUES(?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertVolcano);
			insertStmt.setInt(1, volcano.getDisasterId());
			insertStmt.setString(2,  volcano.getAlertLevel().name());
			insertStmt.executeUpdate();			
			return volcano;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (connection != null) connection.close();
			if (insertStmt != null) insertStmt.close();
		}
	}
	
	public Volcano getVolcanoByDisasterId(int disasterId) throws SQLException {
		String selectVolcano =
				"SELECT Volcano.DisasterId as DisasterId, FipsCountyCode, Year,"
				+ " DisasterType, Description, AlertLevel"
				+ " FROM Volcano INNER JOIN Disaster"
				+ "  ON Volcano.DisasterId = Disaster.DisasterId"
				+ " WHERE Volcano.DisasterId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectVolcano);
			selectStmt.setInt(1, disasterId);
			results = selectStmt.executeQuery();
			CountyDao countyDao = CountyDao.getInstance();
			
			if(results.next()) {
				int resultDisasterId = results.getInt("DisasterId");
				String fipsCountyCode = results.getString("FipsCountyCode");
				int year = results.getInt("Year");
				String disasterType = results.getString("DisasterType");
				String description = results.getString("Description");
				Volcano.AlertLevel alertLevel = 
						Volcano.AlertLevel.valueOf(results.getString("AlertLevel"));
				
				County county = countyDao.getCountyByFipsCountyCode(fipsCountyCode);
				Volcano volcano = new Volcano(resultDisasterId,
						county, year, disasterType, description, alertLevel);
				return volcano;
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
	
	public Volcano delete(Volcano volcano) throws SQLException {
		String deleteVolcano = "DELETE FROM Volcano WHERE DisasterId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteVolcano);
			deleteStmt.setInt(1, volcano.getDisasterId());
			deleteStmt.executeUpdate();
			super.delete(volcano);

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
