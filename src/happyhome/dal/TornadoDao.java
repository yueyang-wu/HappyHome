package happyhome.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import happyhome.model.*;

public class TornadoDao extends DisasterDao {

	protected ConnectionManager connectionManager;
	private static TornadoDao instance = null;
	
	protected TornadoDao() {
		connectionManager = new ConnectionManager();
	}
	
	public static TornadoDao getInstance() {
		if (instance == null) {
			instance = new TornadoDao();
		}
		return instance;
	}
	
	public Tornado create(Tornado tornado)
			throws SQLException {
		Disaster disaster = create(new Disaster(tornado.getCounty(), tornado.getYear(),
				tornado.getDisasterType(), tornado.getDescription()));
		tornado.setDisasterId(disaster.getDisasterId());
		
		String insertTornado = "INSERT INTO Tornado"
				+ "(DisasterId,Classification)"
				+ " VALUES(?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertTornado);
			insertStmt.setInt(1, tornado.getDisasterId());
			insertStmt.setString(2,  tornado.getClassification().name());
			insertStmt.executeUpdate();			
			return tornado;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (connection != null) connection.close();
			if (insertStmt != null) insertStmt.close();
		}
	}
	
	public Tornado getTornadoByDisasterId(int disasterId) throws SQLException {
		String selectTornado =
				"SELECT Tornado.DisasterId as DisasterId, FipsCountyCode, Year,"
				+ " DisasterType, Description, Classification"
				+ " FROM Tornado INNER JOIN Disaster"
				+ "  ON Tornado.DisasterId = Disaster.DisasterId"
				+ " WHERE Tornado.DisasterId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectTornado);
			selectStmt.setInt(1, disasterId);
			results = selectStmt.executeQuery();
			CountyDao countyDao = CountyDao.getInstance();
			
			if(results.next()) {
				int resultDisasterId = results.getInt("DisasterId");
				String fipsCountyCode = results.getString("FipsCountyCode");
				int year = results.getInt("Year");
				String disasterType = results.getString("DisasterType");
				String description = results.getString("Description");
				Tornado.Classification classification = 
						Tornado.Classification.valueOf(results.getString("Classification"));
				
				County county = countyDao.getCountyByFipsCountyCode(fipsCountyCode);
				Tornado tornado = new Tornado(resultDisasterId,
						county, year, disasterType, description, classification);
				return tornado;
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
	
	public Tornado delete(Tornado tornado) throws SQLException {
		String deleteTornado = "DELETE FROM Tornado WHERE DisasterId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteTornado);
			deleteStmt.setInt(1, tornado.getDisasterId());
			deleteStmt.executeUpdate();
			super.delete(tornado);

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
