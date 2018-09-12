package com.zukoff.controllers;

import com.zukoff.dtos.AuthDto;
import com.zukoff.dtos.UserDto;
import com.zukoff.entities.Comment;
import com.zukoff.entities.Role;
import com.zukoff.entities.User;
import com.zukoff.entities.Video;
import com.zukoff.enums.RoleEnum;
import com.zukoff.security.JwtToken;
import com.zukoff.services.RoleService;
import com.zukoff.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value="/users")
public class UserController {
    private static final String EMAIL_EXISTS_MESSAGE = "This email is in use";

    private PasswordEncoder passwordEncoder;
    private UserService userService;
    private RoleService roleService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @RequestMapping(value = {"", "/"}, method = RequestMethod.POST)
    public ResponseEntity<?> createUser(@Valid @RequestBody AuthDto auth) {
        System.out.println("USER CONTROLLER INDEX");
        final String requestedEmail = auth.getUsername();

        UserDetails ud =  userService.loadUserByUsername(requestedEmail);
        if (ud != null) {
            return ResponseEntity.badRequest().body(EMAIL_EXISTS_MESSAGE);
        }

        User user = new User();
        user.setEnabled(true);
        user.setUsername(auth.getUsername());
        user.setPassword(passwordEncoder.encode(auth.getPassword()));

        user.setRoles(new ArrayList<>());
        Role r = roleService.findByRole(RoleEnum.USER);
        user.getRoles().add(r);

        User savedUser = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @RequestMapping(value = "/credentials", method = RequestMethod.GET)
    public UserDto credentials(Principal user) {
        System.out.println("USER CONTROLLER CREDENTIALS");
        int uid = ((JwtToken)user).getUserId();
        User u = userService.findUserById(uid);
        List<String> roles = u.getRoles().stream().map(r -> r.getRole().toString()).collect(Collectors.toList());
        UserDto userDto = new UserDto(u.getUsername(), roles);
        return userDto;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public User findUserById(@PathVariable int id) {
        return this.userService.findUserById(id);
    }


    @RequestMapping(value = "/{id}/comments", method = RequestMethod.GET)
    public List<Comment> findCommentsFromUser(@PathVariable int id) {
        return this.userService.findCommentsFromUser(id);
    }

    @RequestMapping(value = "{id}/comments/count", method = RequestMethod.GET)
    public Map<String,Integer> countCommentsFromUser(@PathVariable int id){
        Map<String, Integer> commentCount = new HashMap<>();
        int count = this.userService.countCommentsFromUser(id);
        commentCount.put("Comments", count);
        return commentCount;
    }

    @RequestMapping(value = "/{id}/videos", method = RequestMethod.GET)
    public List<Video> findVideosFromUser(@PathVariable int id) {
        return this.userService.findVideosFromUser(id);
    }

    @RequestMapping(value = "{id}/videos/count", method = RequestMethod.GET)
    public Map<String,Integer> countVideosFromUser(@PathVariable int id){
        Map<String, Integer> videoCount = new HashMap<>();
        int count = this.userService.countVideosFromUser(id);
        videoCount.put("Videos", count);
        return videoCount;
    }


}
