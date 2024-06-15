package Servlets;

import Accounts.SqlAccountDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Objects;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
                request.getRequestDispatcher("/IllegalLoginPage.jsp").forward(request, response);
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
            request.getRequestDispatcher("/IllegalLoginPage.jsp").forward(request, response);
            return;
        }

        try {
            request.getServletContext().setAttribute("username", db.getUserName(email));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        request.getRequestDispatcher("home").forward(request, response);
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
