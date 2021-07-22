package happyhome.servlet;

import happyhome.dal.*;
import happyhome.model.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/userfavorite")
public class UserFavorite extends HttpServlet {
	
	protected FavoriteDao favoriteDao;
	
	@Override
	public void init() throws ServletException {
		favoriteDao = FavoriteDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
		
        List<Favorite> favorites = new ArrayList<Favorite>();
        
		// Retrieve favorite depending on valid PostId or UserName.
        String userName = req.getParameter("UserName");
        String fipsCountyCode = req.getParameter("FipsCountyCode");
        
        try {
	        if (userName != null && !userName.trim().isEmpty()) {
	        	// If the postid param is provided then ignore the username param.
	        	User user = new User(userName);
	        	favorite = favoriteDao.getFavoriteForUser(user);
	        	messages.put("title", "Favorite for UserName " + userName);
	        } else if (fipsCountyCode != null && !fipsCountyCode.trim().isEmpty()) {
	        	// If postid is invalid, then use the username param.
	        	County county = new County(fipsCountyCode);
	        	favorite = favoriteDao.getFavoritesForCounty(county);
	        	messages.put("title", "Favorite for fipsCountyCode " + fipsCountyCode);
	        } else {
	        	messages.put("title", "Invalid UserName and FipsCountyCode.");
	        }
        } catch (SQLException e) {
			e.printStackTrace();
			throw new IOException(e);
        }
        
        req.setAttribute("favorite", favorite);
        req.getRequestDispatcher("/Favorites.jsp").forward(req, resp);
	}
}
