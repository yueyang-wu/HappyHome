package happyhome.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import happyhome.model.*;

public class TyphoonDao extends DisasterDao {

	protected ConnectionManager connectionManager;
	private static TyphoonDao instance = null;
	
	protected TyphoonDao() {
		connectionManager = new ConnectionManager();
	}
	
	public static TyphoonDao getInstance() {
		if (instance == null) {
			instance = new TyphoonDao();
		}
		return instance;
	}
	
	public Typhoon create(Typhoon typhoon) throws SQLException {
		Disaster disaster = create(new Disaster(typhoon.getCounty(), typhoon.getYear(),
				typhoon.getDisasterType(), typhoon.getDescription()));
		typhoon.setDisasterId(disaster.getDisasterId());
		
		String insertTyphoon = "INSERT INTO Typhoon"
				+ "(DisasterId,Category)"
				+ " VALUES(?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertTyphoon);
			insertStmt.setInt(1, typhoon.getDisasterId());
			insertStmt.setString(2,  typhoon.getCategory().name());
			insertStmt.executeUpdate();			
			return typhoon;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (connection != null) connection.close();
			if (insertStmt != null) insertStmt.close();
		}
	}
	
	public Typhoon getTyphoonByDisasterId(int disasterId) throws SQLException {
		String selectTyphoon =
				"SELECT Typhoon.DisasterId as DisasterId, FipsCountyCode, Year,"
				+ " DisasterType, Description, Category"
				+ " FROM Typhoon INNER JOIN Disaster"
				+ "  ON Typhoon.DisasterId = Disaster.DisasterId"
				+ " WHERE Typhoon.DisasterId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectTyphoon);
			selectStmt.setInt(1, disasterId);
			results = selectStmt.executeQuery();
			CountyDao countyDao = CountyDao.getInstance();
			
			if(results.next()) {
				int resultDisasterId = results.getInt("DisasterId");
				String fipsCountyCode = results.getString("FipsCountyCode");
				int year = results.getInt("Year");
				String disasterType = results.getString("DisasterType");
				String description = results.getString("Description");
				Typhoon.Category category = 
						Typhoon.Category.valueOf(results.getString("Category"));
				
				County county = countyDao.getCountyByFipsCountyCode(fipsCountyCode);
				Typhoon typhoon = new Typhoon(resultDisasterId,
						county, year, disasterType, description, category);
				return typhoon;
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
	
	public Typhoon delete(Typhoon typhoon) throws SQLException {
		String deleteTyphoon = "DELETE FROM Typhoon WHERE DisasterId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteTyphoon);
			deleteStmt.setInt(1, typhoon.getDisasterId());
			deleteStmt.executeUpdate();
			super.delete(typhoon);

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
