package com.sunbeam.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sunbeam.pojos.Reviews;
import com.sunbeam.utils.DBUtil;

public class ReviewsDao implements ReviewsInt, AutoCloseable{
	private Connection con;
	
	public ReviewsDao() throws SQLException{
		con = DBUtil.getConnection();
	}
	
	@Override
	public int insReview(Reviews rev) throws SQLException{
		String sql = "INSERT INTO reviews VALUES(default, ?, ?, ?, ?, ?)";
		try(PreparedStatement stmt = con.prepareStatement(sql)){
			stmt.setInt(1, rev.getMovie_id());
			stmt.setString(2,rev.getReview());
			stmt.setInt(3, rev.getRating());
		    stmt.setInt(4, rev.getUser_id());
		    java.sql.Timestamp sq = new java.sql.Timestamp(rev.getModified().getTime()); 
		    stmt.setTimestamp(5, sq);
		    
			int cnt = stmt.executeUpdate();
			return cnt;
		}
	}
	@Override
	public List<Reviews> displayAll() throws SQLException{
		List<Reviews> list = new ArrayList<>();
		String sql = "SELECT * FROM reviews";
		try(PreparedStatement stmt = con.prepareStatement(sql)){
			try(ResultSet rs = stmt.executeQuery()){
				while(rs.next()) {
					int id = rs.getInt("id");
					int movie_id = rs.getInt("movie_id");
					String review = rs.getString("review");
					int rating = rs.getInt("rating");
					int users_id = rs.getInt("user_id");
					java.sql.Timestamp sdate = rs.getTimestamp("modified");
					java.util.Date jdate = new java.util.Date(sdate.getTime());
					Reviews rev = new Reviews(id,movie_id, review,rating,users_id,jdate);
					list.add(rev);
				}
			}
		}
		return list;
	}
	
	@Override
	public Reviews findById(int id) throws SQLException{
		Reviews rev = null;
		String sql = "SELECT * FROM reviews where id = ?";
		try(PreparedStatement stmt = con.prepareStatement(sql)){
			stmt.setInt(1, id);
			try(ResultSet rs = stmt.executeQuery()){
				while(rs.next()) {
					id = rs.getInt("id");
					int movie_id = rs.getInt("movie_id");
					String review = rs.getString("review");
					int rating = rs.getInt("rating");
					int users_id = rs.getInt("user_id");
					java.sql.Timestamp sdate = rs.getTimestamp("modified");
					java.util.Date jdate = new java.util.Date(sdate.getTime());
					rev = new Reviews(id,movie_id, review,rating,users_id,jdate);
					
				}
			}
		}
		return rev;
	}
	
	@Override
	public List<Reviews> displayMyReviews(int user_id) throws SQLException{
		List<Reviews> list = new ArrayList<>();
		String sql = "SELECT * FROM reviews WHERE user_id = ?";
		try(PreparedStatement stmt = con.prepareStatement(sql)){
			stmt.setInt(1, user_id);
			try(ResultSet rs = stmt.executeQuery()){
				while(rs.next()) {
					int id = rs.getInt("id");
					int movie_id = rs.getInt("movie_id");
					String review = rs.getString("review");
					int rating = rs.getInt("rating");
					int users_id = rs.getInt("user_id");
					java.sql.Timestamp sdate = rs.getTimestamp("modified");
					java.util.Date jdate = new java.util.Date(sdate.getTime());
					Reviews rev = new Reviews(id,movie_id, review,rating,users_id,jdate);
					list.add(rev);
				}
			}
		}
		return list;
	}
	@Override
	public int editReview(String review, int user_id, int id, int rating) throws SQLException{
		int cnt = 0;
		String sql = "UPDATE reviews SET review = ?, rating = ? where user_id = ? and id = ?";
		try(PreparedStatement stmt = con.prepareStatement(sql)){
			stmt.setString(1, review);
			stmt.setInt(2, rating);
			stmt.setInt(3, user_id);
			stmt.setInt(4, id);
			
			cnt = stmt.executeUpdate();
		}
		return cnt;
	}
	
	@Override
	public int deleteReview(int rev_id, int user_id) throws SQLException{
		String sql = "DELETE FROM reviews where id = ? and user_id = ?";
		try(PreparedStatement stmt = con.prepareStatement(sql)){
			stmt.setInt(1, rev_id);
			stmt.setInt(2, user_id);
			int cnt = stmt.executeUpdate();
			return cnt;
		}
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

	@Override
	public List<Reviews> getSharedWithUser(int userId) throws Exception {
		List<Reviews> list = new ArrayList<>();
		String sql = "select reviews.id, reviews.movie_id, reviews.review, reviews.rating from reviews, shares where reviews.id = shares.review_id and shares.user_id = ?";
		try(PreparedStatement stmt = con.prepareStatement(sql)){
			stmt.setInt(1, userId);
			try(ResultSet rs = stmt.executeQuery()){
				while(rs.next()) {
					int id = rs.getInt("id");
					int movie_id = rs.getInt("movie_id");
					String review = rs.getString("review");
					int rating = rs.getInt("rating");
					java.util.Date jdate = new java.util.Date();
					Reviews rev = new Reviews(id,movie_id, review,rating,0,jdate);
					list.add(rev);
				}
			}
		}
		return list;
		
	}
}
