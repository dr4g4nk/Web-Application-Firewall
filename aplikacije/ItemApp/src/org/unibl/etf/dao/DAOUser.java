package org.unibl.etf.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.unibl.etf.dbconnection.ConnectionPool;
import org.unibl.etf.shop.logging.Logger;

public class DAOUser {

	public static final String GET_ROLE_FROM_USER = "SELECT role FROM users WHERE username=?";

	public static int getRoleFromUser(String username) {
		int role = -1;
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			c = ConnectionPool.getInstance().checkOut();
			ps = c.prepareStatement(GET_ROLE_FROM_USER);
			ps.setString(1, username);
			rs = ps.executeQuery();
			if (rs.next())
				role = rs.getInt(1);
			else
				System.out.println("Nema role");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Logger.log(e.toString(), e);
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					Logger.log(e.toString(), e);
				}
			}

			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					Logger.log(e.toString(), e);
				}
			}
			ConnectionPool.getInstance().checkIn(c);
		}

		return role;

	}
}
