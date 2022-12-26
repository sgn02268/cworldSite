package svc;
import static db.jdbcUtil.*;
import java.sql.*;
import dao.*;
import vo.*;

public class LoginSvc {
// �α��ο� �ʿ��� ���̵�� ��ȣ�� �޾ƿ� ����Ͻ� ������ ó��(DB�۾� ����)�ϴ� Ŭ����
	String uid;
	public MemberInfo getLoginMember(String uid, String pwd){
		Connection conn = getConnection();
		LoginDao loginDao = LoginDao.getInstance();
		loginDao.setConnection(conn);
		
		MemberInfo loginInfo = loginDao.getLoginMember(uid, pwd);
		close(conn);
		
		return loginInfo;
	}
	public int getLoginUpdate(String uid, String pwd){
		Connection conn = getConnection();
		LoginDao loginDao = LoginDao.getInstance();
		loginDao.setConnection(conn);
		int result = 0;
		result = loginDao.getLoginUpdate(uid, pwd);
		
		if(result == 1) {
			System.out.println("�α��� �� �����α������� �� ����Ƚ�� ������Ʈ ����!");
		} else {
			System.out.println("�α��� �� �����α������� �� ����Ƚ�� ������Ʈ ����!");
		}
		
		if (result == 1) 	commit(conn);
		else				rollback(conn);
		close(conn);
		
		return result;
	}
}
