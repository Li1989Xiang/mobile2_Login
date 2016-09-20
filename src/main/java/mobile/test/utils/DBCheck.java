package mobile.test.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.sapium.assertutil.AssertUtil;

public class DBCheck {
	private static final String ADDR = "jdbc:sqlserver://10.101.237.208:1433;DatabaseName=testcase";
	private static final String USERNAME = "sa";
	private static final String PASSWORD = "1111111";

	private static final String SQL = "select operway, stkcode, stkname, orderprice, orderqty from [JD2A].[run].[dbo].[orderrec] where fundid = ? and ordersno = ?";

	private Connection conn;

	private DBCheck() {
		getConnection();
	}

	public static DBCheck getInstance() {
		return InstanceHolder.ins;
	}

	public void checkOrder(String orderNo, String stkName, String stkCode, String price, String number) {
		if (conn == null) {
			//throw new RuntimeException("conntection is null");
			return;
		}
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, "80316041");
			pstmt.setString(2, orderNo);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				String type = rs.getString("operway");
				String name = rs.getString("stkname").trim();
				String code = rs.getString("stkcode").trim();
				String num = rs.getString("orderqty");
				String pric = rs.getString("orderprice");
				
				AssertUtil.assertEquals("8", type);
				AssertUtil.assertEquals(stkName, name);
				AssertUtil.assertEquals(stkCode, code);
				AssertUtil.assertEquals(number, num);
				AssertUtil.assertEquals(String.format("%.3f", Float.valueOf(price)), pric);
			} else {
				throw new RuntimeException("DBCheck: 无法找到订单号为" + orderNo + "的记录");
			}
			
		} catch (SQLException e) {
			throw new RuntimeException("execute query failed.", e);
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
			} catch (SQLException e) {
				throw new RuntimeException("close statement failed.", e);
			}
		}
	}

	public void close() {
		if (conn != null) {
			try {
				conn.close();
				conn = null;
				System.out.println("close database connection");
			} catch (SQLException e) {
				throw new RuntimeException("close database failed.", e);
			}
		}
	}

	private void getConnection() {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			conn = DriverManager.getConnection(ADDR, USERNAME, PASSWORD);
			System.out.println("connect database");
		} catch (Exception e) {
			//throw new RuntimeException("connect database failed.", e);
			System.err.println("WARN: can't connect database, DBCheck will not work");
		}
	}

	private static class InstanceHolder {
		private static DBCheck ins = new DBCheck();
	}
}
