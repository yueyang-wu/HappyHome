package happyhome.dal;

import java.sql.Connection;

/**
 * No update operation for Favorite
 * because Favorite only has favoriteId, user, and county.
 * It doesn't make sense to update any of them in FavoriteDao.
 */

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import happyhome.model.*;

public class FavoriteDao {
	protected ConnectionManager connectionManager;
	private static FavoriteDao instance = null;
	
	protected FavoriteDao() {
		connectionManager = new ConnectionManager();
	}
	
	public static FavoriteDao getInstance() {
		if (instance == null) {
			instance = new FavoriteDao();
		}
		return instance;
	}
	
	public Favorite create(Favorite favorite) throws SQLException {
		String insertFavorite = "INSERT INTO Favorite(UserName,FipsCountyCode) "
				+ "VALUES(?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertFavorite,
					Statement.RETURN_GENERATED_KEYS);
			insertStmt.setString(1, favorite.getUser().getUserName());
			insertStmt.setString(2, favorite.getCounty().getFipsCountyCode());
			insertStmt.executeUpdate();
			
			resultKey = insertStmt.getGeneratedKeys();
			int favoriteId = -1;
			if(resultKey.next()) {
				favoriteId = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			favorite.setFavoriteId(favoriteId);
			
			return favorite;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (connection != null) connection.close();
			if (insertStmt != null) insertStmt.close();
		}
	}
	
	public Favorite getFavoriteByFavoriteId(int favoriteId)
			throws SQLException {
		String selectFavorite = "SELECT FavoriteId,UserName,FipsCountyCode "
				+ "FROM Favorite WHERE FavoriteId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectFavorite);
			selectStmt.setInt(1, favoriteId);
			results = selectStmt.executeQuery();
			UserDao userDao = UserDao.getInstance();
			CountyDao countyDao = CountyDao.getInstance();
			
			if(results.next()) {
				int resultFavoriteId = results.getInt("FavoriteId");
				String userName = results.getString("UserName");
				String fipsCountyCode = results.getString("FipsCountyCode");
				
				User user = userDao.getUserByUserName(userName);
				County county = countyDao.getCountyByFipsCountyCode(fipsCountyCode);
				Favorite favorite = new Favorite(resultFavoriteId, user, county);
				return favorite;
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
	
	public List<Favorite> getFavoritesForUser(User user)
			throws SQLException {
		List<Favorite> favorites = new ArrayList<Favorite>();
		String selectFavorites =
			"SELECT FavoriteId,UserName,FipsCountyCode FROM Favorite "
			+ "WHERE UserName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectFavorites);
			selectStmt.setString(1, user.getUserName());
			results = selectStmt.executeQuery();
			CountyDao countyDao = CountyDao.getInstance();
			
			while(results.next()) {
				int favoriteId = results.getInt("FavoriteId");
				String fipsCountyCode = results.getString("FipsCountyCode");
				
				County county = countyDao.getCountyByFipsCountyCode(fipsCountyCode);
				Favorite favorite = new Favorite(favoriteId, user, county);
				favorites.add(favorite);
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
		return favorites;
	}
	
	public List<Favorite> getFavoritesForCounty(County county)
			throws SQLException {
		List<Favorite> favorites = new ArrayList<Favorite>();
		String selectFavorites =
			"SELECT FavoriteId,UserName,FipsCountyCode FROM Favorite "
			+ "WHERE FipsCountyCode=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectFavorites);
			selectStmt.setString(1, county.getFipsCountyCode());
			results = selectStmt.executeQuery();
			UserDao userDao = UserDao.getInstance();
			
			while(results.next()) {
				int favoriteId = results.getInt("FavoriteId");
				String userName = results.getString("UserName");
				
				User user = userDao.getUserByUserName(userName);
				Favorite favorite = new Favorite(favoriteId, user, county);
				favorites.add(favorite);
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
		return favorites;
	}
	
	public Favorite delete(Favorite favorite) throws SQLException {
		String deleteFavorite = "DELETE FROM Favorite WHERE FavoriteId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteFavorite);
			deleteStmt.setInt(1, favorite.getFavoriteId());
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
