package dao;


import static db.jdbcUtil.*;
import java.util.*;
import java.sql.*;
import vo.*;


public class GQnaProcInDao {

	private static GQnaProcInDao gQnaProcInDao;
	private Connection conn;
	private GQnaProcInDao() {}
	
	public static GQnaProcInDao getInstance() {
		if (gQnaProcInDao == null)	gQnaProcInDao = new GQnaProcInDao();
		return gQnaProcInDao;
	}
	
	public void setConnection(Connection conn) {
		this.conn = conn;
	}
	
	public int GqnaListInsert(GqnaInfo gqnaInfo) {
	// ���� �Խ��� �Խñ� ��� ó�� �޼ҵ�(�۹�ȣ�� ����)
		Statement stmt = null;
		ResultSet rs = null;
		int result = 0, idx = 1;
		
		try {
			stmt = conn.createStatement();
			String sql = "select max(gq_idx) from t_group_qna";
			rs = stmt.executeQuery(sql);
			if(rs.next())	idx = rs.getInt(1) + 1;
			
			sql = "insert into t_group_qna (mi_id, gq_idx, gq_gname, gq_title, gq_content, gq_phone, gq_ef, gq_ip) values('" + gqnaInfo.getMi_id() + "', " + idx + ", '" + 
				gqnaInfo.getGq_gname()	+	"', '"	+	gqnaInfo.getGq_title()	+	"', '" +
				gqnaInfo.getGq_content()	+	"', '"	+	gqnaInfo.getGq_phone()	+	"', '" +
				gqnaInfo.getGq_ef()	+	"', '"	+	gqnaInfo.getGq_ip()	+	"');";
				result = stmt.executeUpdate(sql);
				if (result == 0)		return result;
				else					result = idx;
			
		} catch(Exception e) {
			System.out.println("GQnaProcInDao Ŭ������ GqnaListInsert() �޼ҵ� ����");
			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}
		return result;
	}
}
