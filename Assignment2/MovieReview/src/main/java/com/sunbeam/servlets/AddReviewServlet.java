package com.sunbeam.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sunbeam.daos.MoviesDao;
import com.sunbeam.pojos.Movies;
import com.sunbeam.pojos.Users;
@WebServlet("/newreview")
public class AddReviewServlet extends HttpServlet{

		@Override
		protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			HttpSession session = req.getSession();
			Users currUser = (Users) session.getAttribute("CurrUser");
			resp.setContentType("text/html");
			PrintWriter out = resp.getWriter();
			
			out.println("<html>");
			out.println("<head>");
			out.println("<title>Add Review</title>");
			out.println("</head>");
			out.println("<body>");
			out.println("<h4> Hello, " + currUser.getFirstName() + " " + currUser.getLastName()+"</h4><br /><hr />");
			out.println("<form method='post' action='addreview'>");
			out.println("<b>Movies : </b>");
			out.println("<select name='movie'>");
			try(MoviesDao mdao = new MoviesDao()){
				List<Movies> list =  mdao.displayAll();
				
				for(Movies m : list) {
					out.println("<option value="+ m.getId() +">" + m.getTitle() +"</option>");
				}
	  		} catch(Exception e) {
				throw new ServletException(e);
			}
			out.println("</select><br /><br /><br />");
			out.println("<label for='rating'>Rating (between 0 and 10):</label>");
			out.println("<input type='range' name='rating' min='1' max='10' /><br /><br />");
			out.println("Review : <input type='text' name='review' /><br /><br />");
			out.println("<input type='submit' value='Save' />");
			out.println("</form>");
			out.println("</body>");
			out.println("</html>");
			
		}
}
