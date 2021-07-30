package happyhome.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import happyhome.dal.FavoriteDao;
import happyhome.dal.UserDao;
import happyhome.model.Favorite;
import happyhome.model.User;

@WebServlet("/matchinguser")
public class MatchingUser extends HttpServlet {
	protected UserDao userDao;
	protected FavoriteDao favoriteDao;
	
	@Override
	public void init() throws ServletException {
		userDao = UserDao.getInstance();
		favoriteDao = FavoriteDao.getInstance();
	}
	
	@Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        User user = null;
        List<Favorite> favorites = new ArrayList<>();
        
        String userName = req.getParameter("username");
    	try {
        	user = userDao.getUserByUserName(userName);
        	favorites = favoriteDao.getFavoritesForUser(user);
        } catch (SQLException e) {
			e.printStackTrace();
			throw new IOException(e);
        }
        req.setAttribute("user", user);
        req.setAttribute("favorites", favorites);
        
        req.getRequestDispatcher("/MatchingUser.jsp").forward(req, resp);
    }
}
