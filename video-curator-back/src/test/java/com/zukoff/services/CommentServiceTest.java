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

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(value = {"/sql/seed.sql"})
public class CommentServiceTest {
    private Comment comment;

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @Autowired
    private VideoService videoService;

    @Before
    public void setUp() throws Exception {
        User user = this.userService.findUserById(3);
        Video video = this.videoService.findVideoById(2);
        comment = new Comment("Awesome video!",user);
        comment.setVideo(video);
    }

    @Test
    public void shouldFindCommentById() throws Exception {
        Comment comment1 = this.commentService.findCommentById(2);
        assertEquals("Incredible",comment1.getText());
    }

    @Test
    public void shouldDeleteComment() throws Exception {
        Comment comment1 = this.commentService.findCommentById(1);
        assertEquals("Dope.",comment1.getText());
        this.commentService.deleteCommentById(1);
        assertNull(this.commentService.findCommentById(1));
    }

    @Test
    public void shouldFindVideoFromComment() throws Exception {
        Video video1 = this.commentService.findVideoFromComment(1);
        assertEquals("Dope.",this.commentService.findCommentById(1).getText());
        assertEquals("1612",video1.getTitle());
        assertEquals(50,video1.getVotes());
        Video video2 = this.commentService.findVideoFromComment(5);
        assertEquals(1000,video2.getVotes());
    }

    @Test
    public void shouldFindUserFromComment() throws Exception {
        User user1 = this.commentService.findUserFromComment(2);
        assertEquals("pghaderi@gmail.com",user1.getUsername());
        assertEquals(3,user1.getId());
        User user2 = this.commentService.findUserFromComment(6);
        assertEquals("azukoff@gmail.com",user2.getUsername());
        assertEquals(0,user2.getVersion());
    }

    @Test
    public void shouldUpdateVoteCount() throws Exception {
        Comment comment1 = this.commentService.findCommentById(2);
        assertEquals(19,comment1.getVotes());
        Comment comment2 = this.commentService.increaseVotes(2);
        assertEquals(20,comment2.getVotes());
        Comment comment3 = this.commentService.increaseVotes(2);
        Comment comment4 = this.commentService.increaseVotes(2);
        Comment comment5 = this.commentService.decreaseVotes(2);
        assertEquals(21,comment5.getVotes());
    }

}