package com.sunbeam.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.connector.Response;

import com.sunbeam.daos.MoviesDao;
import com.sunbeam.daos.ReviewsDao;
import com.sunbeam.pojos.Movies;
import com.sunbeam.pojos.Reviews;
import com.sunbeam.pojos.Users;

@WebServlet("/edit")
public class EditServlet extends HttpServlet{
	
		@Override
		protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			resp.setContentType("text/html");
			PrintWriter out = resp.getWriter();
			
			int id = Integer.parseInt(req.getParameter("reviewid"));
			
			out.println("<html>");
			out.println("<head>");
			out.println("<title>Edit Review</title>");
			out.println("</head>");
			out.println("<body>");
			out.println("<form method='post' action='edit'>");
			Reviews r = null;
			try(ReviewsDao rdao = new ReviewsDao()){
				r = rdao.findById(id);
			} catch(Exception e) {
				throw new ServletException(e);
			}
			out.println("Id : <input type='number' value='"+ r.getId() +"' name='id' readonly /><br/><br/>");
			try(MoviesDao mdao = new MoviesDao()) {
				Movies m = mdao.findById(r.getMovie_id());
				out.println("Movie : <input type='text' value='"+ m.getTitle() +"' name='movie' readonly /> <br/><br/>");
			} catch (Exception e) {
				throw new ServletException(e);
			}
			out.println("<label for='rating'>Rating (between 0 and 10):</label>");
			out.println("<input type='range' name='rating' min='1' max='10' value='"+ r.getRating() +"' /><br /><br />");
			out.println("Review : <input type='text' value='"+ r.getReview() +"' name='review' /><br/><br/>");
			out.println("<input type='submit' value='Update' />");
			out.println("</form>");
			out.println("<form>");
			out.println("</body>");
			out.println("</html>");
		}
		
		@Override
		protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			HttpSession session = req.getSession();
			Users currUser = (Users) session.getAttribute("CurrUser");
			
			int id = Integer.parseInt(req.getParameter("id"));
			String review = req.getParameter("review");
			int rating = Integer.parseInt(req.getParameter("rating"));
			
			try(ReviewsDao rdao = new ReviewsDao()) {
				int cnt = rdao.editReview(review, currUser.getId(), id, rating);
			} catch(Exception e) {
				throw new ServletException(e);
			}
			
			resp.sendRedirect("reviewlist");
		}
		
}
