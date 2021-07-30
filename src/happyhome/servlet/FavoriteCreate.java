package happyhome.servlet;

import happyhome.dal.*;

import happyhome.model.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/favoritecreate")
public class FavoriteCreate extends HttpServlet {
	protected FavoriteDao favoriteDao;
	protected UserDao userDao;
	protected CountyDao countyDao;
	
	@Override
	public void init() throws ServletException {
		favoriteDao = FavoriteDao.getInstance();
		userDao = UserDao.getInstance();
		countyDao = CountyDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        
        String fipsCountyCode = req.getParameter("fipscountycode");

		req.setAttribute("fipscountycode", fipsCountyCode);
		req.getRequestDispatcher("/FavoriteCreate.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        User user = null;
        
        String userName = req.getParameter("username");
        String fipsCountyCode = req.getParameter("fipscountycode");
        if (userName == null || userName.trim().isEmpty()) {
            messages.put("success", "Please enter a valid username.");
            messages.put("disableSubmit", "true");
        } else {
	        try {
	        	User existingUser = userDao.getUserByUserName(userName);
	        	if (existingUser == null) {
	        		messages.put("success", "Please enter a valid username.");
	        	} else {
	        		user = new User(userName);
		        	County county = new County(fipsCountyCode);
		        	Favorite favorite = new Favorite(user, county);
		        	List<Favorite> existingFavorites = favoriteDao.getFavoritesForUser(existingUser);
		        	if (existingFavorites.stream().anyMatch(f -> 
		        	(f.getUser().getUserName().equals(userName)
		        			&& f.getCounty().getFipsCountyCode().equals(fipsCountyCode)))) {
		        		messages.put("success", "Already favorited.");
		        	} else {
		        		favorite = favoriteDao.create(favorite);
		        		messages.put("success", "Successfully favorited!");
		        	}
	        	}
	        } catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }
        
        req.getRequestDispatcher("/FavoriteCreate.jsp").forward(req, resp);
    }
}
