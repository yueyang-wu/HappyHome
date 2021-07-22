package happyhome.dal;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import happyhome.model.*;

/**
 * No update operation for Disaster and its child classes(i.g. Drought, Fire, Freezing...)
 * because we will never update any information of a disaster happened in the past. 
 */

public class DisasterDao {
	protected ConnectionManager connectionManager;
	private static DisasterDao instance = null;
	
	protected DisasterDao() {
		connectionManager = new ConnectionManager();
	}
	
	public static DisasterDao getInstance() {
		if (instance == null) {
			instance = new DisasterDao();
		}
		return instance;
	}
	
	public Disaster create(Disaster disaster) throws SQLException {
		String insertDisaster = "INSERT INTO Disaster(FipsCountyCode,Year,"
				+ "DisasterType,Description) VALUES(?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertDisaster,
					Statement.RETURN_GENERATED_KEYS);
			insertStmt.setString(1, disaster.getCounty().getFipsCountyCode());
			insertStmt.setInt(2, disaster.getYear());
			insertStmt.setString(3, disaster.getDisasterType());
			insertStmt.setString(4, disaster.getDescription());
			insertStmt.executeUpdate();
			
			resultKey = insertStmt.getGeneratedKeys();
			int disasterId = -1;
			if(resultKey.next()) {
				disasterId = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			disaster.setDisasterId(disasterId);
			
			return disaster;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (connection != null) connection.close();
			if (insertStmt != null) insertStmt.close();
		}
	}
	
	public Disaster getDisasterByDisasterId(int disasterId) throws SQLException {
		String selectDisaster = "SELECT DisasterId,FipsCountyCode,Year,"
				+ "DisasterType,Description FROM Disaster WHERE DisasterId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectDisaster);
			selectStmt.setInt(1, disasterId);
			results = selectStmt.executeQuery();
			CountyDao countyDao = CountyDao.getInstance();
			
			if(results.next()) {
				int resultDisasterId = results.getInt("DisasterId");
				String fipsCountyCode = results.getString("FipsCountyCode");
				int year = results.getInt("Year");
				String disasterType = results.getString("DisasterType");
				String description = results.getString("Description");
				
				County county = countyDao.getCountyByFipsCountyCode(fipsCountyCode);
				Disaster disaster = new Disaster(resultDisasterId, county,
						year, disasterType, description);
				return disaster;
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
	
	public List<Disaster> getDisastersForCounty(County county)
			throws SQLException {
		List<Disaster> disasters = new ArrayList<Disaster>();
		String selectDisasters = "SELECT DisasterId,FipsCountyCode,Year,"
				+ "DisasterType,Description FROM Disaster WHERE FipsCountyCode=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectDisasters);
			selectStmt.setString(1, county.getFipsCountyCode());
			results = selectStmt.executeQuery();
			
			while(results.next()) {
				int disasterId = results.getInt("DisasterId");
				int year = results.getInt("Year");
				String disasterType = results.getString("DisasterType");
				String description = results.getString("Description");
				
				Disaster disaster = new Disaster(disasterId, county,
						year, disasterType, description);
				disasters.add(disaster);
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
		return disasters;
	}
	
	public Disaster delete(Disaster disaster) throws SQLException {
		String deleteDisaster = "DELETE FROM Disaster WHERE DisasterId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteDisaster);
			deleteStmt.setInt(1, disaster.getDisasterId());
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
