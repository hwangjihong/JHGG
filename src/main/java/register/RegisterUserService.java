package register;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.CommandHandler;

public class RegisterUserService implements CommandHandler {

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, SQLException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=UTF-8");
		String id = null;
		String password = null;
		String passwordC = null;
		String nickname = null;
		String email = null;

		if (request.getParameter("id") != null) {
			id = request.getParameter("userID");
		}
		if (request.getParameter("password") != null) {
			password = request.getParameter("userEmail");
		}
		if (request.getParameter("passwordC") != null) {
			passwordC = request.getParameter("userPassword");
		}
		if (request.getParameter("nickname") != null) {
			nickname = request.getParameter("userPasswordRe");
		}
		if (request.getParameter("email") != null) {
			email = request.getParameter("userName");
		}
		
		if (id == null || password == null || nickname == null || email == null) {
			PrintWriter script = response.getWriter();
			script.print("<script>");
			script.print("alert('입력이 안 된 사항이 있습니다.');");
			script.print("history.back();");
			script.print("</script>");
			script.close();
		} else if (!password.equals(passwordC)) {
			PrintWriter script = response.getWriter();
			script.print("<script>");
			script.print("alert('비밀번호 확인이 틀립니다.\\n비밀번호 확인을 다시 입력해주세요.');");
			script.print("history.back();");
			script.print("</script>");
			script.close();
		}
		
	
		return "login";
	}

}
