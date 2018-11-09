package com.zukoff.services;

import com.zukoff.entities.Comment;
import com.zukoff.entities.User;
import com.zukoff.entities.Video;
import com.zukoff.repositories.ICommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    private ICommentRepository commentRepository;

    @Autowired
    public void setCommentRepository(ICommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Comment findCommentById(int id) {
        return this.commentRepository.findOneById(id);
    }

//    public void deleteCommentById(int id) {
//        this.commentRepository.delete(id);
//    }

    public Video findVideoFromComment(int id) {
        return this.commentRepository.findOneById(id).getVideo();
    }

    public User findUserFromComment(int id) {
        return this.commentRepository.findOneById(id).getUser();
    }

    public Comment increaseVotes(int id) {
        Comment comment = this.commentRepository.findOneById(id);
        int votes = comment.getVotes();
        comment.setVotes(votes+1);
        return this.commentRepository.save(comment);
    }

    public Comment decreaseVotes(int id) {
        Comment comment = this.commentRepository.findOneById(id);
        int votes = comment.getVotes();
        comment.setVotes(votes-1);
        return this.commentRepository.save(comment);
    }
}
