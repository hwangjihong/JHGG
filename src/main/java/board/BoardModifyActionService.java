package board;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.CommandHandler;

public class BoardModifyActionService implements CommandHandler{

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, SQLException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=UTF-8");
		
		String title = null;
		String content = null;
		if(request.getParameter("contentTitle") != null) {
			 title = request.getParameter("contentTitle");
		}
		if(request.getParameter("contentDetail") != null) {
			content = request.getParameter("contentDetail");
		}		
		if(title == null || content == null) {
			PrintWriter script = response.getWriter();
			script.print("<script>");
			script.print("alert('제목 또는 내용을 입력하세요.');");
			script.print("history.back();");
			script.print("</script>");
			script.close();
		} else {
			BoardDAO boardDAO = new BoardDAO();
			
			int result = boardDAO.modify(Integer.parseInt(request.getParameter("boardId")), title, content);
			
			if(result == -1) {
				PrintWriter script = response.getWriter();
				script.print("<script>");
				script.print("alert('수정에 실패했습니다.');");
				script.print("history.back();");
				script.print("</script>");
				script.close();
			}else {
				PrintWriter script = response.getWriter();
				script.print("<script>");
				script.print("alert('수정 완료');");
				script.print("location.href='board.do'");
				script.print("</script>");
				script.close();
			}
		}
		return null;
	}
	
}
