package com.zukoff.services;

import com.zukoff.entities.Comment;
import com.zukoff.entities.User;
import com.zukoff.entities.Video;
import com.zukoff.repositories.ICommentRepository;
import com.zukoff.repositories.IUserRepository;
import com.zukoff.repositories.IVideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class VideoService {
    private IVideoRepository videoRepository;

    private ICommentRepository commentRepository;

    private IUserRepository userRepository;

    @Autowired
    public void setCommentRepository(ICommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Autowired
    public void setVideoRepository(IVideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    @Autowired
    public void setUserRepository(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Video createVideo(Video video, String username) {
        video.setVotes(1);
        User user = this.userRepository.findByUsername(username);
        video.setUser(user);
        return this.videoRepository.save(video);
    }

    public Video findVideoById(int id) {
        return this.videoRepository.findOne(id);
    }

    public Iterable<Video> findAllVideos() { return this.videoRepository.findAll(); }

    public void deleteVideo(int id) {
        this.videoRepository.delete(id);
    }

    public Video increaseVotes(int id) {
        Video video = this.videoRepository.findOne(id);
        int votes = video.getVotes();
        video.setVotes(votes+1);
        return this.videoRepository.save(video);
    }

    public Video decreaseVotes(int id) {
        Video video = this.videoRepository.findOne(id);
        int votes = video.getVotes();
        video.setVotes(votes-1);
        return this.videoRepository.save(video);
    }

    public Comment createComment(Comment comment, int video_id) {
        Video video = this.videoRepository.findOne(video_id);
        comment.setVideo(video);
        comment.setVotes(1);
        return this.commentRepository.save(comment);
    }

    public List<Comment> findCommentsFromVideo(int id) { return this.videoRepository.findOne(id).getComments(); }

    public int countCommentsFromVideo(int video_id) { return this.commentRepository.countCommentsFromVideo(video_id); }

    public User findUserFromVideo(int id) {
        return this.videoRepository.findOne(id).getUser();
    }
}
