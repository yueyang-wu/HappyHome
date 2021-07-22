package happyhome.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import happyhome.model.*;

public class StormDao extends DisasterDao {

	protected ConnectionManager connectionManager;
	private static StormDao instance = null;
	
	protected StormDao() {
		connectionManager = new ConnectionManager();
	}
	
	public static StormDao getInstance() {
		if (instance == null) {
			instance = new StormDao();
		}
		return instance;
	}
	
	public Storm create(Storm storm) throws SQLException {
		Disaster disaster = create(new Disaster(storm.getCounty(), storm.getYear(),
				storm.getDisasterType(), storm.getDescription()));
		storm.setDisasterId(disaster.getDisasterId());
		
		String insertStorm = "INSERT INTO Storm"
				+ "(DisasterId,Type)"
				+ " VALUES(?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertStorm);
			insertStmt.setInt(1, storm.getDisasterId());
			insertStmt.setString(2,  storm.getType().name());
			insertStmt.executeUpdate();			
			return storm;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (connection != null) connection.close();
			if (insertStmt != null) insertStmt.close();
		}
	}
	
	public Storm getStormByDisasterId(int disasterId) throws SQLException {
		String selectStorm =
				"SELECT Storm.DisasterId as DisasterId, FipsCountyCode, Year,"
				+ " DisasterType, Description, Type"
				+ " FROM Storm INNER JOIN Disaster"
				+ "  ON Storm.DisasterId = Disaster.DisasterId"
				+ " WHERE Storm.DisasterId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectStorm);
			selectStmt.setInt(1, disasterId);
			results = selectStmt.executeQuery();
			CountyDao countyDao = CountyDao.getInstance();
			
			if(results.next()) {
				int resultDisasterId = results.getInt("DisasterId");
				String fipsCountyCode = results.getString("FipsCountyCode");
				int year = results.getInt("Year");
				String disasterType = results.getString("DisasterType");
				String description = results.getString("Description");
				Storm.Type type = 
						Storm.Type.valueOf(results.getString("Type"));
				
				County county = countyDao.getCountyByFipsCountyCode(fipsCountyCode);
				Storm storm = new Storm(resultDisasterId,
						county, year, disasterType, description, type);
				return storm;
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
	
	public Storm delete(Storm storm) throws SQLException {
		String deleteStorm = "DELETE FROM Storm WHERE DisasterId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteStorm);
			deleteStmt.setInt(1, storm.getDisasterId());
			deleteStmt.executeUpdate();
			super.delete(storm);

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
