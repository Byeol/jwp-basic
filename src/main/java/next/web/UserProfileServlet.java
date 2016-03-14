package next.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import next.model.User;

@WebServlet("/user/profile")
public class UserProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User user = null;

		HttpSession session = req.getSession();
		Object value = session.getAttribute("user");
		if (value != null) {
			user = (User) value;
		}

		if (user == null) {
			resp.sendRedirect("/user/login");
			return;
		}

		req.setAttribute("user", user);

		RequestDispatcher rd = req.getRequestDispatcher("/user/profile.jsp");
		rd.forward(req, resp);
	}
}
