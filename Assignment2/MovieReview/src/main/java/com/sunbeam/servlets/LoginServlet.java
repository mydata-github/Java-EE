package com.sunbeam.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sunbeam.daos.UsersDao;
import com.sunbeam.pojos.Users;

@WebServlet("/login")
public class LoginServlet extends HttpServlet{
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String email = req.getParameter("email");
		String password = req.getParameter("passwd");
		
		try(UsersDao udao = new UsersDao()) {
			Users user = udao.signin(email);
			
			if(user != null && user.getPasswd().equals(password)) {
				HttpSession session = req.getSession();
				session.setAttribute("CurrUser", user);
				resp.sendRedirect("reviewlist");
				
			} else {
				resp.setContentType("text/html");
				PrintWriter out = resp.getWriter();
				
				out.println("<html>");
				out.println("<head>");
				out.println("<title>Login Failed</title>");
				out.println("</head>");
				out.println("<body>");
				out.println("<h3>Invalid Login Credentials!!</h3>");
				out.println("<a href='index.html'>Try again</a>");
				out.println("</body>");
				out.println("</html>");
			}
		} catch(Exception e) {
			throw new ServletException(e);
		}
	}
}
