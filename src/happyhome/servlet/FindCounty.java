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

/**
 * FindCounty is the primary entry point into the application.
 */
@WebServlet("/findcounty")
public class FindCounty extends HttpServlet {
	protected CountyDao countyDao;
	
	@Override
	public void init() throws ServletException {
		countyDao = CountyDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException {
		Map<String, String> messages = new HashMap<String, String>();
		req.setAttribute("messages", messages);
		
		County county = null;
		
		String countyName = req.getParameter("countyname");
		String state = req.getParameter("state");

		try {
			county = countyDao.getCountyByCountyNameAndState(countyName, state);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new IOException(e);
		}
		// Save the previous search term so it can be used as the default
		// in the input box when rendering FindCounty.jsp
		messages.put("previousCountyName", countyName);
		messages.put("previousState", state);
		
		req.setAttribute("county", county);
		req.getRequestDispatcher("/FindCounty.jsp").forward(req, resp);
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException {
		Map<String, String> messages = new HashMap<String, String>();
		req.setAttribute("messages", messages);
		
		County county = null;
		
		String countyName = req.getParameter("countyname");
		String state = req.getParameter("state");
		if (countyName == null || countyName.trim().isEmpty() 
				|| state == null || state.trim().isEmpty()) {
			messages.put("success", "Please enter a valid county name and state/territory.");
		} else {
			try {
				county = countyDao.getCountyByCountyNameAndState(countyName, state);
			} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
			}
		}
		
		if (county == null) {
			messages.put("success", "Please enter a valid county name and state/territory.");
            req.getRequestDispatcher("/FindCounty.jsp").forward(req, resp);
		} else {
			resp.sendRedirect("matchingcounty?countyname="+county.getCountyName()+"&state="+county.getState());
		}
	}
}