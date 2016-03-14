package next.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import core.db.DataBase;
import next.model.User;

@WebServlet("/user/login")
public class LoginUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestDispatcher rd = req.getRequestDispatcher("/user/login.jsp");
		rd.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User user = DataBase.findUserById(req.getParameter("userId"));

		if (user == null || !user.getPassword().equals(req.getParameter("password"))) {
			RequestDispatcher rd = req.getRequestDispatcher("/user/login_failed.jsp");
			rd.forward(req, resp);
		}

		HttpSession session = req.getSession();
		session.setAttribute("user", user);

		resp.sendRedirect("/user/list");
	}
}
