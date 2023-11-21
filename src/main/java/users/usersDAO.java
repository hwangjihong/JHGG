package users;

import java.sql.*;

import util.DatabaseUtil;

public class usersDAO {
	
	public void register(String id, String pw, String name, String nickname) {
		String sql = "insert into users (id, pw, name, nickname, type) values"
				+ "(?, ?, ?, ?, 0)";
		try {
			Connection conn = DatabaseUtil.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			pstmt.setString(3, name);
			pstmt.setString(4, nickname);
			pstmt.execute();
		} catch (Exception e) {
			System.out.println("SQL 회원가입 실패 : " +e.getMessage());
		}
	}
}
