package happyhome.servlet;

import happyhome.dal.*;
import happyhome.model.*;
import happyhome.model.County;

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

@WebServlet("/countydisasters")
public class CountyDisasters extends HttpServlet {
	protected DisasterDao disasterDao;
	protected CountyDao countyDao;
	
	@Override
	public void init() throws ServletException {
		disasterDao = DisasterDao.getInstance();
		countyDao = CountyDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
		
		// Retrieve and validate county and state name
		String countyName = req.getParameter("countyName");
		String state = req.getParameter("state");
		
		if (countyName == null || countyName.trim().isEmpty() 
				|| state == null || state.trim().isEmpty()) {
			messages.put("title", "Please enter valid county and state name.");
		} else {
			messages.put("title", "Disasters for " + countyName + ", " + state);
		}
		
		// Retrieve County, and store in the request
		List<Disaster> disasters = new ArrayList<>();
		try {
			County county = countyDao.getCountyByCountyNameAndState(countyName, state);
			disasters = disasterDao.getDisastersForCounty(county);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new IOException(e);
		}
		
		req.setAttribute("disasters", disasters);
		req.getRequestDispatcher("/CountyDisasters.jsp").forward(req, resp);
	}
}
