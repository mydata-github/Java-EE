package com.sunbeam.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sunbeam.daos.ShareDao;
import com.sunbeam.daos.UsersDao;
import com.sunbeam.pojos.Users;

@WebServlet("/share")
public class ShareServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		
		int id = Integer.parseInt(req.getParameter("reviewid"));
		
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Share</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<form method='post' action='share'>");
		
		out.println("<input type='number' name='revid' value='"+ id + "' readonly><br/><br/>");
		out.println("<select name='sharedto'>");
		try(UsersDao udao = new UsersDao()){
			List<Users> list = udao.displayAll();
			
			for(Users u : list) {
				out.println("<option value="+ u.getId() +">" + u.getId() +" " + u.getFirstName() +"</option>");
			}
		} catch(Exception e) {
			throw new ServletException(e);
		}
		out.println("</select><br/><br/>");
		out.println("<input type='submit' value='share'>");
		out.println("</form>");
		out.println("</body>");
		out.println("</html>");
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		int id = Integer.parseInt(req.getParameter("revid"));
		int u_id = Integer.parseInt(req.getParameter("sharedto"));
		
		try(ShareDao sdao = new ShareDao()){
			int cnt = sdao.share(id, u_id);
		} catch(Exception e) {
			throw new ServletException(e);
		}
		
		resp.sendRedirect("reviewlist?type=shared");
	}
}
