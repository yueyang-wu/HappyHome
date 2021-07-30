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

@WebServlet("/userupdate")
public class UserUpdate extends HttpServlet {
	protected UserDao userDao;
	
	@Override
	public void init() throws ServletException {
		userDao = UserDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        String userName = req.getParameter("username");
        if (userName == null || userName.trim().isEmpty()) {
            messages.put("success", "Please enter a valid username.");
        } else {
        	try {
        		User user = userDao.getUserByUserName(userName);
        		if(user == null) {
        			messages.put("success", "UserName does not exist.");
        		}
        		req.setAttribute("user", user);
        	} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }
        
        req.getRequestDispatcher("/UserUpdate.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        User user = null;
        
        String userName = req.getParameter("username");
        String providedZip = req.getParameter("newzipcode");
        if (userName == null || userName.trim().isEmpty()) {
            messages.put("success", "Please update a valid user.");
        } else {
        	try {
        		user = userDao.getUserByUserName(userName);
        		if (user == null) {
        			messages.put("success", userName + "does not exist. No update to perform.");
                } else if (providedZip == null || providedZip.trim().isEmpty()) {
                	messages.put("success", "Please enter a valid ZIP code.");
                } else {
                	Integer newZip = Integer.parseInt(providedZip);
        	        user = userDao.updateCurrentZip(user, newZip);
        	        messages.put("success", "Successfully updated!");
        		}
        	} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }
		req.setAttribute("user", user);
        
        req.getRequestDispatcher("/UserUpdate.jsp").forward(req, resp);
    }
}