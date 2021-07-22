package happyhome.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import happyhome.model.*;

public class EarthquakeDao extends DisasterDao {

	protected ConnectionManager connectionManager;
	private static EarthquakeDao instance = null;
	
	protected EarthquakeDao() {
		connectionManager = new ConnectionManager();
	}
	
	public static EarthquakeDao getInstance() {
		if (instance == null) {
			instance = new EarthquakeDao();
		}
		return instance;
	}
	
	public Earthquake create(Earthquake earthquake) throws SQLException {
		Disaster disaster = create(new Disaster(earthquake.getCounty(), earthquake.getYear(),
				earthquake.getDisasterType(), earthquake.getDescription()));
		earthquake.setDisasterId(disaster.getDisasterId());
		
		String insertEarthquake = "INSERT INTO Earthquake"
				+ "(DisasterId,Class)"
				+ " VALUES(?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertEarthquake);
			insertStmt.setInt(1, earthquake.getDisasterId());
			insertStmt.setString(2,  earthquake.getClassType().name());
			insertStmt.executeUpdate();			
			return earthquake;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (connection != null) connection.close();
			if (insertStmt != null) insertStmt.close();
		}
	}
	
	public Earthquake getEarthquakeByDisasterId(int disasterId) throws SQLException {
		String selectEarthquake =
				"SELECT Earthquake.DisasterId as DisasterId, FipsCountyCode, Year,"
				+ " DisasterType, Description, Class"
				+ " FROM Earthquake INNER JOIN Disaster"
				+ "  ON Earthquake.DisasterId = Disaster.DisasterId"
				+ " WHERE Earthquake.DisasterId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectEarthquake);
			selectStmt.setInt(1, disasterId);
			results = selectStmt.executeQuery();
			CountyDao countyDao = CountyDao.getInstance();
			
			if(results.next()) {
				int resultDisasterId = results.getInt("DisasterId");
				String fipsCountyCode = results.getString("FipsCountyCode");
				int year = results.getInt("Year");
				String disasterType = results.getString("DisasterType");
				String description = results.getString("Description");
				Earthquake.ClassType classType = 
						Earthquake.ClassType.valueOf(results.getString("Class"));
				
				County county = countyDao.getCountyByFipsCountyCode(fipsCountyCode);
				Earthquake earthquake = new Earthquake(resultDisasterId,
						county, year, disasterType, description, classType);
				return earthquake;
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
	
	public Earthquake delete(Earthquake earthquake) throws SQLException {
		String deleteEarthquake = "DELETE FROM Earthquake WHERE DisasterId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteEarthquake);
			deleteStmt.setInt(1, earthquake.getDisasterId());
			deleteStmt.executeUpdate();
			super.delete(earthquake);

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
