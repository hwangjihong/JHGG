package summonerRanking;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import util.APIKey;
import util.CommandHandler;


public class SummonerRankingService implements CommandHandler {

	SummonerRankingDAO srDAO;
	
	public SummonerRankingService() {
		srDAO = new SummonerRankingDAO();
	}
	
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, SQLException {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String cTime = sdf.format(System.currentTimeMillis());
		Date uTime = srDAO.selSummonerRankingUpdateTime();
		
		// 시간 다르면 업데이트 (하루에 한 번만)
		if(!cTime.equals(uTime.toString())) {
			BufferedReader br = null;
			try {
				String requestURL = "https://kr.api.riotgames.com/lol/league/v4/challengerleagues/by-queue/RANKED_SOLO_5x5";
				requestURL += "?api_key=" + APIKey.Key;
				URL url = new URL(requestURL);
				HttpURLConnection uconn = (HttpURLConnection)url.openConnection();
				uconn.setRequestMethod("GET");
				
				br = new BufferedReader(new InputStreamReader(uconn.getInputStream(), "UTF-8"));
				
				String result = "";
				String line;
				
				while((line = br.readLine()) != null) {
					result = result + line;
				}
				
				JSONParser jp = new JSONParser();
				JSONObject jo = (JSONObject) jp.parse(result);
				JSONArray entries = (JSONArray) jo.get("entries");
				ArrayList<SummonerRankingDTO> list = new ArrayList<>();
				for(Object obj : entries) {
					JSONObject childobj = (JSONObject)obj;
					SummonerRankingDTO sr = new SummonerRankingDTO();
					sr.summonerName = childobj.get("summonerName").toString();
					sr.leaguePoints = Integer.parseInt(String.valueOf(childobj.get("leaguePoints")));
					sr.wins = Integer.parseInt(String.valueOf(childobj.get("wins")));
					sr.losses = Integer.parseInt(String.valueOf(childobj.get("losses")));
									
					list.add(sr);
				}
				srDAO.SummonerRankingUpdate(list);
				srDAO.upSummonerRankingUpdateTime(cTime);
			} catch (Exception e) {
				System.out.println("RankingService JSON 에러 : " + e.getMessage());
			}
		}
		
		
		int page = 1;
		int limit = 50;
		int listCount = srDAO.getListCount();
		
		if(request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
		}
		
		int startRank = (page - 1) * limit;
		
		int startRow = (page - 1) * limit;
		int pageCount = (int) Math.ceil((double) listCount / limit);
		System.out.println("page :" + page + ", limit : " + limit + ", listCount : " + listCount + ", startRow : " + startRow +
				", pageCount : " + pageCount);
		ArrayList<SummonerRankingDTO> list = srDAO.selSummonerRanking(startRow, limit);
		request.setAttribute("list", list);
		request.setAttribute("startRank", startRank);
		request.setAttribute("page", page);
		request.setAttribute("pageCount", pageCount);
		return "summonerRanking";
	}
	
}
