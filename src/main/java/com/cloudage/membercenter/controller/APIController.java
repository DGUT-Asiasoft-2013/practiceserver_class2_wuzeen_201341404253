package com.cloudage.membercenter.controller;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cloudage.membercenter.entity.Article;
import com.cloudage.membercenter.entity.Comment;
import com.cloudage.membercenter.entity.Likes;
import com.cloudage.membercenter.entity.User;
import com.cloudage.membercenter.service.IArticleService;
import com.cloudage.membercenter.service.ICommentService;
import com.cloudage.membercenter.service.ILikesService;
import com.cloudage.membercenter.service.IUserService;

import org.apache.commons.io.FileUtils;

@RestController
@RequestMapping("/api")
public class APIController {

	@Autowired
	IUserService userService;
	@Autowired
	IArticleService articleService;
	@Autowired
	ICommentService commentService;
	@Autowired
	ILikesService likesService;

	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	public @ResponseBody String hello() {
		System.out.println("HELLO");
		return "HELLO WORLD";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public @ResponseBody User register(@RequestParam String account,
			@RequestParam String passwordHash, @RequestParam String email,
			@RequestParam String name, MultipartFile avatar,
			HttpServletRequest request) {

		User user = new User();
		user.setAccount(account);
		user.setPasswordHash(passwordHash);
		user.setEmail(email);
		user.setName(name);

		if (avatar != null) {
			try {
				String realPath = request.getSession().getServletContext()
						.getRealPath("/WEB-INF/upload");
				System.out.println(realPath);
				FileUtils.copyInputStreamToFile(avatar.getInputStream(),
						new File(realPath, account + ".png"));
				user.setAvatar("upload/" + account + ".png");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return userService.save(user);
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public @ResponseBody User register(@RequestParam String account,
			@RequestParam String passwordHash, HttpServletRequest request) {
		User user = userService.login(account);

		if (user != null && user.getPasswordHash().equals(passwordHash)) {
			request.getSession().setAttribute("uid", user.getId());
			return user;
		}

		return null;
	}

	@RequestMapping(value = "/me", method = RequestMethod.GET)
	public User getCurrentUser(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		Integer uid = (Integer) session.getAttribute("uid");
		return userService.findUserById(uid);
	}

	@RequestMapping(value = "/passwordRecover", method = RequestMethod.POST)
	public @ResponseBody boolean passwordRecover(@RequestParam String email,
			@RequestParam String passwordHash, HttpServletRequest request) {

		User user = userService.findUserByEmail(email);
		
		if (user != null) {
			user.setPasswordHash(passwordHash);
			userService.save(user);
			return true;

		}
		return false;
	}

	@RequestMapping(value = "/article", method = RequestMethod.POST)
	public Article addArticle(@RequestParam String title,
			@RequestParam String text, HttpServletRequest request) {
		User currentUser = getCurrentUser(request);
		Article article = new Article();
		article.setAuthor(currentUser);
		article.setTitle(title);
		article.setText(text);
		return articleService.save(article);
	}

	@RequestMapping(value = "/feeds/{page}")
	public Page<Article> getFeeds(@PathVariable int page) {

		return articleService.getFeeds(page);
	}

	@RequestMapping(value = "/feeds")
	public Page<Article> getFeeds() {
		return articleService.getFeeds(0);
	}

	@RequestMapping(value = "/article/{article_id}/comments/{page}")
	public Page<Comment> getComments(@PathVariable int article_id,
			@PathVariable int page) {
		return commentService.findAllByArticleId(article_id, page);
	}

	@RequestMapping(value = "/article/{article_id}/comments")
	public Page<Comment> getComments(@PathVariable int article_id) {
		return commentService.findAllByArticleId(article_id, 0);
	}

	@RequestMapping(value = "/article/{article_id}/comments", method = RequestMethod.POST)
	public @ResponseBody Comment saveComment(@PathVariable int article_id,
			@RequestParam String content, HttpServletRequest request) {

		User me = getCurrentUser(request);
		Article article = articleService.findOne(article_id);
		Comment comment = new Comment();
		comment.setAuthor(me);
		comment.setArticle(article);
		comment.setContent(content);
		return commentService.save(comment);
	}

	@RequestMapping(value = "/article/{article_id}/likes" ,method=RequestMethod.POST)
	public @ResponseBody int changeLikes(@PathVariable int article_id,
			@RequestParam boolean likes,
			HttpServletRequest request) {

		User me = getCurrentUser(request);

		Article article = articleService.findOne(article_id);
	
		if(likes)
		{
			likesService.addLikes(me,article);
		}
		else{
			likesService.removeLikes(me,article);
		}
		
		
		return likesService.countLikes(article_id);
	}
	
	@RequestMapping(value = "/article/{article_id}/likes")
	public int  countLikes(@PathVariable int article_id) {
		return likesService.countLikes(article_id);
	}

	@RequestMapping(value = "/article/{article_id}/isLikes")
	public boolean  checkLiked(@PathVariable int article_id,HttpServletRequest request) {
		User me=getCurrentUser(request);
		return likesService.checkLiked(me.getId(),article_id);
	}
	
	@RequestMapping(value = "/article/search/{keyword}")
	public Page<Article> searchArticleWithKeyword(@PathVariable String keyword,
			@RequestParam (defaultValue="0") int page) {

		return articleService.searchTextWithKeyword(keyword,page);
	}
	@RequestMapping(value = "/comment/toMe{page}")
	public Page<Comment> findCommentToMe(@PathVariable int page,HttpServletRequest request) {
		User me=getCurrentUser(request);
		return commentService.findCommentToMe(me.getId(),page);
	}

	@RequestMapping(value = "/comment/toOther/{page}")
	public Page<Comment> findMyComment(@PathVariable int page,HttpServletRequest request) {
		User me=getCurrentUser(request);
		return commentService.findMyComment(me.getId(),page);
	}
	
}
