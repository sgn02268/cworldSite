package dao;

import static db.jdbcUtil.*;
import java.sql.*;

import vo.*;

public class LoginDao {
// �α��ο� ���õ� ���� �۾��� ó���ϴ� Ŭ����
	private static LoginDao loginDao;	
	private Connection conn;
	private LoginDao() {}
	public static LoginDao getInstance() {
		if (loginDao == null) loginDao = new LoginDao();
		return loginDao;
	}
	public void setConnection(Connection conn) {
		this.conn = conn;
	}
	public MemberInfo getLoginMember(String uid, String pwd) {
	// �޾ƿ� ���̵�� ��ȣ�� �α��� �۾��� ó���� �� ȸ�������� MemberInfo�� �ν��Ͻ��� ��� �����ϴ� �޼ҵ�
		Statement stmt = null; 	
		ResultSet rs = null;
		MemberInfo loginInfo = null; 
		try {
			stmt = conn.createStatement();
			
			String sql = "select * from t_member_info where (mi_status='a' or mi_status='b') and mi_id = '" + uid + "' and mi_pw = '" + pwd + "' ";
			rs = stmt.executeQuery(sql);
			if(rs.next()) {	
				loginInfo = new MemberInfo();	// ȸ�� ������ ������ �ν��Ͻ� ����
				loginInfo.setMi_id(uid);	
				loginInfo.setMi_pw(pwd);
				loginInfo.setMi_name(rs.getString("mi_name"));
				loginInfo.setMi_gender(rs.getString("mi_gender"));
				loginInfo.setMi_birth(rs.getString("mi_birth"));
				loginInfo.setMi_mail(rs.getString("mi_mail"));
				loginInfo.setMi_phone(rs.getString("mi_phone"));
				loginInfo.setMi_point(rs.getInt("mi_point"));
				loginInfo.setMi_join(rs.getString("mi_join"));
				loginInfo.setMi_last(rs.getString("mi_last"));
				loginInfo.setMi_status(rs.getString("mi_status"));
				loginInfo.setMi_visited(rs.getInt("mi_visited"));
				
			}
		} catch(Exception e) {
			System.out.println("LoginDaoŬ������ getLoginMember() �޼ҵ� ����");
			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}	
		return loginInfo;
	}
	
	public int getLoginUpdate(String uid, String pwd) {
		Statement stmt =null;
		int result = 0;
		try {
			String sql = "update t_member_info set mi_visited = mi_visited + 1, mi_last = now() where mi_status='a' and mi_id = '" + uid + "' and mi_pw = '" + pwd + "'";
			stmt = conn.createStatement();
			result = stmt.executeUpdate(sql);		
		} catch(Exception e) {
			System.out.println("LoginDaoŬ������ getLoginUpdate() �޼ҵ� ����");
			e.printStackTrace();
		} finally {
			close(stmt);
		}
		return result;
	}
}
