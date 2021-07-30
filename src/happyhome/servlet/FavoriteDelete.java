package happyhome.servlet;

import happyhome.dal.*;

import happyhome.model.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.annotation.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/favoritedelete")
public class FavoriteDelete extends HttpServlet {
	
	protected FavoriteDao favoriteDao;
	
	@Override
	public void init() throws ServletException {
		favoriteDao = FavoriteDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        req.getRequestDispatcher("/FavoriteDelete.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        String favoriteId = req.getParameter("favoriteid");
        if (favoriteId == null || favoriteId.trim().isEmpty()) {
            messages.put("success", "Please delete a valid favorite.");
            messages.put("disableSubmit", "true");
        } else {
	        Favorite favorite = new Favorite(Integer.parseInt(favoriteId));
	        try {
	        	favorite = favoriteDao.delete(favorite);
		        if (favorite == null) {
		            messages.put("success", "Successfully deleted.");
		            messages.put("disableSubmit", "true");
		        } else {
		        	messages.put("success", "Failed to delete.");
		        	messages.put("disableSubmit", "true");
		        }
	        } catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }
        
        req.getRequestDispatcher("/FavoriteDelete.jsp").forward(req, resp);
    }
}
