package happyhome.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import happyhome.model.*;

public class FloodDao extends DisasterDao {

	protected ConnectionManager connectionManager;
	private static FloodDao instance = null;
	
	protected FloodDao() {
		connectionManager = new ConnectionManager();
	}
	
	public static FloodDao getInstance() {
		if (instance == null) {
			instance = new FloodDao();
		}
		return instance;
	}
	
	public Flood create(Flood flood) throws SQLException {
		Disaster disaster = create(new Disaster(flood.getCounty(), flood.getYear(),
				flood.getDisasterType(), flood.getDescription()));
		flood.setDisasterId(disaster.getDisasterId());
		
		String insertFlood = "INSERT INTO Flood"
				+ "(DisasterId,Stage)"
				+ " VALUES(?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertFlood);
			insertStmt.setInt(1, flood.getDisasterId());
			insertStmt.setString(2,  flood.getStage().name());
			insertStmt.executeUpdate();			
			return flood;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (connection != null) connection.close();
			if (insertStmt != null) insertStmt.close();
		}
	}
	
	public Flood getFloodByDisasterId(int disasterId) throws SQLException {
		String selectFlood =
				"SELECT Flood.DisasterId as DisasterId, FipsCountyCode, Year,"
				+ " DisasterType, Description, Stage"
				+ " FROM Flood INNER JOIN Disaster"
				+ "  ON Flood.DisasterId = Disaster.DisasterId"
				+ " WHERE Flood.DisasterId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectFlood);
			selectStmt.setInt(1, disasterId);
			results = selectStmt.executeQuery();
			CountyDao countyDao = CountyDao.getInstance();
			
			if(results.next()) {
				int resultDisasterId = results.getInt("DisasterId");
				String fipsCountyCode = results.getString("FipsCountyCode");
				int year = results.getInt("Year");
				String disasterType = results.getString("DisasterType");
				String description = results.getString("Description");
				Flood.Stage stage = Flood.Stage.valueOf(results.getString("Stage"));
				
				County county = countyDao.getCountyByFipsCountyCode(fipsCountyCode);
				Flood flood = new Flood(resultDisasterId,
						county, year, disasterType, description, stage);
				return flood;
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
	
	public Flood delete(Flood flood) throws SQLException {
		String deleteFlood = "DELETE FROM Flood WHERE DisasterId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteFlood);
			deleteStmt.setInt(1, flood.getDisasterId());
			deleteStmt.executeUpdate();
			super.delete(flood);

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
