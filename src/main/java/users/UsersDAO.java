package users;

import java.sql.*;

import util.DatabaseUtil;

public class UsersDAO {
	
	public int join(UsersDTO user) {
		// 회원가입 SQL
		String sql = "insert into users (id, pw, nickname, email, emailHash, emailAuth) values"
				+ "(?, md5(?), ?, ?, ?, ?)";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {		
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, user.id);
			pstmt.setString(2, user.password);
			pstmt.setString(3, user.nickname);
			pstmt.setString(4, user.email);
			pstmt.setString(5, user.emailHash);
			pstmt.setInt(6, user.emailAuth);
			
			return pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("UsersDAO SQL 회원가입 실패 : " +e.getMessage());
		} finally {
			if(pstmt!=null) try{pstmt.close();}catch(Exception e){}
			if(conn!=null) try{conn.close();}catch(Exception e){}
		}
		
		return -1; // 오류 또는 아이디 중복 등으로 인한 회원가입 실패
	}
	
	public int emailAuth(String id, String emailHash) {
		// 이메일 인증 SQL
		String sql = "update users set emailAuth = 1 where id = ? and emailHash = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {			
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, id);
			pstmt.setString(2, emailHash);
			
			return pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("UsersDAO SQL emailAuth 이메일 인증 실패 : " +e.getMessage());
		} finally {
			if(pstmt!=null) try{pstmt.close();}catch(Exception e){}
			if(conn!=null) try{conn.close();}catch(Exception e){}
		}
		
		return -1; // 오류 또는 메일 인증 실패
	}
	
	public int emailAuthCheck(String id, String emailHash) {
		// 이메일 인증이 됐는지 확인하는 SQL
		String sql = "select emailAuth from users where id = ? and emailHash = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;
		
		try {			
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, id);
			pstmt.setString(2, emailHash);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) { result = rs.getInt("emailAuth"); }
			
		} catch (Exception e) {
			System.out.println("UsersDAO emailAuthCheck SQL 이메일 인증 체크 : " +e.getMessage());
		} finally {
			if(rs != null) try {rs.close();}catch(Exception e) {}
			if(pstmt!=null) try{pstmt.close();}catch(Exception e){}
			if(conn!=null) try{conn.close();}catch(Exception e){}
		}
		
		return result; // 이메일 인증이 된 아이디면 1 아니면 0
	}
	
	public int emailAuthCheck(String id) {
		// 이메일 인증이 됐는지 확인하는 SQL
		String sql = "select emailAuth from users where id = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;
		
		try {			
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) { result = rs.getInt("emailAuth"); }
			
		} catch (Exception e) {
			System.out.println("UsersDAO emailAuthCheck SQL 이메일 인증 체크 : " +e.getMessage());
		} finally {
			if(rs != null) try {rs.close();}catch(Exception e) {}
			if(pstmt!=null) try{pstmt.close();}catch(Exception e){}
			if(conn!=null) try{conn.close();}catch(Exception e){}
		}
		
		return result; // 이메일 인증이 된 아이디면 1 아니면 0
	}
	
	public String getEmail(String id) {
		// id 정보로 email 정보를 가져오는 SQL
		String sql = "select email from users where id = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String email = null;
		
		try {			
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) { email = rs.getString("email"); }
			
		} catch (Exception e) {
			System.out.println("UsersDAO getEmail SQL : " +e.getMessage());
		} finally {
			if(rs != null) try {rs.close();}catch(Exception e) {}
			if(pstmt!=null) try{pstmt.close();}catch(Exception e){}
			if(conn!=null) try{conn.close();}catch(Exception e){}
		}
		
		return email; // 이메일 인증이 된 아이디면 1 아니면 0
	}
	
	@SuppressWarnings("resource")
	public int login(String id, String pw) {
		// 로그인 SQL
		String sql = "select count(*) as count from users where id = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {if(rs.getInt("count") == 0) return 0;} // id 없음
			
			sql = "select count(*) as count from users where id = ? and pw = md5(?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				if(rs.getInt("count") == 0) {
					return 1; // 비밀번호 틀림
				} else {	
					return 2; // 로그인 성공
				}
			}
			
			
		} catch (Exception e) {
			System.out.println("UsersDAO login SQL : " +e.getMessage());
		} finally {
			if(rs != null) try {rs.close();}catch(Exception e) {}
			if(pstmt!=null) try{pstmt.close();}catch(Exception e){}
			if(conn!=null) try{conn.close();}catch(Exception e){}
		}
		
		return -1; // 로그인 실패
	}
	
	public String getNickName(String id) {
		// 유저 닉네임 정보를 가져오는 SQL
		String sql = "select nickname from users where id = ?";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {			
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) { return rs.getString("nickname"); }
			
		} catch (Exception e) {
			System.out.println("UsersDAO getNickName SQL : " +e.getMessage());
		} finally {
			if(rs != null) try {rs.close();}catch(Exception e) {}
			if(pstmt!=null) try{pstmt.close();}catch(Exception e){}
			if(conn!=null) try{conn.close();}catch(Exception e){}
		}
		
		return "";
	}
}
