package board;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.CommandHandler;

public class BoardSearchService implements CommandHandler{

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, SQLException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=UTF-8");
		

		String data = null;
		if(request.getParameter("data") == null) {
			PrintWriter script = response.getWriter();
			script.print("<script>");
			script.print("alert('검색할 제목을 입력해주세요.');");
			script.print("history.back();");
			script.print("</script>");
			script.close();
			return null;
		} else {
			data = request.getParameter("data");
		}
		
		BoardDAO boardDAO = new BoardDAO();
		int page = 1;
		int limit = 5;	
		int listCount = boardDAO.getListCount(data);
		
		if(request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
		}
		
		int startPage = (page - 1) * limit;
		
		int startRow = (page - 1) * limit;
			
		int pageCount = (int) Math.ceil((double) listCount / limit);
		ArrayList<BoardDTO> list =  boardDAO.getBoardList(startRow, limit, data);
		request.setAttribute("list", list);
		request.setAttribute("startRank", startPage);
		request.setAttribute("page", page);
		request.setAttribute("pageCount", pageCount);
		request.setAttribute("data", data);
		
		return "board";
	}

}
