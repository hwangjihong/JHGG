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
		
		String id = request.getParameter("id");
		String code = request.getParameter("code");
		
		UsersDAO usersDAO = new UsersDAO();
		
		int result = usersDAO.emailAuthCheck(id, code);
		if(result == 1) {
			PrintWriter script = response.getWriter();
			script.print("<script>");
			script.print("alert('이미 이메일 인증이 된 아이디입니다.');");
			script.print("location.href='home.do'");
			script.print("</script>");
			script.close();
		} else {		
			result = usersDAO.emailAuth(id, code);
			
			if(result == -1) {
				PrintWriter script = response.getWriter();
				script.print("<script>");
				script.print("alert('이메일 인증 실패');");
				script.print("location.href='home.do'");
				script.print("</script>");
				script.close();
			}else {
				PrintWriter script = response.getWriter();
				script.print("<script>");
				script.print("alert('이메일 인증 성공');");
				script.print("location.href='home.do'");
				script.print("</script>");
				script.close();		
			}
		}
		return null;
	}
	
}
