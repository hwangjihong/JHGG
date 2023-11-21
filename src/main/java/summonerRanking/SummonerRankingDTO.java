package summonerRanking;

public class SummonerRankingDTO {
	String summonerName;
	int leaguePoints;
	int wins;
	int losses;
	public String getSummonerName() {
		return summonerName;
	}
	public void setSummonerName(String summonerName) {
		this.summonerName = summonerName;
	}
	public int getLeaguePoints() {
		return leaguePoints;
	}
	public void setLeaguePoints(int leaguePoints) {
		this.leaguePoints = leaguePoints;
	}
	public int getWins() {
		return wins;
	}
	public void setWins(int wins) {
		this.wins = wins;
	}
	public int getLosses() {
		return losses;
	}
	public void setLosses(int losses) {
		this.losses = losses;
	}
}
