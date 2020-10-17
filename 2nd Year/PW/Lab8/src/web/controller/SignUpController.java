package web.controller;

import web.domain.User;
import web.exceptions.CustomException;
import web.utils.DatabaseConnection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

public class SignUpController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        RequestDispatcher requestDispatcher = null;

        try {
            User user = databaseConnection.signUp(username, password, email);
            if (user!=null){
                requestDispatcher = req.getRequestDispatcher("/gallery.jsp");
                HttpSession session = req.getSession();
                session.setAttribute("user", user);

            }
        } catch (SQLException | CustomException ex) {
            requestDispatcher = req.getRequestDispatcher("/redirect.jsp?error=" + ex);

        }
        requestDispatcher.forward(req, resp);

        databaseConnection.disconnect();
    }
}
