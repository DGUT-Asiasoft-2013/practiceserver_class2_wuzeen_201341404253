package com.cloudage.membercenter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cloudage.membercenter.entity.Article;
import com.cloudage.membercenter.entity.Likes;
import com.cloudage.membercenter.entity.Likes.Key;
import com.cloudage.membercenter.entity.User;
import com.cloudage.membercenter.repository.ILisksRepository;

@Component
@Service
@Transactional
public class DefaultLikesService implements ILikesService {
	@Autowired
	ILisksRepository likesRepo;

	@Override
	public Likes save(Likes likes) {
		return likesRepo.save(likes);
	}

	@Override
	public int countLikes(int article_id) {
		return likesRepo.countLikes(article_id);
	}

	@Override
	public boolean checkLiked(int user_id, int article_id) {

		return likesRepo.checkLiked(user_id, article_id) != null;
	}

	@Override
	public Likes addLikes(User me, Article article) {
		
		Likes.Key id=new Likes.Key();
		id.setUser(me);
		id.setArticle(article);
		Likes likes=new Likes();
		likes.setId(id);
		return likesRepo.save(likes);
	}

	@Override
	public void  removeLikes(User me, Article article) {
		Likes.Key key = new Key();
		key.setUser(me);
		key.setArticle(article);
		
		likesRepo.delete(key);
	}
}