package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import javax.servlet.annotation.WebServlet;

import util.SetOperations;

@WebServlet(name = "NextServlet", urlPatterns = {"/NextServlet"})

public class NextServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
			PrintWriter out = response.getWriter();
			String presvote = request.getParameter("presvote");
			String vpvote = request.getParameter("vpvote");
			
			if (presvote != null && vpvote != null){
				SetOperations presidents = new SetOperations("president");
				presidents.incrementScore(presvote);
				
				SetOperations vpresidents = new SetOperations("vice_president");
				vpresidents.incrementScore(vpvote);
			}

	
			
			request.setAttribute("pres", presvote);
			
			response.sendRedirect("votesuccess.jsp");
			
    }
	
	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
			PrintWriter out = response.getWriter();
			out.println("Wrong page");
    }
	
	 @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
	
}