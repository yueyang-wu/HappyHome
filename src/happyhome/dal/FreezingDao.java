package happyhome.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import happyhome.model.*;

public class FreezingDao extends DisasterDao {

	protected ConnectionManager connectionManager;
	private static FreezingDao instance = null;
	
	protected FreezingDao() {
		connectionManager = new ConnectionManager();
	}
	
	public static FreezingDao getInstance() {
		if (instance == null) {
			instance = new FreezingDao();
		}
		return instance;
	}
	
	public Freezing create(Freezing freezing) throws SQLException {
		Disaster disaster = create(new Disaster(freezing.getCounty(), freezing.getYear(),
				freezing.getDisasterType(), freezing.getDescription()));
		freezing.setDisasterId(disaster.getDisasterId());
		
		String insertFreezing = "INSERT INTO Freezing"
				+ "(DisasterId,Category)"
				+ " VALUES(?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertFreezing);
			insertStmt.setInt(1, freezing.getDisasterId());
			insertStmt.setString(2,  freezing.getCategory().name());
			insertStmt.executeUpdate();			
			return freezing;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (connection != null) connection.close();
			if (insertStmt != null) insertStmt.close();
		}
	}
	
	public Freezing getFreezingByDisasterId(int disasterId) throws SQLException {
		String selectFreezing =
				"SELECT Freezing.DisasterId as DisasterId, FipsCountyCode, Year,"
				+ " DisasterType, Description, Category"
				+ " FROM Freezing INNER JOIN Disaster"
				+ "  ON Freezing.DisasterId = Disaster.DisasterId"
				+ " WHERE Freezing.DisasterId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectFreezing);
			selectStmt.setInt(1, disasterId);
			results = selectStmt.executeQuery();
			CountyDao countyDao = CountyDao.getInstance();
			
			if(results.next()) {
				int resultDisasterId = results.getInt("DisasterId");
				String fipsCountyCode = results.getString("FipsCountyCode");
				int year = results.getInt("Year");
				String disasterType = results.getString("DisasterType");
				String description = results.getString("Description");
				Freezing.Category category = 
						Freezing.Category.valueOf(results.getString("Category"));
				
				County county = countyDao.getCountyByFipsCountyCode(fipsCountyCode);
				Freezing freezing = new Freezing(resultDisasterId,
						county, year, disasterType, description, category);
				return freezing;
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
	
	public Freezing delete(Freezing freezing) throws SQLException {
		String deleteFreezing = "DELETE FROM Freezing WHERE DisasterId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteFreezing);
			deleteStmt.setInt(1, freezing.getDisasterId());
			deleteStmt.executeUpdate();
			super.delete(freezing);

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
