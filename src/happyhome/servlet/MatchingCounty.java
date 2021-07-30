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

import happyhome.dal.CountyDao;
import happyhome.dal.CountyHousePriceDao;
import happyhome.dal.CountyHousePriceForecastDao;
import happyhome.dal.DisasterDao;
import happyhome.dal.FavoriteDao;
import happyhome.dal.ReportDao;
import happyhome.model.County;
import happyhome.model.CountyHousePrice;
import happyhome.model.CountyHousePriceForecast;
import happyhome.model.Disaster;
import happyhome.model.Favorite;
import happyhome.model.Report;

@WebServlet("/matchingcounty")
public class MatchingCounty extends HttpServlet {
	protected CountyDao countyDao;
	protected CountyHousePriceDao countyHousePriceDao;
	protected CountyHousePriceForecastDao countyHousePriceForecastDao;
	protected ReportDao reportDao;
	protected DisasterDao disasterDao;
	protected FavoriteDao favoriteDao;
	
	@Override
	public void init() throws ServletException {
		countyDao = CountyDao.getInstance();
		countyHousePriceDao = CountyHousePriceDao.getInstance();
		countyHousePriceForecastDao = CountyHousePriceForecastDao.getInstance();
		reportDao = ReportDao.getInstance();
		disasterDao = DisasterDao.getInstance();
		favoriteDao = FavoriteDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Map<String, String> messages = new HashMap<String, String>();
		req.setAttribute("messages", messages);
		
		County county = null;
		CountyHousePrice countyHousePrice = null;
		CountyHousePriceForecast countyHousePriceForecast = null;
		Report report = null;
		List<Disaster> disasters = new ArrayList<>();
		List<Favorite> favorites = new ArrayList<>();
		
		String countyName = req.getParameter("countyname");
		String state = req.getParameter("state");
	
		try {
			county = countyDao.getCountyByCountyNameAndState(countyName, state);
			countyHousePrice = countyHousePriceDao.getCountyHousePriceByFipsCountyCode(
					county.getFipsCountyCode());
			countyHousePriceForecast = countyHousePriceForecastDao.
					getCountyHousePriceForecastByFipsCountyCode(county.getFipsCountyCode());
			report = reportDao.getReportForCounty(county);
			disasters = disasterDao.getDisastersForCounty(county);
			favorites = favoriteDao.getFavoritesForCounty(county);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new IOException(e);
		}
		
		req.setAttribute("county", county);
		req.setAttribute("housePrice", countyHousePrice);
		req.setAttribute("housePriceForecast", countyHousePriceForecast);
		req.setAttribute("report", report);
		req.setAttribute("disasters", disasters);
		req.setAttribute("favorites", favorites.size());
		req.getRequestDispatcher("/MatchingCounty.jsp").forward(req, resp);
	}
}
