package board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import util.DatabaseUtil;

public class BoardDAO {
	
	public String getDate() {
		// 현재 날짜를 얻는 SQL
		String sql = "select now()";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {			
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(sql);		
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) { return rs.getString(1); }
			
		} catch (Exception e) {
			System.out.println("BoardDAO getDate SQL : " +e.getMessage());
		} finally {
			if(rs != null) try {rs.close();}catch(Exception e) {}
			if(pstmt!=null) try{pstmt.close();}catch(Exception e){}
			if(conn!=null) try{conn.close();}catch(Exception e){}
		}
		
		return "";
	}
	
	public int Write(String id, String nickname, String title, String content) { 
		// 게시판에 글을 작성하는 SQL
		String sql = "insert into board(writerId, writerNick, title, content, redate, target, available)"
				+ "value(?, ?, ?, ?, ?, 0, 0)";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {			
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(sql);		
			
			pstmt.setString(1, id);
			pstmt.setString(2, nickname);
			pstmt.setString(3, title);
			pstmt.setString(4, content);
			pstmt.setString(5, getDate());
					
			return pstmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("BoardDAO Write SQL : " +e.getMessage());
		} finally {
			if(pstmt!=null) try{pstmt.close();}catch(Exception e){}
			if(conn!=null) try{conn.close();}catch(Exception e){}
		}
		
		return -1;
	}
	
	public int modify(int boardId, String title, String content) { 
		// 게시판에 글을 작성하는 SQL
		String sql = "update board set title = ?, content = ? where boardId = ?";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {			
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(sql);		
					
			pstmt.setString(1, title);
			pstmt.setString(2, content);
			pstmt.setInt(3, boardId);
			
			return pstmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("BoardDAO Write SQL : " +e.getMessage());
		} finally {
			if(pstmt!=null) try{pstmt.close();}catch(Exception e){}
			if(conn!=null) try{conn.close();}catch(Exception e){}
		}
		
		return -1;
	}
	
	public ArrayList<BoardDTO> getBoardList(int startRow, int limit) {
		// 게시판의 제한된 범위 만큼의 글을 가져오는 SQL (available != 1 이 아닌 리스트만 가져온다 available이 1이면 삭제된 글)
		String sql = "select * from board where available != 1 order by boardId desc limit ?, ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<BoardDTO> list = new ArrayList<>();
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(sql);		
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, limit);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				BoardDTO boardDTO = new BoardDTO();
				boardDTO.setBoardId(rs.getInt("boardId"));  
				boardDTO.setWriterId(rs.getString("writerId"));
				boardDTO.setWriterNick(rs.getString("writerNick"));
				boardDTO.setTitle(rs.getString("title"));
				boardDTO.setContent(rs.getString("content"));
				boardDTO.setRedate(rs.getString("redate"));
				boardDTO.setTarget(rs.getInt("target"));
				list.add(boardDTO);
			}
		} catch (SQLException e) {
			System.out.println("BoardDAO getBoardList(전체) SQLException : " + e.getMessage());
		} finally {
			if(rs != null) try {rs.close();}catch(Exception e) {}
			if(pstmt!=null) try{pstmt.close();}catch(Exception e){}
			if(conn!=null) try{conn.close();}catch(Exception e){}
		}
		return list;
	}
	
	public ArrayList<BoardDTO> getBoardList(int startRow, int limit, String data) {
		// 게시판의 제한된 범위 만큼의 글을 가져오는 SQL (available != 1 이 아닌 리스트만 가져온다 available이 1이면 삭제된 글)
		String sql = "select * from board where available != 1 and title like ? order by boardId desc limit ?, ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<BoardDTO> list = new ArrayList<>();
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(sql);	
			pstmt.setString(1, "%" + data + "%");
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, limit);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				BoardDTO boardDTO = new BoardDTO();
				boardDTO.setBoardId(rs.getInt("boardId"));  
				boardDTO.setWriterId(rs.getString("writerId"));
				boardDTO.setWriterNick(rs.getString("writerNick"));
				boardDTO.setTitle(rs.getString("title"));
				boardDTO.setContent(rs.getString("content"));
				boardDTO.setRedate(rs.getString("redate"));
				boardDTO.setTarget(rs.getInt("target"));
				list.add(boardDTO);
			}
		} catch (SQLException e) {
			System.out.println("BoardDAO getBoardList(검색) SQLException : " + e.getMessage());
		} finally {
			if(rs != null) try {rs.close();}catch(Exception e) {}
			if(pstmt!=null) try{pstmt.close();}catch(Exception e){}
			if(conn!=null) try{conn.close();}catch(Exception e){}
		}
		return list;
	}
	
	public int getListCount() {
		// 게시판의 전체 글 수를 가져오는 SQL (available != 1 이 아닌 리스트만 가져온다 available이 1이면 삭제된 글)
		String sql = "select count(*) as count from board where available != 1";
		int count = 0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				count = rs.getInt("count");
			}
		} catch (SQLException e) {
			System.out.println("BoardDAO getListCount SQLException : " + e.getMessage());
		} finally {
			if(rs != null) try {rs.close();}catch(Exception e) {}
			if(pstmt!=null) try{pstmt.close();}catch(Exception e){}
			if(conn!=null) try{conn.close();}catch(Exception e){}
		}
		
		return count;
	}
	
	public int getListCount(String title) {
		// 게시판의 전체 글 수를 가져오는 SQL (available != 1 이 아닌 리스트만 가져온다 available이 1이면 삭제된 글)
		String sql = "select count(*) as count from board where available != 1 and title like ?";
		int count = 0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, "%" + title + "%");
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				count = rs.getInt("count");
			}
		} catch (SQLException e) {
			System.out.println("BoardDAO getListCount SQLException : " + e.getMessage());
		} finally {
			if(rs != null) try {rs.close();}catch(Exception e) {}
			if(pstmt!=null) try{pstmt.close();}catch(Exception e){}
			if(conn!=null) try{conn.close();}catch(Exception e){}
		}
		
		return count;
	}
	
	public BoardDTO getBoard(int boardId) {
		String sql = "select * from board where boardId = ?";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BoardDTO boardDTO = new BoardDTO();
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardId);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				boardDTO.setBoardId(rs.getInt("boardId"));
				boardDTO.setTitle(rs.getString("title"));
				boardDTO.setWriterId(rs.getString("WriterId"));
				boardDTO.setWriterNick(rs.getString("WriterNick"));
				boardDTO.setRedate(rs.getString("redate"));
				boardDTO.setTarget(rs.getInt("target"));
				boardDTO.setContent(rs.getString("content"));
			}
		} catch (SQLException e) {
			System.out.println("BoardDAO getBoard SQLException : " + e.getMessage());
		} finally {
			if(rs != null) try {rs.close();}catch(Exception e) {}
			if(pstmt!=null) try{pstmt.close();}catch(Exception e){}
			if(conn!=null) try{conn.close();}catch(Exception e){}
		}
		return boardDTO;
	}
	
	public void incrementTarget(int boardId) {
		String sql = "update board set target = target + 1 where boardId = ?";
		
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardId);
			
			pstmt.execute();
		} catch (SQLException e) {
			System.out.println("BoardDAO incrementTarget SQLException : " + e.getMessage());
		} finally {
			if(pstmt!=null) try{pstmt.close();}catch(Exception e){}
			if(conn!=null) try{conn.close();}catch(Exception e){}
		}
	}
	
	public int removeBoard(int boardId) {
		String sql = "update board set available = 1 where boardId = ?";
		
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardId);
			
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("BoardDAO removeBoard SQLException : " + e.getMessage());
		} finally {
			if(pstmt!=null) try{pstmt.close();}catch(Exception e){}
			if(conn!=null) try{conn.close();}catch(Exception e){}
		}
		
		return -1;
	}
}
