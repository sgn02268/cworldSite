package dao;

import static db.jdbcUtil.*;
import java.util.*;
import java.sql.*;
import vo.*;

public class PdtJjimDao {
	private static PdtJjimDao pdtJjimDao;	
	private Connection conn;
	private PdtJjimDao() {}
	public static PdtJjimDao getInstance() {
		if (pdtJjimDao == null) {
			pdtJjimDao = new PdtJjimDao();
		}
		return pdtJjimDao;
	}
	public void setConnection(Connection conn) {
		this.conn = conn;
	}
	public int jjimInsert(String miid, String piid) {
		int result = 0;
		Statement stmt = null;
		
		try {
			String sql = "insert into t_member_jjim values ('" + piid + "', '" + miid + "', now())";
			stmt = conn.createStatement();
			result = stmt.executeUpdate(sql);
		} catch(Exception e) {
			System.out.println("PdtJjimDaoŬ������ jjimInsert() �޼ҵ� ����");
			e.printStackTrace();
		} finally {
			close(stmt);
		}

		return result;
	}
	
}
