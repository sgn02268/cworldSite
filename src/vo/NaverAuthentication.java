package vo;

import javax.mail.*;

public class NaverAuthentication extends Authenticator {
	PasswordAuthentication passAuth;
	
	public NaverAuthentication() {
		passAuth = new PasswordAuthentication("1841pjs", "��й�ȣ");
	}
	public PasswordAuthentication getPasswordAuthentication() {
		return passAuth;
	}
}
