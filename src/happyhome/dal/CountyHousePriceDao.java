package happyhome.dal;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import happyhome.model.*;

public class CountyHousePriceDao extends CountyDao {
	protected ConnectionManager connectionManager;
	private static CountyHousePriceDao instance = null;
	
	protected CountyHousePriceDao() {
		connectionManager = new ConnectionManager();
	}
	
	public static CountyHousePriceDao getInstance() {
		if (instance == null) {
			instance = new CountyHousePriceDao();
		}
		return instance;
	}
	
	
	public CountyHousePrice create(CountyHousePrice countyHousePrice) 
			throws SQLException {
		create(new County(countyHousePrice.getFipsCountyCode(),
				countyHousePrice.getState(), countyHousePrice.getCountyName(),
				countyHousePrice.getPopularityIndex()));
		
		String insertCountyHousePrice = "INSERT INTO CountyHousePrice" 
				+ "(FipsCountyCode,CurrentPrice) VALUES(?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertCountyHousePrice);
			insertStmt.setString(1, countyHousePrice.getFipsCountyCode());
			insertStmt.setInt(2, countyHousePrice.getCurrentPrice());
			insertStmt.executeUpdate();
			
			return countyHousePrice;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (connection != null) connection.close();
			if (insertStmt != null) insertStmt.close();
		}
	}
	
	public CountyHousePrice getCountyHousePriceByFipsCountyCode(String
			fipsCountyCode) throws SQLException {
		String selectCountyHousePrice =
				"SELECT CountyHousePrice.FipsCountyCode AS FipsCountyCode,"
				+ " State, CountyName, PopularityIndex, CurrentPrice"
				+ " FROM CountyHousePrice INNER JOIN County "
				+ "  ON CountyHousePrice.FipsCountyCode = County.FipsCountyCode"
				+ " WHERE CountyHousePrice.FipsCountyCode=?;";
			Connection connection = null;
			PreparedStatement selectStmt = null;
			ResultSet results = null;
			try {
				connection = connectionManager.getConnection();
				selectStmt = connection.prepareStatement(selectCountyHousePrice);
				selectStmt.setString(1, fipsCountyCode);
				results = selectStmt.executeQuery();
				if(results.next()) {
					String resultFipsCountyCode = results.getString("FipsCountyCode");
					String state = results.getString("State");
					String countyName = results.getString("CountyName");
					int popularityIndex = results.getInt("PopularityIndex");
					int currentPrice = results.getInt("CurrentPrice");
					CountyHousePrice countyHousePrice = new CountyHousePrice(
							resultFipsCountyCode, state, countyName,
							popularityIndex, currentPrice);
					return countyHousePrice;
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
	
	// No delete operation is required because counties would never be deleted
	// from the database.
}