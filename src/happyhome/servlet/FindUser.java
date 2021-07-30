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

@WebServlet("/finduser")
public class FindUser extends HttpServlet {
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
        
        User user = null;
        
        String userName = req.getParameter("username");
        if (userName == null || userName.trim().isEmpty()) {
        } else {
        	try {
            	user = userDao.getUserByUserName(userName);
            } catch (SQLException e) {
    			e.printStackTrace();
    			throw new IOException(e);
            }
        	// Save the previous search term, so it can be used as the default
        	// in the input box when rendering FindUser.jsp.
        	messages.put("previousUserName", userName);
        }
        req.setAttribute("user", user);
        
        req.getRequestDispatcher("/FindUser.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        User user = null;
        
        String userName = req.getParameter("username");
        if (userName == null || userName.trim().isEmpty()) {
            messages.put("success", "Please enter a valid username.");
        } else {
        	try {
            	user = userDao.getUserByUserName(userName);
            } catch (SQLException e) {
    			e.printStackTrace();
    			throw new IOException(e);
            }
        }
        
        if (user == null) {
        	messages.put("success", "Please enter a valid username.");
        	req.getRequestDispatcher("/FindUser.jsp").forward(req, resp);
        } else {
			resp.sendRedirect("matchinguser?username="+user.getUserName());
        }
    }
}
