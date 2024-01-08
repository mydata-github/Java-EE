package com.sunbeam.daos;

import java.util.List;

import com.sunbeam.pojos.Reviews;

public interface ReviewsInt extends AutoCloseable{
	public int insReview(Reviews r) throws Exception;
	public int editReview(String review, int user_id, int id, int rating) throws Exception;
	public List<Reviews> displayAll() throws Exception;
	public List<Reviews> displayMyReviews(int userId) throws Exception;
	public List<Reviews> getSharedWithUser(int userId) throws Exception;
	public Reviews findById(int id) throws Exception;
	public int deleteReview(int rev_id, int user_id) throws Exception;
}
