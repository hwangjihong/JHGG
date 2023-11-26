package users;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.CommandHandler;
import util.SHA256;
import util.SendMail;

public class RegisterService implements CommandHandler {

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, SQLException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=UTF-8");
		
		String requestPage = request.getRequestURI().replaceAll(request.getContextPath() + "/","");
		
		String viewPage = null;
		// 받은 do에 따라 실행다르게
		switch (requestPage) {
			case "register.do": {
				viewPage = "register";
				break;
			} case "registerAction.do": {
				viewPage = registerAction(request, response);
				break;
			}
		}
		return viewPage;
	}
	
	public String registerAction(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		String id = null;
		String password = null;
		String passwordCheck = null;
		String nickname = null;
		String email = null;

		if (request.getParameter("id") != null) {
			id = request.getParameter("id");
		}
		if (request.getParameter("password") != null) {
			password = request.getParameter("password");
		}
		if (request.getParameter("passwordCheck") != null) {
			passwordCheck = request.getParameter("passwordCheck");
		}
		if (request.getParameter("nickname") != null) {
			nickname = request.getParameter("nickname");
		}
		if (request.getParameter("email") != null) {
			email = request.getParameter("email");
		}
		
		if (id == null || password == null || nickname == null || email == null) {
			PrintWriter script = response.getWriter();
			script.print("<script>");
			script.print("alert('회원가입 정보를 입력해 주세요.');");
			script.print("history.back();");
			script.print("</script>");
			script.close();
			return null;
		} else if (!password.equals(passwordCheck)) {
			PrintWriter script = response.getWriter();
			script.print("<script>");
			script.print("alert('비밀번호 확인이 틀립니다.\\n비밀번호 확인을 다시 입력해주세요.');");
			script.print("history.back();");
			script.print("</script>");
			script.close();
			return null;
		}
		
		UsersDTO user = new UsersDTO();
		
		user.setId(id);
		user.setPassword(password);
		user.setNickname(nickname);
		user.setEmail(email);
		user.setEmailHash(SHA256.getSHA256(email));
		user.setEmailAuth(0); // 이메일 인증 0 : 안됨 // 1 : 됨
		user.setType(0); // 유저 타입 0 : 일반 // 1 : 관리자
		
		UsersDAO usersDAO = new UsersDAO();
		
		int result = usersDAO.join(user);
		
		if (result == -1) {
			PrintWriter script = response.getWriter();
			script.print("<script>");
			script.print("alert('이미 존재하는 아이디입니다.');");
			script.print("history.back();");
			script.print("</script>");
			script.close();
			return null;
		} else {
			SendMail.sendMail(email);
			PrintWriter script = response.getWriter();
			script.print("<script>");
			script.print("alert('회원가입 완료.\\n입력한 이메일에 인증 메일을 보냈습니다.\\n인증해 주세요.');");
			script.print("location.href = 'login.do';");
			script.print("</script>");
		}
			
		return null;
	}
}
