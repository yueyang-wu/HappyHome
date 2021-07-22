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
        
		// Retrieve favorite depending on valid UserName.
        String userName = req.getParameter("username");
        try {
	        if (userName != null && !userName.trim().isEmpty()) {
	        	User user = new User(userName);
	        	favorites = favoriteDao.getFavoritesForUser(user);
	        	
	        	messages.put("title", "Favorite for UserName " + userName);
	        } else {
	        	messages.put("title", "Invalid UserName.");
	        }
        } catch (SQLException e) {
			e.printStackTrace();
			throw new IOException(e);
        }
        req.setAttribute("favorites", favorites);
        req.getRequestDispatcher("/UserFavorite.jsp").forward(req, resp);
	}
}
