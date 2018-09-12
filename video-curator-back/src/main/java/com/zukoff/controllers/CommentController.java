package com.zukoff.controllers;


import com.zukoff.entities.Comment;
import com.zukoff.entities.User;
import com.zukoff.entities.Video;
import com.zukoff.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value="/comments")
public class CommentController {
    private CommentService commentService;

    @Autowired
    public void setCommentService(CommentService commentService) {
        this.commentService = commentService;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Comment findCommentById(@PathVariable int id) {
        return this.commentService.findCommentById(id);
    }

    @RequestMapping(value = "/{id}/video", method = RequestMethod.GET)
    public Video findVideoFromComment(@PathVariable int id) {
        return this.commentService.findVideoFromComment(id);
    }

    @RequestMapping(value = "/{id}/user", method = RequestMethod.GET)
    public User findUserFromComment(@PathVariable int id) {
        return this.commentService.findUserFromComment(id);
    }

    @RequestMapping(value = "/{id}/upvote", method = RequestMethod.PUT)
    public Comment increaseVotes(@PathVariable int id) {
        return this.commentService.increaseVotes(id);
    }

    @RequestMapping(value = "/{id}/downvote", method = RequestMethod.PUT)
    public Comment decreaseVotes(@PathVariable int id) {
        return this.commentService.decreaseVotes(id);
    }


}
