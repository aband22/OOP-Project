package Servlets;

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
import java.util.Objects;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final String USER = "curUser";
    private static final String ILLEGAL = "illegal";
    private static final String LOGGED_IN_STATUS = "logged_in_status";
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getServletContext().removeAttribute(LOGGED_IN_STATUS);
        request.getServletContext().setAttribute(USER, "" + 0);
        request.getRequestDispatcher("/LoginPage.jsp").forward(request, response);
//        ServletContext context = request.getServletContext();
//        AccountDao store;
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//        try {
//            store = new SqlAccountDao(ConnectionManager.getInstance());
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        context.setAttribute("store", store);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        checkEmptyFields(request, response);

        SqlAccountDao db = (SqlAccountDao) request.getServletContext().getAttribute("accounts_db");
        String passFromDB = "";
        try {
            passFromDB = db.getAccPass(email);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            if(!db.emailExist(email)){
                request.setAttribute(ILLEGAL, ILLEGAL);
                request.getRequestDispatcher("/LoginPage.jsp").forward(request, response);
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
        if(!Objects.equals(passFromDB, passHash.getPasswordHash())){
            request.setAttribute(ILLEGAL, ILLEGAL);
            request.getRequestDispatcher("/LoginPage.jsp").forward(request, response);
            return;
        }

        try {
            request.getServletContext().setAttribute("username", db.getUserName(email));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            //response.addCookie(new Cookie(USER, "" + db.getUserID(email)));
            request.getServletContext().setAttribute(USER, "" + db.getUserID(email));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        request.getServletContext().setAttribute(LOGGED_IN_STATUS, "in");
        response.sendRedirect("home");
        //request.getRequestDispatcher("home").forward(request, response);
    }

    private void checkEmptyFields(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if(email == null){
            request.getRequestDispatcher("/LoginPage.jsp").forward(request, response);
            return;
        }

        if(password == null){
            request.getRequestDispatcher("/LoginPage.jsp").forward(request, response);
            return;
        }

        if(!(email.indexOf('@') > -1)){
            request.getRequestDispatcher("/LoginPage.jsp").forward(request, response);
        }
    }
}
