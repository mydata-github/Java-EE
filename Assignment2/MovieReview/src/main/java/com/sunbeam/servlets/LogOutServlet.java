package com.sunbeam.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/logout")
public class LogOutServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		
		HttpSession session = req.getSession();
		
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Logout page</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<h3>Thank For Using Our Application!!</h3><br /><br />");
		out.println("<a href='index.html'>Login Again</a>");
		out.println("</body>");
		out.println("</html>");
		
		session.invalidate();		
	}
}
