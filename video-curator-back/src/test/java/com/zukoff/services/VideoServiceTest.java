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
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(value = {"/sql/seed.sql"})
public class VideoServiceTest {
    private Video video;
    private User user;

    @Autowired
    private VideoService videoService;

    @Autowired
    private UserService userService;

    @Before
    public void setUp() throws Exception {
        user = this.userService.findUserById(1);
        video = new Video("Grandpa Light-up Shoes","https://www.youtube.com/watch?v=OQ_IdA5cbvk");
        video.setUser(user);
    }

    @Test
    public void shouldCreateVideo() throws Exception {
        Video videoSave = this.videoService.createVideo(video, "nzukoff@gmail.com");
        assertEquals(7,videoSave.getId());
        assertEquals(0,videoSave.getVersion());
        assertEquals(1,videoSave.getVotes());
        assertEquals("Grandpa Light-up Shoes", videoSave.getTitle());
    }

    @Test
    public void shouldDeleteVideo() throws Exception {
        Video videoSave = this.videoService.createVideo(video, "nzukoff@gmail.com");
        assertEquals(7,videoSave.getId());
        this.videoService.deleteVideo(7);
        Video videoNull = this.videoService.findVideoById(7);
        assertNull(videoNull);
    }

    @Test
    public void shouldFindVideoById() throws Exception {
        Video videoFind = this.videoService.findVideoById(2);
        assertEquals(0,videoFind.getVersion());
        assertEquals("Flinstones",videoFind.getTitle());
    }

    @Test
    public void shouldFindAllVideos() throws Exception {
        ArrayList<Video> videoList = (ArrayList<Video>) this.videoService.findAllVideos();
        assertEquals(6,videoList.size());
    }

    @Test
    public void shouldUpdateVoteCount() throws Exception {
        Video video1 = this.videoService.findVideoById(2);
        assertEquals(19,video1.getVotes());
        Video video2 = this.videoService.increaseVotes(2);
        assertEquals(20,video2.getVotes());
        Video video3 = this.videoService.increaseVotes(2);
        Video video4 = this.videoService.increaseVotes(2);
        Video video5 = this.videoService.decreaseVotes(2);
        assertEquals(21,video5.getVotes());
    }

    @Test
    public void shouldCreateCommentOnVideo() throws Exception {
        Comment comment = new Comment("I need a comment",user);
        Comment commentSave = this.videoService.createComment(comment,1);
        assertEquals(8,commentSave.getId());
        assertEquals(1,commentSave.getVotes());
        assertEquals("I need a comment",commentSave.getText());
        assertEquals("cmedford@gmail.com",commentSave.getUser().getUsername());
        assertEquals("1612",commentSave.getVideo().getTitle());
    }

    @Test
    public void shouldFindUserOfVideo() throws Exception {
        User user1 = this.videoService.findUserFromVideo(2);
        assertEquals("cmedford@gmail.com",user1.getUsername());
        User user2 = this.videoService.findUserFromVideo(6);
        assertEquals("pghaderi@gmail.com",user2.getUsername());
        assertEquals(3,user2.getId());
    }

    @Transactional
    @Test
    public void shouldFindCommentsFromVideo() throws Exception {
        List<Comment> commentList = this.videoService.findCommentsFromVideo(1);
        assertEquals(2,commentList.size());
        assertEquals("Dope.",commentList.get(0).getText());
        List<Comment> commentList1 = this.videoService.findCommentsFromVideo(5);
        assertEquals(1,commentList1.size());
    }

    @Test
    public void shouldCountCommentsFromVideo() throws Exception {
        int commentCount = this.videoService.countCommentsFromVideo(1);
        assertEquals(2,commentCount);
    }

}