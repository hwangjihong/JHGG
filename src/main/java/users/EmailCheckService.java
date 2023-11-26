package users;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.CommandHandler;

public class EmailCheckService implements CommandHandler{

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, SQLException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=UTF-8");
		
		UsersDAO usersDAO = new UsersDAO();
		int result = usersDAO.emailAuth(request.getParameter("code"));
		
		if(result == -1) {
			PrintWriter script = response.getWriter();
			script.print("<script>");
			script.print("alert('이메일 인증 실패');");
			script.print("location.href='home.do'");
			script.print("</script>");
			script.close();
		}else {
			String id = usersDAO.getID(request.getParameter("code"));
			
			HttpSession session = request.getSession();
			session.setAttribute("id", id);
			
			PrintWriter script = response.getWriter();
			script.print("<script>");
			script.print("alert('이메일 인증 성공');");
			script.print("location.href='home.do'");
			script.print("</script>");
			script.close();
		}
		
		return null;
	}
	
}
