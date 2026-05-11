package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import za.ac.tut.entity.User;
import za.ac.tut.entity.bl.UserFacadeLocal;

/**
 *
 * @author moses
 */
public class LoginServlet extends HttpServlet {
@EJB
    private UserFacadeLocal ufl;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("username"); 
        String password = request.getParameter("password");
        
        User user = authenticate(email, password);

        String u = "login.jsp"; 

        if (user != null) {
            
            HttpSession session = request.getSession(true);
            session.setAttribute("user", user);
            u = "studentDashboard.jsp"; 
        } else {
           
            request.setAttribute("errorMassage", "Invalid email or password. Please try again.");
        }
        RequestDispatcher disp = request.getRequestDispatcher(u);
        disp.forward(request, response);
    }

    private User authenticate(String email, String password) {
    
        User user = ufl.findByEmail(email); 

        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
       
}
}
