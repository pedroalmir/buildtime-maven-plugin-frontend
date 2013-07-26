/**
 * 
 */
package com.pedroalmir.controller;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pedroalmir.repository.BuildInformationDAO;

/**
 * @author Pedro Almir
 *
 */
public class ServletRemoveBuildInformation extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String id = request.getParameter("id");
		
		BuildInformationDAO dao = new BuildInformationDAO();
		dao.remove(Long.parseLong(id));
		
		response.sendRedirect("/");
	}
}
