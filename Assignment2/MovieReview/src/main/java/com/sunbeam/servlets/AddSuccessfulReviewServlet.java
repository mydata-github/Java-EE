package com.sunbeam.servlets;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sunbeam.daos.ReviewsDao;
import com.sunbeam.pojos.Reviews;
import com.sunbeam.pojos.Users;

@WebServlet("/addreview")
public class AddSuccessfulReviewServlet extends HttpServlet{
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		Users currUser = (Users) session.getAttribute("CurrUser");
		
		int movie_id = Integer.parseInt(req.getParameter("movie"));
		int rating = Integer.parseInt(req.getParameter("rating"));
		String review = req.getParameter("review");
		int user_id = currUser.getId();
		
		Reviews r = new Reviews(1, movie_id, review, rating, user_id, new Date());
		
		try(ReviewsDao rdao = new ReviewsDao()){
			int cnt = rdao.insReview(r);
			
			if(cnt == 1) {
				System.out.println("Successfull");
				resp.sendRedirect("reviewlist");
			}
		} catch(Exception e) {
			throw new ServletException(e);
		}
	}
}
