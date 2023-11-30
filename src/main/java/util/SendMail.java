package util;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMail {
	
	public static void sendMail(String email, String id) {
		// 메일 전송
		String host = "http://localhost:8181/JHGG/";
		String from = "jhggproject@gmail.com";
		String to = email;
		String code = SHA256.getSHA256(to);
		String subject = "JHGG 메일 인증";
		String content = "다음 링크에 접속하여 이메일 인증을 진행해주세요." + 
			"<a href='" + host + "emailCheck.do?code=" + code + "&&id=" + id +"'>이메일 인증하기</a>";
		
		Properties p = new Properties();
		p.put("mail.smtp.user", from);
		p.put("mail.smtp.host", "smtp.googlemail.com"); // google SMTP 주소
		p.put("mail.smtp.port", "465");
		p.put("mail.smtp.starttls.enable", "true");
		p.put("mail.smtp.auth", "true");
		p.put("mail.smtp.debug", "true");
		p.put("mail.smtp.socketFactory.port", "465");
		p.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		p.put("mail.smtp.socketFactory.fallback", "false");
		p.put("mail.smtp.ssl.protocols", "TLSv1.2");
		p.put("mail.smtp.ssl.enable", "true");
		
		try {
			Authenticator auth = new Mail();
			Session ses = Session.getInstance(p, auth);
		    ses.setDebug(true);
		    MimeMessage msg = new MimeMessage(ses); 
		    msg.setSubject(subject); // 메일 제목
		    Address fromAddr = new InternetAddress(from); // 보내는 사람 정보
		    msg.setFrom(fromAddr);
		    Address toAddr = new InternetAddress(to); // 받는 사람 정보
		    msg.addRecipient(Message.RecipientType.TO, toAddr);
		    msg.setContent(content, "text/html;charset=UTF-8");
		    Transport.send(msg); // 메세지 전송
		}catch (Exception e) {
			System.out.println("SendMail 에러 : " + e.getMessage());
		}
	}
}
