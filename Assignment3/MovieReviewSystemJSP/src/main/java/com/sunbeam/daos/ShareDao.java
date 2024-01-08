package com.sunbeam.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sunbeam.pojos.Shares;
import com.sunbeam.utils.DBUtil;

public class ShareDao implements AutoCloseable {
	private Connection con;

	public ShareDao() throws SQLException {
		con = DBUtil.getConnection();
	}

	public List<Shares> display(int id) throws SQLException {
		List<Shares> list = new ArrayList<Shares>();
		String sql = "SELECT * FROM shares where user_id = ?";
		try(PreparedStatement stmt = con.prepareStatement(sql)){
			stmt.setInt(1, id);
			try(ResultSet rs = stmt.executeQuery()) {
				while(rs.next()) {
					int u_id = rs.getInt("user_id");
					int r_id = rs.getInt("review_id");
					
					Shares s = new Shares(r_id,u_id);
					list.add(s);
				}
			}
		} return list;
	}
	
	public int share(int rid, int user_id) throws SQLException {
		String sql = "INSERT INTO shares VALUES(?,?)";
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setInt(1, rid);
			stmt.setInt(2, user_id);

			int cnt = stmt.executeUpdate();
			return cnt;
		}
	}

	public void add(int rid, int uid) throws Exception {
		String sql = "insert into shares values(?,?)";
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setInt(1, rid);
			stmt.setInt(2, uid);
			stmt.executeUpdate();
		}
	}

	// deleting shared reviews
	public void delete(int rid) throws Exception {
		String sql = "delete from shares where review_id = ?";
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setInt(1, rid);

			stmt.executeUpdate();
		}
	}

	@Override
	public void close() throws Exception {
		try {
			if (con != null)
				con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
