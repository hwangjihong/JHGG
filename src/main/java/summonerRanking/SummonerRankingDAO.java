package summonerRanking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import util.DatabaseUtil;

public class SummonerRankingDAO {	
	
	@SuppressWarnings("resource")
	public void SummonerRankingUpdate(ArrayList<SummonerRankingDTO> list) {
		String sql = "delete from summonerRanking";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.execute();
			
			sql = "insert into summonerRanking values(?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			int count = 0;
			for (SummonerRankingDTO sr : list) {
				++count;
				
				pstmt.setString(1, sr.getSummonerName());
				pstmt.setInt(2, sr.getLeaguePoints());
				pstmt.setInt(3, sr.getWins());
				pstmt.setInt(4, sr.getLosses());
				
				pstmt.addBatch();
				pstmt.clearParameters();
				
				if(count % 100 == 0) {
					pstmt.executeBatch();
					pstmt.clearBatch();
				}
			}

			pstmt.executeBatch();

		} catch (SQLException e) {
			System.out.println("SummonerRankingUpdate SQLException : " + e.getMessage());
		} finally {
			if(pstmt!=null) try{pstmt.close();}catch(Exception e){}
			if(conn!=null) try{conn.close();}catch(Exception e){}
		}
	}
	
	public ArrayList<SummonerRankingDTO> getSummonerRanking(int startRow, int limit) {
		String sql = "select * from summonerRanking order by leaguePoints desc limit ?, ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<SummonerRankingDTO> list = new ArrayList<>();
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(sql);		
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, limit);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				SummonerRankingDTO srDTO = new SummonerRankingDTO();
				srDTO.summonerName = rs.getString("summonerName");
				srDTO.leaguePoints = rs.getInt("leaguePoints");
				srDTO.wins = rs.getInt("wins");
				srDTO.losses = rs.getInt("losses");
				list.add(srDTO);
			}
		} catch (SQLException e) {
			System.out.println("selSummonerRanking SQLException : " + e.getMessage());
		} finally {
			if(rs != null) try {rs.close();}catch(Exception e) {}
			if(pstmt!=null) try{pstmt.close();}catch(Exception e){}
			if(conn!=null) try{conn.close();}catch(Exception e){}
		}
		return list;
	}
	
	public Date getSummonerRankingUpdateTime() {
		String sql = "select * from summonerRankingUpdateTime";	
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		Date date = null;
		
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();			
			while(rs.next())
				date = rs.getDate("time");
		} catch (SQLException e) {
			System.out.println("selSummonerRankingUpdateTime SQLException : " + e.getMessage());
		} finally {
			if(rs != null) try {rs.close();}catch(Exception e) {}
			if(pstmt!=null) try{pstmt.close();}catch(Exception e){}
			if(conn!=null) try{conn.close();}catch(Exception e){}
		}
		return date;
	}
	
	public void setSummonerRankingUpdateTime(String time) {
		String sql = "update summonerRankingUpdateTime set time = ?";	
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, time);
			pstmt.executeUpdate();			

		} catch (SQLException e) {
			System.out.println("upSummonerRankingUpdateTime SQLException : " + e.getMessage());
		} finally {
			if(pstmt!=null) try{pstmt.close();}catch(Exception e){}
			if(conn!=null) try{conn.close();}catch(Exception e){}
		}
	}
	
	public int getListCount() {
		String sql = "select count(*) as count from summonerRanking";
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
			System.out.println("getListCount SQLException : " + e.getMessage());
		} finally {
			if(rs != null) try {rs.close();}catch(Exception e) {}
			if(pstmt!=null) try{pstmt.close();}catch(Exception e){}
			if(conn!=null) try{conn.close();}catch(Exception e){}
		}
		
		return count;
	}
}
