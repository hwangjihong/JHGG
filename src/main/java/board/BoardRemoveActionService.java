package board;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.CommandHandler;

public class BoardRemoveActionService implements CommandHandler{

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, SQLException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=UTF-8");
		
		BoardDAO boardDAO = new BoardDAO();
		
		int result = boardDAO.removeBoard(Integer.parseInt(request.getParameter("boardId")));
		if(result == -1) {
			PrintWriter script = response.getWriter();
			script.print("<script>");
			script.print("alert('삭제 실패');");
			script.print("history.back();");
			script.print("</script>");
			script.close();
		} else {
			PrintWriter script = response.getWriter();
			script.print("<script>");
			script.print("alert('삭제 성공');");
			script.print("location.href='board.do';");
			script.print("</script>");
			script.close();
		}
		
		return null;
	}

}
