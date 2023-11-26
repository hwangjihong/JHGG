package users;

public class UsersDTO {
	String id;
	String password;
	String nickname;
	String email;
	String emailHash;
	int emailAuth;
	int type;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEmailHash() {
		return emailHash;
	}
	public void setEmailHash(String emailHash) {
		this.emailHash = emailHash;
	}
	public int getEmailAuth() {
		return emailAuth;
	}
	public void setEmailAuth(int emailAuth) {
		this.emailAuth = emailAuth;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
}
