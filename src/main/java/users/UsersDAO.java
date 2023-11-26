package users;

import java.sql.*;

import util.DatabaseUtil;

public class UsersDAO {
	
	public int join(UsersDTO user) {
		String sql = "insert into users (id, pw, nickname, email, emailHash, emailAuth, type) values"
				+ "(?, ?, ?, ?, ?, ?, ?)";
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
			pstmt.setInt(7, user.type);
			
			return pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("UsersDAO SQL 회원가입 실패 : " +e.getMessage());
		} finally {
			if(pstmt!=null) try{pstmt.close();}catch(Exception e){}
			if(conn!=null) try{conn.close();}catch(Exception e){}
		}
		
		return -1; // 오류 또는 아이디 중복 등으로 인한 회원가입 실패
	}
	
	public int emailAuth(String emailHash) {
		String sql = "update users set emailAuth = 1 where emailHash = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {			
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, emailHash);
			
			return pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("UsersDAO SQL 이메일 인증 실패 : " +e.getMessage());
		} finally {
			if(pstmt!=null) try{pstmt.close();}catch(Exception e){}
			if(conn!=null) try{conn.close();}catch(Exception e){}
		}
		
		return -1; // 오류 또는 메일 인증 실패
	}
	
	public String getID(String emailHash) {
		String sql = "select id from users where emailHash = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String id = null;
		
		try {			
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, emailHash);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				id = rs.getString("id");
			}
		} catch (Exception e) {
			System.out.println("UsersDAO SQL 이메일 인증 실패 : " +e.getMessage());
		} finally {
			if(pstmt!=null) try{pstmt.close();}catch(Exception e){}
			if(conn!=null) try{conn.close();}catch(Exception e){}
		}
		
		return id;
	}
}
