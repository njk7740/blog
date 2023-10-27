package com.voda.blog;

import com.voda.blog.post.PostService;
import com.voda.blog.user.SiteUser;
import com.voda.blog.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BlogApplicationTests {
	@Autowired
	private PostService postService;
	@Autowired
	private UserService userService;

	@Test
	void contextLoads() {
		SiteUser user = userService.getByUsername("njk7740");
		for(int i = 0; i < 100; i++) {
			postService.create("테스트용 "+i, "내용없음", user);
		}
	}

}
