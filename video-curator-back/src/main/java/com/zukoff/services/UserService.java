package com.zukoff.services;

import com.zukoff.entities.Comment;
import com.zukoff.entities.Role;
import com.zukoff.entities.User;
import com.zukoff.entities.Video;
import com.zukoff.enums.RoleEnum;
import com.zukoff.repositories.ICommentRepository;
import com.zukoff.repositories.IUserRepository;
import com.zukoff.repositories.IVideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService implements UserDetailsService {
    private IUserRepository userRepository;

    private IVideoRepository videoRepository;

    private ICommentRepository commentRepository;

    @Autowired
    public void setUserRepository(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setVideoRepository(IVideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    @Autowired
    public void setCommentRepository(ICommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = userRepository.findByUsername(username);
//        if (user == null) throw new UsernameNotFoundException("Username " + username + " not found");
//        //return my own user not springs user, my user class extends userdetails
//        return user;
//    }

    //generate random string
    //pull in uuid library (or crypto bites)

    public User saveUser(User user) {
        System.out.println("USERSERVICE SAVE USER");
        return this.userRepository.save(user);
    }

    public User findUserById(int id) {
        System.out.println("USERSERVICE FIND USER BY ID");
        return this.userRepository.findOne(id);
    }

    public User findUserByUsername(String username) {
        System.out.println("USERSERVICE FIND USER BY USERNAME");
        return this.userRepository.findByUsername(username);
    }

    public void deleteUserById(int id) {
        this.userRepository.delete(id);
    }

//    public Optional<User> getByUsername(String username) {
//        return this.userRepository.findUserByUsername(username);
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("USERSERVICE LOAD USER BY USERNAME");
        return this.userRepository.findByUsername(username);
    }

    public List<Comment> findCommentsFromUser(int id) { return this.findUserById(id).getComments(); }

    public List<Video> findVideosFromUser(int id) { return this.findUserById(id).getVideos(); }

    public int countVideosFromUser(int user_id) { return this.videoRepository.countVideos(user_id); }

    public int countCommentsFromUser(int user_id) { return this.commentRepository.countCommentsFromUser(user_id); }
 }
