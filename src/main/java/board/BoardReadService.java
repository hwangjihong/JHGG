package board;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.CommandHandler;

public class BoardReadService implements CommandHandler{

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, SQLException, IOException {
		BoardDAO boradDAO = new BoardDAO();
		BoardDTO boardDTO = new BoardDTO();
		boradDAO.incrementTarget(Integer.parseInt(request.getParameter("boardId")));
		boardDTO = boradDAO.getBoard(Integer.parseInt(request.getParameter("boardId")));
		boardDTO.setContent(boardDTO.getContent().replace("\r\n", "<br>"));
		
		HttpSession session = request.getSession();
		
		request.setAttribute("board", boardDTO);

		return "boardRead";
	}

}
