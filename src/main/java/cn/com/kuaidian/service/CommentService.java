package cn.com.kuaidian.service;

import org.gelivable.dao.GeliDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.kuaidian.entity.Comment;

@Service
public class CommentService {
	@Autowired
	private GeliDao geliDao;
	
	public Comment getCommentById(long commentId){
		return geliDao.find(Comment.class, commentId);
	}
}
