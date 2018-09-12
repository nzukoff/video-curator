package com.zukoff.controllers;

import com.zukoff.entities.Comment;
import com.zukoff.entities.User;
import com.zukoff.entities.Video;
import com.zukoff.services.CommentService;
import com.zukoff.services.VideoService;
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

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@AutoConfigureMockMvc(secure=false)
@RunWith(SpringRunner.class)
@WebMvcTest(VideoController.class)
public class VideoControllerTest {
    private Video video;
    private Video video2;
    private List<Video> videoList;
    private Comment comment1;
    private Comment comment2;
    private List<Comment> commentList;
    private User user;


    @Autowired
    private MockMvc mvc;

    @MockBean
    private VideoService videoService;

    @Before
    public void setUp() throws Exception {
        user = new User("nzukoff@gmail.com");
        user.setId(1);
        video = new Video("Babys first bacon","https://www.youtube.com/watch?v=OtMVMNST_g4");
        video.setUser(user);
        video.setId(1);
        video.setVotes(1);
        video2 = new Video("Giant Human Claw Machine", "https://www.youtube.com/watch?v=jVejErcTH8k");
        video.setUser(user);
        videoList = new ArrayList<>();
        videoList.add(video);
        videoList.add(video2);
        comment1 = new Comment("Baby!",user);
        comment1.setVideo(video);
        comment2 = new Comment("Bacon is gud",user);
        comment2.setVideo(video);
        commentList = new ArrayList<>();
        commentList.add(comment1);
        commentList.add(comment2);
    }

    @Test
    public void shouldCreateVideo() throws Exception {
        given(this.videoService.createVideo(Mockito.any(Video.class),"nzukoff@gmail.com"))
                .willReturn(video);


        MockHttpServletRequestBuilder request = post("/videos")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "      \"title\": \"Babys first bacon\",\n" +
                        "      \"votes\": 1,\n" +
                        "      \"link\": \"https://www.youtube.com/watch?v=OtMVMNST_g4\",\n" +
                        "      \"user\": {\n" +
                        "        \"id\": 1,\n" +
                        "        \"version\": 0,\n" +
                        "        \"email\": \"nzukoff@gmail.com\",\n" +
                        "        \"firstName\": \"Nathan\",\n" +
                        "        \"lastName\": \"Zukoff\",\n" +
                        "        \"username\": \"nzukoff\",\n" +
                        "        \"created\": 1490726107000,\n" +
                        "        \"modified\": 1490726107000\n" +
                        "      }\n" +
                        "  }");

        this.mvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Babys first bacon")));
    }

    @Test
    public void shouldFindVideo() throws Exception {
        when(this.videoService.findVideoById(1)).thenReturn(video);

        MockHttpServletRequestBuilder request = get("/videos/1");

        this.mvc.perform(request)
                .andDo(print())
                .andExpect(jsonPath("$.votes",is(1)))
                .andExpect(jsonPath("$.title",is("Babys first bacon")));
    }

    @Test
    public void shouldFindAllVideos() throws Exception {
        when(this.videoService.findAllVideos()).thenReturn((Iterable<Video>) videoList);

        MockHttpServletRequestBuilder request = get("/videos");

        this.mvc.perform(request)
                .andDo(print())
                .andExpect(jsonPath("$.*",hasSize(2)));
    }

    @Test
    public void shouldFindCommentsFromVideo() throws Exception {
        when(this.videoService.findCommentsFromVideo(1)).thenReturn(commentList);

        MockHttpServletRequestBuilder request = get("/videos/1/comments");

        this.mvc.perform(request)
                .andDo(print())
                .andExpect(jsonPath("$.*",hasSize(2)))
                .andExpect(jsonPath("$[1].text", is("Bacon is gud")));
    }

    @Test
    public void shouldCreateComment() throws Exception {
        given(this.videoService.createComment(Mockito.any(Comment.class),eq(1)))
                .willReturn(comment1);

        MockHttpServletRequestBuilder request = post("/videos/1/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"text\": \"New Comment!\",\n" +
                        "  \"user\": {\n" +
                        "    \"id\": 1,\n" +
                        "    \"version\": 0,\n" +
                        "    \"email\": \"pghaderi@gmail.com\",\n" +
                        "    \"firstName\": \"Parisa\",\n" +
                        "    \"lastName\": \"Ghaderi\",\n" +
                        "    \"username\": \"pghaderi\",\n" +
                        "    \"created\": 1490726107000,\n" +
                        "    \"modified\": 1490726107000\n" +
                        "  }\n" +
                        "}");

        this.mvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text", is("Baby!")));
    }


    @Test
    public void shouldIncreaseVideoVote() throws Exception {
        when(this.videoService.increaseVotes(1)).thenReturn(video);

        MockHttpServletRequestBuilder request = put("/videos/1/upvote");

        this.mvc.perform(request)
                .andDo(print())
                .andExpect(jsonPath("$.title",is("Babys first bacon")))
                .andExpect(jsonPath("$.votes",is(1)));
    }

    @Test
    public void shouldDecreaseVideoVote() throws Exception {
        when(this.videoService.decreaseVotes(1)).thenReturn(video);

        MockHttpServletRequestBuilder request = put("/videos/1/downvote");

        this.mvc.perform(request)
                .andDo(print())
                .andExpect(jsonPath("$.title",is("Babys first bacon")))
                .andExpect(jsonPath("$.votes",is(1)));
    }

    @Test
    public void shouldCountAllCommentsFromVideo() throws Exception {
        when(this.videoService.countCommentsFromVideo(1)).thenReturn(2);

        MockHttpServletRequestBuilder request = get("/videos/1/comments/count");

        this.mvc.perform(request)
                .andDo(print())
                .andExpect(jsonPath("$.Comments",is(2)));
    }

    @Test
    public void shouldFindUserFromVideo() throws Exception {
        when(this.videoService.findUserFromVideo(1)).thenReturn(user);

        MockHttpServletRequestBuilder request = get("/videos/1/user");

        this.mvc.perform(request)
                .andDo(print())
                .andExpect(jsonPath("$.firstName", is("Nathan")));
    }

}