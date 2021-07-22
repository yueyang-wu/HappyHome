package happyhome.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import happyhome.model.*;

public class HurricaneDao extends DisasterDao {

	protected ConnectionManager connectionManager;
	private static HurricaneDao instance = null;
	
	protected HurricaneDao() {
		connectionManager = new ConnectionManager();
	}
	
	public static HurricaneDao getInstance() {
		if (instance == null) {
			instance = new HurricaneDao();
		}
		return instance;
	}
	
	public Hurricane create(Hurricane hurricane) throws SQLException {
		Disaster disaster = create(new Disaster(hurricane.getCounty(), hurricane.getYear(),
				hurricane.getDisasterType(), hurricane.getDescription()));
		hurricane.setDisasterId(disaster.getDisasterId());
		
		String insertHurricane = "INSERT INTO Hurricane"
				+ "(DisasterId,Category)"
				+ " VALUES(?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertHurricane);
			insertStmt.setInt(1, hurricane.getDisasterId());
			insertStmt.setString(2,  hurricane.getCategory().name());
			insertStmt.executeUpdate();			
			return hurricane;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (connection != null) connection.close();
			if (insertStmt != null) insertStmt.close();
		}
	}
	
	public Hurricane getHurricaneByDisasterId(int disasterId) throws SQLException {
		String selectHurricane =
				"SELECT Hurricane.DisasterId as DisasterId, FipsCountyCode, Year,"
				+ " DisasterType, Description, Category"
				+ " FROM Hurricane INNER JOIN Disaster"
				+ "  ON Hurricane.DisasterId = Disaster.DisasterId"
				+ " WHERE Hurricane.DisasterId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectHurricane);
			selectStmt.setInt(1, disasterId);
			results = selectStmt.executeQuery();
			CountyDao countyDao = CountyDao.getInstance();
			
			if(results.next()) {
				int resultDisasterId = results.getInt("DisasterId");
				String fipsCountyCode = results.getString("FipsCountyCode");
				int year = results.getInt("Year");
				String disasterType = results.getString("DisasterType");
				String description = results.getString("Description");
				Hurricane.Category category = 
						Hurricane.Category.valueOf(results.getString("Category"));
				
				County county = countyDao.getCountyByFipsCountyCode(fipsCountyCode);
				Hurricane hurricane = new Hurricane(resultDisasterId,
						county, year, disasterType, description, category);
				return hurricane;
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
	
	public Hurricane delete(Hurricane hurricane) throws SQLException {
		String deleteHurricane = "DELETE FROM Hurricane WHERE DisasterId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteHurricane);
			deleteStmt.setInt(1, hurricane.getDisasterId());
			deleteStmt.executeUpdate();
			super.delete(hurricane);

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
