package users;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.CommandHandler;
import util.SendMail;

public class LoginService implements CommandHandler{

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, SQLException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=UTF-8");
		
		String requestPage = request.getRequestURI().replaceAll(request.getContextPath() + "/","");
		
		String viewPage = null;
		// 받은 do에 따라 실행다르게
		switch (requestPage) {
			case "login.do": {
				viewPage = "login";
				break;
			} case "loginAction.do": {
				viewPage = loginAction(request, response);
				break;
			}
		}
		return viewPage;
	}

	private String loginAction(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		String id = null;
		String password = null;
		
		if(request.getParameter("id") != null) {
			id = request.getParameter("id");
		}
		if(request.getParameter("password") != null) {
			password = request.getParameter("password");
		}
		
		if (id == null || password == null) {
			PrintWriter script = response.getWriter();
			script.print("<script>");
			script.print("alert('아이디와 패스워드를 입력해 주세요.');");
			script.print("history.back();");
			script.print("</script>");
			script.close();
			return null;
		}
		
		UsersDAO usersDAO = new UsersDAO();
		
		int result = usersDAO.login(id, password);
		
		if(result == -1) {
			PrintWriter script = response.getWriter();
			script.print("<script>");
			script.print("alert('로그인 실패');");
			script.print("history.back();");
			script.print("</script>");
			script.close();
			return null;
		}
		if(result == 0) {
			PrintWriter script = response.getWriter();
			script.print("<script>");
			script.print("alert('존재하지 않는 아이디입니다.');");
			script.print("history.back();");
			script.print("</script>");
			script.close();
			return null;
		}
		if(result == 1) {
			PrintWriter script = response.getWriter();
			script.print("<script>");
			script.print("alert('잘못된 비밀번호입니다.');");
			script.print("history.back();");
			script.print("</script>");
			script.close();
			return null;
		}
		if(result == 2) {
			result = usersDAO.emailAuthCheck(id); // 로그인이 가능하면 이메일 인증 상태를 체크
			if(result == 0) {
				String email = usersDAO.getEmail(id);
				SendMail.sendMail(email, id);
				PrintWriter script = response.getWriter();
				script.print("<script>");
				script.print("alert('이메일 인증을 해주세요.');");
				script.print("location.href= 'login.do'");
				script.print("</script>");
				script.close();
				return null;
			}
			
			HttpSession session = request.getSession();
			session.setAttribute("id", id);
				
			PrintWriter script = response.getWriter();
			script.print("<script>");
			script.print("alert('로그인 성공');");
			script.print("location.href= 'home.do'");
			script.print("</script>");
			script.close();
		}
		
		return null;
	}
}
