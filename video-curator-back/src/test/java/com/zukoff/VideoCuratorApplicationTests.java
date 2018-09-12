package com.zukoff;

import com.zukoff.controllers.CommentControllerTest;
import com.zukoff.controllers.UserControllerTest;
import com.zukoff.controllers.VideoControllerTest;
import com.zukoff.services.CommentServiceTest;
import com.zukoff.services.UserServiceTest;
import com.zukoff.services.VideoServiceTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(Suite.class)
@SpringBootTest
@Suite.SuiteClasses({
		UserServiceTest.class,
		VideoServiceTest.class,
		CommentServiceTest.class,
		UserControllerTest.class,
		VideoControllerTest.class,
		CommentControllerTest.class
})
public class VideoCuratorApplicationTests {

	@Test
	public void contextLoads() {
	}

}
