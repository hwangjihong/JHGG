package summoner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import util.APIKey;
import util.CommandHandler;

public class SummonerSearchService implements CommandHandler{

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, SQLException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=UTF-8");
		
		String summoner = null;
		if(request.getParameter("summoner") != null) {
			summoner = request.getParameter("summoner");
		}
		if(summoner == null) {
			PrintWriter script = response.getWriter();
			script.print("<script>");
			script.print("alert('소환사 이름과 태그를 입력하세요.');");
			script.print("history.back();");
			script.print("</script>");
			script.close();
			return null;
		}		
		if(!summoner.contains("#")) {
			PrintWriter script = response.getWriter();
			script.print("<script>");
			script.print("alert('소환사 이름과 태그를 정확하게 입력하세요.');");
			script.print("history.back();");
			script.print("</script>");
			script.close();
			return null;
		}
		String[] strSplit = summoner.split("#");
		String gameName = strSplit[0];
		String tagLine = strSplit[1];
		
		HashMap<String, Object> account = getAccount(gameName, tagLine);
		if(account.size() != 3) {
			return "summonerNotFound";
		}
		
		request.setAttribute("gameName", account.get("gameName").toString());
		request.setAttribute("tagLine", account.get("tagLine").toString());
		
		HashMap<String, Object> accountInfo = getAccountInfo(account.get("puuid").toString());
		if(accountInfo.size() != 3) {
			return "summonerNotFound";
		}
		
		request.setAttribute("profileIconId", accountInfo.get("profileIconId").toString());
		request.setAttribute("summonerLevel", accountInfo.get("summonerLevel").toString());
		
		HashMap<String, Object> entries = getSummonerEntries(accountInfo.get("id").toString());
		if(entries.size() != 5) {
			return "summonerNotFound";
		}
		String firstLetter = entries.get("tier").toString().substring(0, 1).toUpperCase();
		String remainLetter = entries.get("tier").toString().substring(1).toLowerCase();
		String tier = firstLetter + remainLetter;
		
		request.setAttribute("tier", tier);
		request.setAttribute("rank", " " + entries.get("rank").toString());
		request.setAttribute("leaguePoints", entries.get("leaguePoints").toString() + " ");
		request.setAttribute("wins", entries.get("wins").toString());
		request.setAttribute("losses", " " + entries.get("losses").toString());
		
		Double win = Double.parseDouble(entries.get("wins").toString());
		Double lose = Double.parseDouble(entries.get("losses").toString());
		Double ratio = (win / (win+lose)) * 100;
	
		request.setAttribute("ratio", Math.round(ratio));
		
		return "summoner";
	}
	
	private HashMap<String, Object> getAccount(String summonerName, String summonerTag) {
		HashMap<String, Object> account = new HashMap<String, Object>();
		BufferedReader br = null;
		try {
			String requestURL = "https://asia.api.riotgames.com/riot/account/v1/accounts/by-riot-id";
			requestURL += "/" + summonerName;
			requestURL += "/" + summonerTag;
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
			account.put("puuid", jo.get("puuid"));
			account.put("gameName", jo.get("gameName"));
			account.put("tagLine", jo.get("tagLine"));
		} catch (Exception e) {
			System.out.println("getPUUID JSON 에러 : " + e.getMessage());
		}
		
		return account;
	}
	
	private HashMap<String, Object> getAccountInfo(String puuid) {
		HashMap<String, Object> account = new HashMap<String, Object>();
		BufferedReader br = null;
		try {
			String requestURL = "https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-puuid";
			requestURL += "/" + puuid;
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
			account.put("id", jo.get("id").toString());
			account.put("profileIconId", jo.get("profileIconId"));
			account.put("summonerLevel", jo.get("summonerLevel"));
		} catch (Exception e) {
			System.out.println("getPUUID JSON 에러 : " + e.getMessage());
		}
		
		return account;
	} 
	
	private HashMap<String, Object> getSummonerEntries(String id) {
		HashMap<String, Object> entries = new HashMap<String, Object>();
		BufferedReader br = null;
		try {
			String requestURL = "https://kr.api.riotgames.com/lol/league/v4/entries/by-summoner";
			requestURL += "/" + id;
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
			JSONArray jo = (JSONArray) jp.parse(result);
			JSONObject childobj = (JSONObject)jo.get(1);
			entries.put("tier", childobj.get("tier"));
			entries.put("rank", childobj.get("rank"));
			entries.put("leaguePoints", childobj.get("leaguePoints"));
			entries.put("wins", childobj.get("wins"));
			entries.put("losses", childobj.get("losses"));
		} catch (Exception e) {
			System.out.println("getPUUID JSON 에러 : " + e.getMessage());
		}
		
		return entries;
	}
}
