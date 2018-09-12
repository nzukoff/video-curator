package com.zukoff.controllers;

import com.zukoff.dtos.AuthDto;
import com.zukoff.entities.Comment;
import com.zukoff.entities.Role;
import com.zukoff.entities.User;
import com.zukoff.entities.Video;
import com.zukoff.enums.RoleEnum;
import com.zukoff.security.JwtToken;
import com.zukoff.services.UserService;
import com.zukoff.services.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.*;

@RestController
@RequestMapping(value="/videos")
public class VideoController {
    private VideoService videoService;
    private UserService userService;

    @Autowired
    public void setVideoService(VideoService videoService) {
        this.videoService = videoService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = {"", "/"}, method = RequestMethod.POST)
    public Video createVideo(Principal user, @RequestBody Video video){
        int uid = ((JwtToken)user).getUserId();
        User u = userService.findUserById(uid);
        video.setUser(u);
        video.setVotes(1);
        u.getVideos().add(video);
        userService.saveUser(u);
        return video;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Video findVideoById(@PathVariable int id) {
        return this.videoService.findVideoById(id);
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.DELETE)
    public void deleteVideoById(@PathVariable int id) {
        this.videoService.deleteVideo(id);
    }

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public Iterable<Video> findAllVideos() {
        return this.videoService.findAllVideos();
    }

    @RequestMapping(value = "/{id}/comments", method = RequestMethod.POST)
    public Comment createComment(@RequestBody Comment comment,@PathVariable int id) {
        return this.videoService.createComment(comment,id);
    }

    @RequestMapping(value = "/{id}/comments", method = RequestMethod.GET)
    public List<Comment> findCommentsFromVideo(@PathVariable int id) {
        return this.videoService.findCommentsFromVideo(id);
    }

    @RequestMapping(value = "/{id}/upvote", method = RequestMethod.PUT)
    public Video increaseVotes(@PathVariable int id) {
        return this.videoService.increaseVotes(id);
    }

    @RequestMapping(value = "/{id}/downvote", method = RequestMethod.PUT)
    public Video decreaseVotes(@PathVariable int id) {
        return this.videoService.decreaseVotes(id);
    }

    @RequestMapping(value = "{id}/comments/count", method = RequestMethod.GET)
    public Map<String,Integer> countCommentsFromVideo(@PathVariable int id){
        Map<String, Integer> commentCount = new HashMap<>();
        int count = this.videoService.countCommentsFromVideo(id);
        commentCount.put("Comments", count);
        return commentCount;
    }

    @RequestMapping(value = "/{id}/user", method = RequestMethod.GET)
    public User findUserFromVideo(@PathVariable int id) {
        return this.videoService.findUserFromVideo(id);
    }



}
