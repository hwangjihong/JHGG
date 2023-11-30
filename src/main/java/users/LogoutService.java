package users;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.CommandHandler;

public class LogoutService implements CommandHandler{

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, SQLException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=UTF-8");
		
		HttpSession session = request.getSession();
		session.invalidate();

		PrintWriter script = response.getWriter();
		script.print("<script>");
		script.print("alert('로그아웃');");
		script.print("location.href='home.do'");
		script.print("</script>");
		script.close();
		
		return null;
	}

}
