package com.zukoff.controllers;

import com.zukoff.entities.Comment;
import com.zukoff.entities.User;
import com.zukoff.entities.Video;
import com.zukoff.services.CommentService;
import com.zukoff.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CommentController.class)
@AutoConfigureMockMvc(secure=false)
public class CommentControllerTest {
    private Comment comment;
    private Video video;
    private User user;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CommentService commentService;

    @Before
    public void setUp() throws Exception {
        user = new User("nzukoff@gmail.com");
        user.setId(1);
        video = new Video("Babys first bacon","https://www.youtube.com/watch?v=OtMVMNST_g4");
        video.setUser(user);
        video.setId(1);
        comment = new Comment("Baby!",user);
        comment.setVideo(video);
        comment.setVotes(2);
    }

    @Test
    public void shouldFindComment() throws Exception {
        when(this.commentService.findCommentById(1)).thenReturn(comment);

        MockHttpServletRequestBuilder request = get("/comments/1");

        this.mvc.perform(request)
                .andDo(print())
                .andExpect(jsonPath("$.text",is("Baby!")))
                .andExpect(jsonPath("$.user.username",is("nzukoff")));
    }

    @Test
    public void shouldFindVideoFromComment() throws Exception {
        when(this.commentService.findVideoFromComment(1)).thenReturn(video);

        MockHttpServletRequestBuilder request = get("/comments/1/video");

        this.mvc.perform(request)
                .andDo(print())
                .andExpect(jsonPath("$.title",is("Babys first bacon")));
    }

    @Test
    public void shouldFindUserFromComment() throws Exception {
        when(this.commentService.findUserFromComment(1)).thenReturn(user);

        MockHttpServletRequestBuilder request = get("/comments/1/user");

        this.mvc.perform(request)
                .andDo(print())
                .andExpect(jsonPath("$.firstName",is("Nathan")));
    }

    @Test
    public void shouldIncreaseCommentVote() throws Exception {
        when(this.commentService.increaseVotes(1)).thenReturn(comment);

        MockHttpServletRequestBuilder request = put("/comments/1/upvote");

        this.mvc.perform(request)
                .andDo(print())
                .andExpect(jsonPath("$.text",is("Baby!")))
                .andExpect(jsonPath("$.votes",is(2)));
    }

    @Test
    public void shouldDecreaseCommentVote() throws Exception {
        when(this.commentService.decreaseVotes(1)).thenReturn(comment);

        MockHttpServletRequestBuilder request = put("/comments/1/downvote");

        this.mvc.perform(request)
                .andDo(print())
                .andExpect(jsonPath("$.text",is("Baby!")))
                .andExpect(jsonPath("$.votes",is(2)));
    }

}