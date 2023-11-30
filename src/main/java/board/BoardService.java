package board;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import summonerRanking.SummonerRankingDTO;
import users.UsersDAO;
import util.CommandHandler;

public class BoardService implements CommandHandler{

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, SQLException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=UTF-8");
		
		String requestPage = request.getRequestURI().replaceAll(request.getContextPath() + "/","");
		
		String viewPage = null;
		// 받은 do에 따라 실행다르게
		switch (requestPage) {
			case "board.do": {
				viewPage = boardListRead(request, response);
				break;
			} case "boardWrite.do": {
				viewPage = boardWrite(request, response);
				break;
			} case "boardWriteAction.do": {
				viewPage = boardWriteAction(request, response);
			}
		}
		return viewPage;
	}
	
	public String boardListRead(HttpServletRequest request, HttpServletResponse response) throws IOException {
		BoardDAO boardDAO = new BoardDAO();
		
		int page = 1;
		int limit = 10;
		int listCount = boardDAO.getListCount();
		
		if(request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
		}
		
		int startPage = (page - 1) * limit;
		
		int startRow = (page - 1) * limit;
		int pageCount = (int) Math.ceil((double) listCount / limit);
		
		ArrayList<BoardDTO> list = boardDAO.getBoardList(startRow, limit);
		
		request.setAttribute("list", list);
		request.setAttribute("startRank", startPage);
		request.setAttribute("page", page);
		request.setAttribute("pageCount", pageCount);
		
		return "board";
	}
	
	public String boardWrite(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		if(session.getAttribute("id") == null) {
			PrintWriter script = response.getWriter();
			script.print("<script>");
			script.print("alert('로그인 후 이용해 주세요.');");
			script.print("location.href='login.do'");
			script.print("</script>");
			script.close();
			return null;
		}
		
		return "boardWrite";
	}
	
	public String boardWriteAction(HttpServletRequest request, HttpServletResponse response) throws IOException {
		UsersDAO usersDAO = new UsersDAO();
		
		HttpSession session = request.getSession();
		
		
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
			String id = (String)session.getAttribute("id");
			String nickname = usersDAO.getNickName(id);
			
			BoardDAO boardDAO = new BoardDAO();
			
			int result = boardDAO.Write(id, nickname, title, content);
			
			if(result == -1) {
				PrintWriter script = response.getWriter();
				script.print("<script>");
				script.print("alert('글쓰기에 실패했습니다.');");
				script.print("history.back();");
				script.print("</script>");
				script.close();
			}else {
				PrintWriter script = response.getWriter();
				script.print("<script>");
				script.print("alert('글쓰기 완료');");
				script.print("location.href='board.do'");
				script.print("</script>");
				script.close();
			}
		}
		return null;
	}
	
}
