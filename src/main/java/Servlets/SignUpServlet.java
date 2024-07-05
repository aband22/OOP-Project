package Servlets;

import Accounts.Account;
import Accounts.SqlAccountDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.sql.Statement;

@WebServlet("/signUp")
public class SignUpServlet extends HttpServlet {
    private static final String USER = "curUser";
    private static final String ILLEGAL = "illegal";
    private static final String LOGGED_IN_STATUS = "logged_in_status";
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        Cookie[] cookies = request.getCookies();
//        if (cookies != null) {
//            for (Cookie c : cookies) {
//                if (c.getName().equals(USER)) {
//                    request.removeAttribute(USER);
//                }
//            }
//        }
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
                //response.addCookie(new Cookie(ILLEGAL, ILLEGAL));
                request.setAttribute(ILLEGAL, ILLEGAL);
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
        int currID;
        try {
            currID = db.getUserID(email);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        request.getServletContext().setAttribute(USER, "" + currID);
        request.getServletContext().setAttribute(LOGGED_IN_STATUS, "in");

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
