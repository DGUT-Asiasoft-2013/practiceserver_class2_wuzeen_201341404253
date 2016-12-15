package com.cloudage.membercenter.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.cloudage.membercenter.entity.Article;
import com.cloudage.membercenter.entity.Comment;
import com.cloudage.membercenter.entity.User;

public interface ICommentService {
	Page<Comment> findAllByArticleId(int articleId,int page);
	Comment save(Comment comment);
	Page<Comment> findMyComment(Integer id, int page);
	Page<Comment> findCommentToMe(Integer id, int page);


}