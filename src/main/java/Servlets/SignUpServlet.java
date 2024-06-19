package Servlets;

import Accounts.Account;
import Accounts.SqlAccountDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

@WebServlet("/signUp")
public class SignUpServlet extends HttpServlet {
    private static final String USER = "user";
    private static final String ILLEGAL = "illegal";
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/SignUpPage.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        SqlAccountDao db = (SqlAccountDao) request.getServletContext().getAttribute("accounts_db");

        checkEmptyFields(request, response);

        try {
            if(db.emailExist(email)){
                response.addCookie(new Cookie(ILLEGAL, ILLEGAL));
                request.getRequestDispatcher("/SignUpPage.jsp").forward(request, response);
                return;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        PasswordHashMaker passHash;
        try {
            passHash = new PasswordHashMaker(password);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        Account acc = new Account(email, passHash.getPasswordHash(), username);
        try {
            db.add(acc);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        request.getServletContext().setAttribute("username", username);
        try {
            response.addCookie(new Cookie(USER, "" + db.getUserID(email)));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        response.sendRedirect("home");
        //request.getRequestDispatcher("home").forward(request, response);
    }

    private void checkEmptyFields(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if(email == null){
            request.getRequestDispatcher("/SignUpPage.jsp").forward(request, response);
            return;
        }

        if(username == null){
            request.getRequestDispatcher("/SignUpPage.jsp").forward(request, response);
            return;
        }

        if(password == null){
            request.getRequestDispatcher("/SignUpPage.jsp").forward(request, response);
            return;
        }

        if(!(email.indexOf('@') > -1)){
            request.getRequestDispatcher("/SignUpPage.jsp").forward(request, response);
        }
    }
}
