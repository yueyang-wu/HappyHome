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

/**
 * FindCounty is the primary entry point into the application.
 */
@WebServlet("/findcounty")
public class FindCounty extends HttpServlet {
	protected CountyDao countyDao;
	protected CountyHousePriceDao countyHousePriceDao;
	protected CountyHousePriceForecastDao countyHousePriceForecastDao;
	protected ReportDao reportDao;
	protected FavoriteDao favoriteDao;
	
	@Override
	public void init() throws ServletException {
		countyDao = CountyDao.getInstance();
		countyHousePriceDao = CountyHousePriceDao.getInstance();
		countyHousePriceForecastDao = CountyHousePriceForecastDao.getInstance();
		reportDao = ReportDao.getInstance();
		favoriteDao = FavoriteDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException {
		// Map for sorting messages
		Map<String, String> messages = new HashMap<String, String>();
		req.setAttribute("messages", messages);
		
		County county = null;
//		Report report = null;
		
		// Retrieve and validate county and state name
		String countyName = req.getParameter("countyName");
		String state = req.getParameter("state");
		if (countyName == null || countyName.trim().isEmpty() 
				|| state == null || state.trim().isEmpty()) {
			messages.put("success", "Please enter valid county and state name.");
		} else {
			// retrieve County and store as a message
			try {
				county = countyDao.getCountyByCountyNameAndState(countyName, state);
//				report = reportDao.getReportForCounty(county);
			} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
			}
			messages.put("success", "Displaying results for " + countyName + ", " + state);
			// save the previous search term so it can be used as the default
			// in the input box when rendering FindCounty.jsp
			messages.put("previousCountyName", countyName);
			messages.put("previousState", state);
		}
		req.setAttribute("county", county);
//		req.setAttribute("report", report);
		req.getRequestDispatcher("/FindCounty.jsp").forward(req, resp);
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException {
		// Map for sorting messages
		Map<String, String> messages = new HashMap<String, String>();
		req.setAttribute("messages", messages);
		
		County county = null;
		CountyHousePrice countyHousePrice = null;
		CountyHousePriceForecast countyHousePriceForecast = null;
		Report report = null;
		List<Favorite> favorites = null;
		
		// Retrieve and validate county and state name
		String countyName = req.getParameter("countyName");
		String state = req.getParameter("state");
		if (countyName == null || countyName.trim().isEmpty() 
				|| state == null || state.trim().isEmpty()) {
			messages.put("success", "Please enter valid county and state name.");
		} else {
			// retrieve County and store as a message
			try {
				county = countyDao.getCountyByCountyNameAndState(countyName, state);
				countyHousePrice = countyHousePriceDao.getCountyHousePriceByFipsCountyCode(
						county.getFipsCountyCode());
				countyHousePriceForecast = countyHousePriceForecastDao.
						getCountyHousePriceForecastByFipsCountyCode(county.getFipsCountyCode());
				report = reportDao.getReportForCounty(county);
				favorites = favoriteDao.getFavoritesForCounty(county);
			} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
			}
			messages.put("success", "Displaying results for " + countyName + ", " + state);
		}
		req.setAttribute("county", county);
		req.setAttribute("housePrice", countyHousePrice);
		req.setAttribute("housePriceForecast", countyHousePriceForecast);
		req.setAttribute("report", report);
		req.setAttribute("popularity", favorites.size());
		req.getRequestDispatcher("/FindCounty.jsp").forward(req, resp);
	}
}