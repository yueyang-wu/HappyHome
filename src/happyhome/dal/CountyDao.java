package happyhome.dal;

import java.sql.Connection;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import happyhome.model.*;

public class CountyDao {
	protected ConnectionManager connectionManager;
	private static CountyDao instance = null;
	
	protected CountyDao() {
		connectionManager = new ConnectionManager();
	}
	
	public static CountyDao getInstance() {
		if (instance == null) {
			instance = new CountyDao();
		}
		return instance;
	}
	
	public County create(County county) throws SQLException {
		String insertCounty = "INSERT INTO County(FipsCountyCode,State,"
				+ "CountyName,PopularityIndex) VALUES(?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertCounty);
			
			insertStmt.setString(1, county.getFipsCountyCode());
			insertStmt.setString(2, county.getState());
			insertStmt.setString(3, county.getCountyName());
			insertStmt.setInt(4, county.getPopularityIndex());
			insertStmt.executeUpdate();

			return county;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (connection != null) connection.close();
			if (insertStmt != null) insertStmt.close();
		}
	}
	
	public County getCountyByFipsCountyCode(String fipsCountyCode) throws 	
		SQLException {
			String selectCounty = "SELECT FipsCountyCode, State, CountyName, PopularityIndex FROM County WHERE FipsCountyCode=?;";
			Connection connection = null;
			PreparedStatement selectStmt = null;
			ResultSet results = null;
		
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectCounty);
			selectStmt.setString(1, fipsCountyCode);
			results = selectStmt.executeQuery();
			
			if(results.next()) {
				String resultFipsCountyCode = results.getString("FipsCountyCode");
				String state = results.getString("State");
				String countyName = results.getString("CountyName");
				int popularityIndex = results.getInt("PopularityIndex");
				
				County county = new County(resultFipsCountyCode, state, countyName, popularityIndex);
				return county;
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
	
	public County getCountyByCountyNameAndState(String countyName, String state) throws SQLException {
		String selectCounty = "SELECT FipsCountyCode, State, CountyName, PopularityIndex "
				+ "FROM County WHERE CountyName=? AND State=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectCounty);
			selectStmt.setString(1, countyName);
			selectStmt.setString(2, state);
			
			results = selectStmt.executeQuery();
			
			if(results.next()) {
				String fipsCountyCode = results.getString("FipsCountyCode");
				String resultState = results.getString("State");
				String resultCountyName = results.getString("CountyName");
				int popularityIndex = results.getInt("PopularityIndex");
				
				County county = new County(fipsCountyCode, resultState, resultCountyName, popularityIndex);
				return county;
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
	
	public County updatePopularityIndex(County county, int newPopularityIndex) throws SQLException {
		String updateCounty = "UPDATE County SET PopularityIndex=? WHERE FipsCountyCode=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateCounty);
			updateStmt.setInt(1, newPopularityIndex);
			updateStmt.setString(2, county.getFipsCountyCode());
			updateStmt.executeUpdate();
			
			county.setPopularityIndex(newPopularityIndex);
			return county;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(updateStmt != null) {
				updateStmt.close();
			}
		}
	}
	
	public County delete(County county) throws SQLException {
		String deleteCounty = "DELETE FROM County WHERE FipsCountyCode=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteCounty);
			deleteStmt.setString(1, county.getFipsCountyCode());
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
