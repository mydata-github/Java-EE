package com.sunbeam.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sunbeam.daos.ReviewsDao;
import com.sunbeam.pojos.Users;

@WebServlet("/delete")
public class DeleteServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		Users currUser = (Users) session.getAttribute("CurrUser");
	
		int id = Integer.parseInt(req.getParameter("reviewid"));
		
		try(ReviewsDao rdao = new ReviewsDao()) {
			int cnt = rdao.deleteReview(id, currUser.getId());
		} catch(Exception e) {			
			throw new ServletException(e);
		}
		
		RequestDispatcher rd = req.getRequestDispatcher("reviewlist");
		rd.forward(req, resp);
	}
}
