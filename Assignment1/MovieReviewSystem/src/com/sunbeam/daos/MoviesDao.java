package com.sunbeam.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sunbeam.pojos.Movies;
import com.sunbeam.utils.DBUtil;

public class MoviesDao implements MoviesInt, AutoCloseable {
	private Connection con;

	public MoviesDao() throws SQLException{
		con = DBUtil.getConnection();
	}

	public List<Movies> displayAll() throws SQLException{
		List<Movies> list = new ArrayList<>();
		String sql = "SELECT * FROM movies";
		try(PreparedStatement stmt = con.prepareStatement(sql)){
			try(ResultSet rs = stmt.executeQuery()){
				while(rs.next()) {
					int id = rs.getInt("id");
					String name = rs.getString("title");
					java.sql.Date sdate = rs.getDate("rel_date");
					java.util.Date jdate = new java.util.Date(sdate.getTime());
					Movies movie = new Movies(id,name,jdate);
					list.add(movie);
				}
			}
		}
		return list;
	}
	
	public Movies findById(int id) throws SQLException {
		Movies m = new Movies();
		String sql = "SELECT * FROM movies WHERE id = ?";
		try(PreparedStatement stmt = con.prepareStatement(sql)){
			stmt.setInt(1, id);
			try(ResultSet rs = stmt.executeQuery()){
				while(rs.next()) {
					id = rs.getInt("id");
					String name = rs.getString("title");
					java.sql.Date sdate = rs.getDate("rel_date");
					java.util.Date jdate = new java.util.Date(sdate.getTime());
					m = new Movies(id,name,jdate);
				}
			}
		}
		return m;
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
