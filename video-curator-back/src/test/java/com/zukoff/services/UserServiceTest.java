package com.zukoff.services;

import com.zukoff.entities.Comment;
import com.zukoff.entities.User;
import com.zukoff.entities.Video;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(value = {"/sql/seed.sql"})
public class UserServiceTest {
    private User user;

    @Autowired
    private UserService userService;

    @Before
    public void SetUp() throws Exception {
        user = new User("nzukoff@gmail.com");
    }

    @Test
    public void shouldCreateUser() throws Exception {
        User user1 = this.userService.saveUser(user);
        assertEquals(4,user1.getId());
        assertEquals("nzukoff@gmail.com",user1.getUsername());
        assertEquals(0,user1.getVersion());
    }

    @Test
    public void shouldFindUserById() throws Exception {
        User user1 = this.userService.findUserById(2);
        assertEquals("azukoff@gmail.com",user1.getUsername());
        assertEquals(0,user1.getVersion());
    }

    @Test
    public void shouldFindUserByUsername() throws Exception {
        User user1 = this.userService.findUserByUsername("nzukoff@gmail.com");
        assertEquals(4,user1.getId());
        assertEquals(0,user1.getVersion());
    }

    @Test
    public void shouldDeleteUser() throws Exception {
        User user1 = this.userService.findUserById(1);
        assertEquals("cmedford",user1.getUsername());
        this.userService.deleteUserById(1);
        User userNull = this.userService.findUserById(1);
        assertNull(userNull);
    }

    @Transactional
    @Test
    public void shouldFindVideosFromUser() throws Exception {
        List<Video> videoList1 = this.userService.findVideosFromUser(1);
        assertEquals(2,videoList1.size());
        List<Video> videoList2 = this.userService.findVideosFromUser(2);
        assertEquals(3,videoList2.size());
        assertEquals(104,videoList2.get(1).getVotes());
        assertEquals("What happens if youre allergic to water?",videoList2.get(1).getTitle());
    }

    @Test
    public void shouldCountAllVideosFromUser() throws Exception {
        int vidCount = this.userService.countVideosFromUser(2);
        assertEquals(3,vidCount);
        int vidCount2 = this.userService.countVideosFromUser(1);
        assertEquals(2,vidCount2);
    }

    @Transactional
    @Test
    public void shouldFindAllCommentsFromUser() throws Exception {
        List<Comment> commentList1 = this.userService.findCommentsFromUser(3);
        assertEquals(2,commentList1.size());
        assertEquals("Cute",commentList1.get(1).getText());
        assertEquals(5,commentList1.get(1).getId());
    }

    @Test
    public void shouldCountCommentsFromUser() throws Exception {
        int commentCount1 = this.userService.countCommentsFromUser(1);
        assertEquals(3,commentCount1);
        int commentCount2 = this.userService.countCommentsFromUser(3);
        assertEquals(2,commentCount2);
    }


}