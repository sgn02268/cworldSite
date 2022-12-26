package svc;

import static db.jdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class OrderProcInSvc {
	public String orderInsert(OrderInfo oi, String where) {
		String result = null;
		Connection conn = getConnection();
		OrderDao orderDao = OrderDao.getInstance();
		orderDao.setConnection(conn);

		result = orderDao.orderInsert(oi, where);
		String[] arr = result.split(":");
		int rcount = Integer.parseInt(arr[1]);	// ���� ����� ���ڵ� ����
		int target = Integer.parseInt(arr[2]);	// ����Ǿ���� �� ���ڵ� ����
		if (rcount == target)	commit(conn);
		else					rollback(conn);
		close(conn);

		return result;
	}
	public String orderInsert(OrderInfo oi) {
		String result = null;
		Connection conn = getConnection();
		OrderDao orderDao = OrderDao.getInstance();
		orderDao.setConnection(conn);

		result = orderDao.orderInsert(oi);
		String[] arr = result.split(":");
		int rcount = Integer.parseInt(arr[1]);	// ���� ����� ���ڵ� ����
		int target = Integer.parseInt(arr[2]);	// ����Ǿ���� �� ���ڵ� ����
		if (rcount == target)	commit(conn);
		else					rollback(conn);
		close(conn);

		return result;
	}
}
