package util;

import java.security.MessageDigest;

public class SHA256 {
	
	public static String getSHA256(String input) {
		// 메일을 SHA256 암호화
		StringBuffer result = new StringBuffer();
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] salt = "Hello! This is Salt.".getBytes();
			digest.reset();
			digest.update(salt);
			byte[] chars = digest.digest(input.getBytes("UTF-8"));
			for(int i = 0; i < chars.length; i++) {
				String hex = Integer.toHexString(0xff & chars[i]);
				if(hex.length() == 1) result.append("0");
				result.append(hex);
			}
			
		} catch (Exception e) {
			System.out.println("SHA256 변환 에러 : " + e.getMessage());
		}
		
		return result.toString();
	}
}
