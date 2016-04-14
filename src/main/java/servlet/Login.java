
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import javax.servlet.annotation.WebServlet;

@WebServlet(name = "Login", urlPatterns = {"/Login"})

public class Login extends HttpServlet {
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
			String user = request.getParameter("user");
			String pass = request.getParameter("pass");

			if (user != null && pass != null){
				if (user.equals("admin") && pass.equals("admin")){
					response.sendRedirect("adminpanel.jsp");
				} else {
					response.sendRedirect("index.jsp");
				}

			} else {
				response.sendRedirect("index.jsp");
			}

    }


    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
