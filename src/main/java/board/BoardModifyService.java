package board;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.CommandHandler;

public class BoardModifyService implements CommandHandler{

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, SQLException, IOException {
		BoardDAO boardDAO = new BoardDAO();
		BoardDTO boardDTO = boardDAO.getBoard(Integer.parseInt(request.getParameter("boardId")));
		
		request.setAttribute("board", boardDTO);
		
		return "boardWrite";
	}

}
