package com.sunbeam.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sunbeam.pojos.Users;
import com.sunbeam.utils.DBUtil;
import com.sunbeam.utils.DateUtil;

public class UsersDao implements UserInt,AutoCloseable{
	private Connection con;
	
	public UsersDao() throws SQLException{
		con = DBUtil.getConnection();
	}
	@Override
	public int signUp(Users user) throws SQLException{
		String sql= "INSERT INTO users VALUES(default, ?, ?, ?, ?, ?, ?)";
		try(PreparedStatement stmt = con.prepareStatement(sql)){
			stmt.setString(1, user.getFirstName());
			stmt.setString(2, user.getLastName());
			stmt.setString(3, user.getEmail());
			stmt.setString(4, user.getMobile());
			stmt.setString(6, user.getPasswd());
			java.sql.Date sdate = DateUtil.utilToSqlDate(user.getBirth());
			stmt.setDate(5, sdate);
			
			int cnt = stmt.executeUpdate();
			return cnt;
		}
	}
	@Override
	public Users signin(String email) throws SQLException{
		Users us = new Users();
		String sql = "SELECT * FROM users where email = ?";
		try(PreparedStatement stmt = con.prepareStatement(sql)){
			stmt.setString(1, email);
			try(ResultSet rs = stmt.executeQuery()){
				while(rs.next()) {
				int id = rs.getInt("id");
				String fname = rs.getString("first_name");
				String lname = rs.getString("last_name");
				String email1 = rs.getString("email");
				String passwd = rs.getString("password");
				String mobile = rs.getString("mobile");
				java.sql.Date sdate = rs.getDate("birth");
				java.util.Date jdate = new java.util.Date( sdate.getTime());
				us = new Users(id, fname, lname, email1, passwd, mobile, jdate);
				}
			}
		}
		return us;
	}
	
	@Override
	public int editInfo(Users user) throws SQLException{
		int cnt = 0;
		String sql = "UPDATE users SET first_name = ?, last_name = ?, mobile = ?, birth = ? WHERE id = ?";
		try(PreparedStatement stmt = con.prepareStatement(sql)){
			stmt.setString(1, user.getFirstName());
			stmt.setString(2, user.getLastName());
			stmt.setString(3, user.getMobile());
			java.sql.Date sdate = new java.sql.Date(user.getBirth().getTime());
			stmt.setDate(4, sdate);
			stmt.setInt(5, user.getId());
			
			cnt = stmt.executeUpdate();
		}
		return cnt;
	}
	
	@Override
	public int changePasswd(Users user) throws SQLException{
		int cnt = 0;
		String sql = "UPDATE users SET email = ?, password = ? WHERE id = ?";
		try(PreparedStatement stmt = con.prepareStatement(sql)){
			stmt.setString(1,user.getEmail());
			stmt.setString(2,user.getPasswd());
			stmt.setInt(3, user.getId());
			
			cnt = stmt.executeUpdate();
		}
		return cnt;
	}
	@Override
	public List<Users> displayAll() throws SQLException{
		List<Users> list = new ArrayList<>();
		String sql = "SELECT * FROM users";
		try(PreparedStatement stmt = con.prepareStatement(sql)){
			try(ResultSet rs = stmt.executeQuery()){
				while(rs.next()) {
					int id = rs.getInt("id");
					String fname = rs.getString("first_name");
					String lname = rs.getString("last_name");		
					Users user = new Users(id,fname, lname,"","", "",new java.util.Date());
					list.add(user);
				}
			}
		}
		return list;
	}
	
	@Override
	public void close() throws Exception {
		try {
			if(con != null)
				con.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
