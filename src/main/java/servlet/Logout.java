
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import javax.servlet.annotation.WebServlet;

@WebServlet(name = "Logout", urlPatterns = {"/Logout"})

public class Logout extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
			PrintWriter out = response.getWriter();
			out.println("Wrong page");
    }

	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
			PrintWriter out = response.getWriter();
			HttpSession session = request.getSession();
			session.invalidate();
			response.sendRedirect("index.jsp");
    }


    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
