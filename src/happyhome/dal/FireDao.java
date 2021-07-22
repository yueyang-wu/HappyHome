package happyhome.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import happyhome.model.*;

public class FireDao extends DisasterDao {

	protected ConnectionManager connectionManager;
	private static FireDao instance = null;
	
	protected FireDao() {
		connectionManager = new ConnectionManager();
	}
	
	public static FireDao getInstance() {
		if (instance == null) {
			instance = new FireDao();
		}
		return instance;
	}
	
	public Fire create(Fire fire) throws SQLException {
		Disaster disaster = create(new Disaster(fire.getCounty(), fire.getYear(),
				fire.getDisasterType(), fire.getDescription()));
		fire.setDisasterId(disaster.getDisasterId());
		
		String insertFire = "INSERT INTO Fire"
				+ "(DisasterId,DangerLevel)"
				+ " VALUES(?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertFire);
			insertStmt.setInt(1, fire.getDisasterId());
			insertStmt.setString(2,  fire.getDangerLevel().name());
			insertStmt.executeUpdate();			
			return fire;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (connection != null) connection.close();
			if (insertStmt != null) insertStmt.close();
		}
	}
	
	public Fire getFireByDisasterId(int disasterId) throws SQLException {
		String selectFire =
				"SELECT Fire.DisasterId as DisasterId, FipsCountyCode, Year,"
				+ " DisasterType, Description, DangerLevel"
				+ " FROM Fire INNER JOIN Disaster"
				+ "  ON Fire.DisasterId = Disaster.DisasterId"
				+ " WHERE Fire.DisasterId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectFire);
			selectStmt.setInt(1, disasterId);
			results = selectStmt.executeQuery();
			CountyDao countyDao = CountyDao.getInstance();
			
			if(results.next()) {
				int resultDisasterId = results.getInt("DisasterId");
				String fipsCountyCode = results.getString("FipsCountyCode");
				int year = results.getInt("Year");
				String disasterType = results.getString("DisasterType");
				String description = results.getString("Description");
				Fire.DangerLevel dangerLevel = 
						Fire.DangerLevel.valueOf(results.getString("DangerLevel"));
				
				County county = countyDao.getCountyByFipsCountyCode(fipsCountyCode);
				Fire fire = new Fire(resultDisasterId,
						county, year, disasterType, description, dangerLevel);
				return fire;
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
	
	public Fire delete(Fire fire) throws SQLException {
		String deleteFire = "DELETE FROM Fire WHERE DisasterId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteFire);
			deleteStmt.setInt(1, fire.getDisasterId());
			deleteStmt.executeUpdate();
			super.delete(fire);

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
