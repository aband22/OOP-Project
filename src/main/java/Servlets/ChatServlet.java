package Servlets;

import Accounts.Account;
import Accounts.SqlAccountDao;
import Accounts.SqlAccountInfoDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/chat")
public class ChatServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        HttpSession session = httpServletRequest.getSession();
        String user = (String) httpServletRequest.getSession().getAttribute("curUser");
        int curUserId = 0;
        if(user != null)  curUserId = Integer.parseInt(user);
        else {
            httpServletRequest.getRequestDispatcher("/ErrorPage.jsp").forward(httpServletRequest, httpServletResponse);
            return;
        }
        SqlAccountInfoDao accInfo = (SqlAccountInfoDao) getServletContext().getAttribute("accountInfo_db");
        SqlAccountDao accs = (SqlAccountDao) getServletContext().getAttribute("accounts_db");
        List<Integer> friendsIds = null;
        try {
            friendsIds = accInfo.getAllFriendsId(curUserId);
        } catch (SQLException e) {
            httpServletRequest.getRequestDispatcher("/ErrorPage.jsp").forward(httpServletRequest, httpServletResponse);
            return;
        }
        List<Account> friendsIdAndNames = new ArrayList<>();
        for(Integer id : friendsIds) {
            try {
                Account acc = new Account();
                acc.setId(id);
                acc.setUsername(accs.getNameById(id));
                friendsIdAndNames.add(acc);
            } catch (SQLException e) {
                httpServletRequest.getRequestDispatcher("/ErrorPage.jsp").forward(httpServletRequest, httpServletResponse);
                return;
            }
        }
        httpServletRequest.getServletContext().setAttribute("friends", friendsIdAndNames);
        if(httpServletRequest.getParameter("friendId") != null) {
            int id = Integer.parseInt(httpServletRequest.getParameter("friendId"));
            httpServletRequest.setAttribute("friendId", id);
            try {
                httpServletRequest.setAttribute("friendName", accs.getAccountById(id).getUsername());
            } catch (SQLException e) {
                httpServletRequest.getRequestDispatcher("/ErrorPage.jsp").forward(httpServletRequest, httpServletResponse);
                return;
            }
        }
        httpServletRequest.getRequestDispatcher("/ChatPage.jsp").forward(httpServletRequest, httpServletResponse);
    }
}