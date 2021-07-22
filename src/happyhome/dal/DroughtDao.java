package happyhome.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import happyhome.model.*;

public class DroughtDao extends DisasterDao {

	protected ConnectionManager connectionManager;
	private static DroughtDao instance = null;
	
	protected DroughtDao() {
		connectionManager = new ConnectionManager();
	}
	
	public static DroughtDao getInstance() {
		if (instance == null) {
			instance = new DroughtDao();
		}
		return instance;
	}
	
	public Drought create(Drought drought) throws SQLException {
		Disaster disaster = create(new Disaster(drought.getCounty(), drought.getYear(),
				drought.getDisasterType(), drought.getDescription()));
		drought.setDisasterId(disaster.getDisasterId());
		
		String insertDrought = "INSERT INTO Drought"
				+ "(DisasterId,Classification)"
				+ " VALUES(?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertDrought);
			insertStmt.setInt(1, drought.getDisasterId());
			insertStmt.setString(2,  drought.getClassification().name());
			insertStmt.executeUpdate();			
			return drought;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (connection != null) connection.close();
			if (insertStmt != null) insertStmt.close();
		}
	}
	
	public Drought getDroughtByDisasterId(int disasterId) throws SQLException {
		String selectDrought =
				"SELECT Drought.DisasterId as DisasterId, FipsCountyCode, Year,"
				+ " DisasterType, Description, Classification"
				+ " FROM Drought INNER JOIN Disaster"
				+ "  ON Drought.DisasterId = Disaster.DisasterId"
				+ " WHERE Drought.DisasterId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectDrought);
			selectStmt.setInt(1, disasterId);
			results = selectStmt.executeQuery();
			CountyDao countyDao = CountyDao.getInstance();
			
			if(results.next()) {
				int resultDisasterId = results.getInt("DisasterId");
				String fipsCountyCode = results.getString("FipsCountyCode");
				int year = results.getInt("Year");
				String disasterType = results.getString("DisasterType");
				String description = results.getString("Description");
				Drought.Classification classification = 
						Drought.Classification.valueOf(results.getString("Classification"));
				
				County county = countyDao.getCountyByFipsCountyCode(fipsCountyCode);
				Drought drought = new Drought(resultDisasterId,
						county, year, disasterType, description, classification);
				return drought;
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
	
	public Drought delete(Drought drought) throws SQLException {
		String deleteDrought = "DELETE FROM Drought WHERE DisasterId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteDrought);
			deleteStmt.setInt(1, drought.getDisasterId());
			deleteStmt.executeUpdate();
			super.delete(drought);

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
