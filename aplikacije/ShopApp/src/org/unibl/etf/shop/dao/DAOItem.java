package org.unibl.etf.shop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.unibl.etf.shop.dto.DTOItem;
import org.unibl.etf.shop.logging.Logger;
import org.unibl.etf.shop.dbconnection.ConnectionPool;

public class DAOItem {

	private static final String GET_ALL_ITEMS = "SELECT * FROM items";

	public static List<DTOItem> listOfArticle() {

		List<DTOItem> list = new ArrayList<DTOItem>();
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			c = ConnectionPool.getInstance().checkOut();
			ps = c.prepareStatement(GET_ALL_ITEMS);
			rs = ps.executeQuery();
			while (rs.next()) {
				list.add(new DTOItem(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(4)));
			}

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
		return list;
	}

}
