package com.zukoff.controllers;

import com.zukoff.entities.Comment;
import com.zukoff.entities.User;
import com.zukoff.entities.Video;
import com.zukoff.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(secure=false)
public class UserControllerTest {
    private User user;
    private List<Comment> commentList;
    private List<Video> videoList;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @Before
    public void setUp() throws Exception {
        user = new User("nzukoff@gmail.com");
        user.setId(1);
        Video video1 = new Video("Babys first bacon","https://www.youtube.com/watch?v=OtMVMNST_g4");
        video1.setUser(user);
        video1.setId(1);
        Video video2 = new Video("First warhead candy","https://www.youtube.com/watch?v=ini1EWqTgd4");
        video2.setUser(user);
        video2.setId(2);
        videoList = new ArrayList<>();
        videoList.add(video1);
        videoList.add(video2);
        Comment comment1 = new Comment("Whoa",user);
        comment1.setVideo(video1);
        comment1.setId(1);
        Comment comment2 = new Comment("Yummy",user);
        comment2.setVideo(video1);
        comment2.setId(2);
        commentList = new ArrayList<>();
        commentList.add(comment1);
        commentList.add(comment2);
    }

    @Test
    public void shouldCreateUser() throws Exception {
        given(this.userService.saveUser(Mockito.any(User.class)))
                .willReturn(user);

        MockHttpServletRequestBuilder request = post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"nzukoff@gmail.com\",\n" +
                        "\"firstName\":\"Nathan\",\n" +
                        "\"lastName\": \"Zukoff\",\n" +
                        "\"username\":\"nzukoff\"}");

        this.mvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is("Nathan")));
    }

    @Test
    public void shouldFindUser() throws Exception {
        when(this.userService.findUserById(1)).thenReturn(user);

        MockHttpServletRequestBuilder request = get("/users/1");

        this.mvc.perform(request)
                .andDo(print())
                .andExpect(jsonPath("$.id",is(1)))
                .andExpect(jsonPath("$.username",is("nzukoff")));
    }

    @Test
    public void shouldFindAllCommentsFromUser() throws Exception {
        when(this.userService.findCommentsFromUser(1)).thenReturn(commentList);

        MockHttpServletRequestBuilder request = get("/users/1/comments");

        this.mvc.perform(request)
                .andDo(print())
                .andExpect(jsonPath("$.*",hasSize(2)))
                .andExpect(jsonPath("$[0].text",is("Whoa")));
    }

    @Test
    public void shouldCountAllCommentsFromUser() throws Exception {
        when(this.userService.countCommentsFromUser(1)).thenReturn(2);

        MockHttpServletRequestBuilder request = get("/users/1/comments/count");

        this.mvc.perform(request)
                .andDo(print())
                .andExpect(jsonPath("$.Comments",is(2)));
    }


    @Test
    public void shouldFindAllVideosFromUser() throws Exception {
        when(this.userService.findVideosFromUser(1)).thenReturn(videoList);

        MockHttpServletRequestBuilder request = get("/users/1/videos");

        this.mvc.perform(request)
                .andDo(print())
                .andExpect(jsonPath("$.*",hasSize(2)))
                .andExpect(jsonPath("$[1].title",is("First warhead candy")));
    }


    @Test
    public void shouldCountAllVideosFromUser() throws Exception {
        when(this.userService.countVideosFromUser(2)).thenReturn(3);

        MockHttpServletRequestBuilder request = get("/users/2/videos/count");

        this.mvc.perform(request)
                .andDo(print())
                .andExpect(jsonPath("$.Videos",is(3)));
    }




}