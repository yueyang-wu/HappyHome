package happyhome.dal;

import java.sql.Connection;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import happyhome.model.*;

public class CountyHousePriceForecastDao extends CountyDao {
	protected ConnectionManager connectionManager;
	private static CountyHousePriceForecastDao instance = null;
	
	protected CountyHousePriceForecastDao() {
		connectionManager = new ConnectionManager();
	}
	
	public static CountyHousePriceForecastDao getInstance() {
		if (instance == null) {
			instance = new CountyHousePriceForecastDao();
		}
		return instance;
	}
	
	public CountyHousePriceForecast create(CountyHousePriceForecast
			countyHousePriceForecast) throws SQLException {
		create(new County(countyHousePriceForecast.getFipsCountyCode(),
				countyHousePriceForecast.getState(),
				countyHousePriceForecast.getCountyName(),
				countyHousePriceForecast.getPopularityIndex()));
		
		String insertCountyHousePriceForecast = "INSERT INTO CountyHousePriceForecast" 
				+ "(FipsCountyCode,HomePriceForecast) VALUES(?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertCountyHousePriceForecast);
			insertStmt.setString(1, countyHousePriceForecast.getFipsCountyCode());
			insertStmt.setDouble(2, countyHousePriceForecast.getHomePriceForecast());
			insertStmt.executeUpdate();
			
			return countyHousePriceForecast;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (connection != null) connection.close();
			if (insertStmt != null) insertStmt.close();
		}
	}
	
	public CountyHousePriceForecast getCountyHousePriceForecastByFipsCountyCode(
			String fipsCountyCode) throws SQLException {
		String selectCountyHousePriceForecast =
				"SELECT CountyHousePriceForecast.FipsCountyCode AS FipsCountyCode,"
				+ " State, CountyName, PopularityIndex, HomePriceForecast"
				+ " FROM CountyHousePriceForecast INNER JOIN County "
				+ "  ON CountyHousePriceForecast.FipsCountyCode = County.FipsCountyCode"
				+ " WHERE CountyHousePriceForecast.FipsCountyCode=?;";
			Connection connection = null;
			PreparedStatement selectStmt = null;
			ResultSet results = null;
			try {
				connection = connectionManager.getConnection();
				selectStmt = connection.prepareStatement(selectCountyHousePriceForecast);
				selectStmt.setString(1, fipsCountyCode);
				results = selectStmt.executeQuery();
				if(results.next()) {
					String resultFipsCountyCode = results.getString("FipsCountyCode");
					String state = results.getString("State");
					String countyName = results.getString("CountyName");
					int popularityIndex = results.getInt("PopularityIndex");
					double homePriceForecast = results.getDouble("HomePriceForecast");
					CountyHousePriceForecast countyHousePriceForecast = new
							CountyHousePriceForecast(resultFipsCountyCode, state,
									countyName, popularityIndex, homePriceForecast);
					return countyHousePriceForecast;
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
	
	public CountyHousePriceForecast updateHomePriceForecast(CountyHousePriceForecast
			countyHousePriceForecast, double newHomePriceForecast) throws SQLException {
		String updateCountyHousePriceForecast =
				"UPDATE CountyHousePriceForecast SET HomePriceForecast=? WHERE FipsCountyCode=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateCountyHousePriceForecast);
			updateStmt.setDouble(1, newHomePriceForecast);
			updateStmt.setString(2, countyHousePriceForecast.getFipsCountyCode());
			updateStmt.executeUpdate();
			
			countyHousePriceForecast.setHomePriceForecast(newHomePriceForecast);
			return countyHousePriceForecast;
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
	
	public CountyHousePriceForecast delete(CountyHousePriceForecast countyHousePriceForecast) throws SQLException {
		String deleteCountyHousePriceForecast = "DELETE FROM CountyHousePriceForecast WHERE FipsCountyCode=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteCountyHousePriceForecast);
			deleteStmt.setString(1, countyHousePriceForecast.getFipsCountyCode());
			deleteStmt.executeUpdate();
			super.delete(countyHousePriceForecast);

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