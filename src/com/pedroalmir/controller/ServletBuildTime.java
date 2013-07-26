/**
 * 
 */
package com.pedroalmir.controller;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.pedroalmir.model.BuildInformation;
import com.pedroalmir.repository.BuildInformationDAO;

/**
 * @author Pedro Almir
 *
 */
@SuppressWarnings("serial")
public class ServletBuildTime extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println("Listing build informations...");

		BuildInformationDAO dao = new BuildInformationDAO();
		List<BuildInformation> list = dao.listAll();
		
		request.setAttribute("builds", list);
		
		long totalTime = calcTotalTime(list);
		if(TimeUnit.MILLISECONDS.toHours(totalTime) > 0){
			request.setAttribute("totalElapsedTime", getCompleteFormattedTime(totalTime));
		}else{
			request.setAttribute("totalElapsedTime", getFormattedTime(totalTime));
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
        try {
			dispatcher.forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Saving build informations...");

		BuildInformation buildInformation = new Gson().fromJson(request.getParameter("buildInformation"), BuildInformation.class);
		BuildInformationDAO dao = new BuildInformationDAO();
		dao.save(buildInformation);
		List<BuildInformation> list = dao.listAll();
		
		request.setAttribute("builds", list);
		
		long totalTime = calcTotalTime(list);
		if(TimeUnit.MILLISECONDS.toHours(totalTime) > 0){
			request.setAttribute("totalElapsedTime", getCompleteFormattedTime(totalTime));
		}else{
			request.setAttribute("totalElapsedTime", getFormattedTime(totalTime));
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
        try {
			dispatcher.forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param totalTime
	 * @return formatted time
	 */
	private String getFormattedTime(long totalTime){
		return String.format("%02d min, %02d sec, %03d ms", TimeUnit.MILLISECONDS.toMinutes(totalTime),
			    TimeUnit.MILLISECONDS.toSeconds(totalTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(totalTime)), 
			    totalTime - TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(totalTime)));
	}
	
	/**
	 * @param totalTime
	 * @return formatted time
	 */
	private String getCompleteFormattedTime(long totalTime){
		return String.format("%02d h, %02d min, %02d sec, %03d ms", TimeUnit.MILLISECONDS.toHours(totalTime), TimeUnit.MILLISECONDS.toMinutes(totalTime), 
				TimeUnit.MILLISECONDS.toSeconds(totalTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(totalTime)), 
			    totalTime - TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(totalTime)));
	}
	
	/**
	 * @param builds
	 * @return total Time in miliseconds
	 */
	private long calcTotalTime(List<BuildInformation> builds){
		long amount = 0l;
		for (BuildInformation buildInformation : builds) {
			amount += buildInformation.getElapsedTime();
			buildInformation.setFormattedElapsedTime(getFormattedTime(buildInformation.getElapsedTime()));
		}
		return amount;
	}

}