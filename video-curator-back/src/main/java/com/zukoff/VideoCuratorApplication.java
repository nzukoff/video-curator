package com.zukoff;

import com.zukoff.entities.Role;
import com.zukoff.entities.User;
import com.zukoff.entities.Video;
import com.zukoff.enums.RoleEnum;
import com.zukoff.repositories.ICommentRepository;
import com.zukoff.repositories.IUserRepository;
import com.zukoff.repositories.IVideoRepository;
//import com.zukoff.configuration.SecurityConfig;
import org.omg.IOP.CodecOperations;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class VideoCuratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(VideoCuratorApplication.class, args);
	}

	@Bean
	public CommandLineRunner seedData(IUserRepository userRepository, IVideoRepository videoRepository, ICommentRepository commentRepository, PasswordEncoder passwordEncoder) {
		return (args) -> {
			userRepository.deleteAll();
			videoRepository.deleteAll();
			commentRepository.deleteAll();
			User user = new User("nzukoff@gmail.com");
			user.setPassword(passwordEncoder.encode("password"));
			ArrayList<Role> roles = new ArrayList<>();
			roles.add(new Role(RoleEnum.ADMIN));
			roles.add(new Role(RoleEnum.USER));
			user.setRoles(roles);
			user.setEnabled(true);
			userRepository.save(user);
			Video video1 = new Video("Flinstones","https://www.youtube.com/watch?v=zua831utwMM");
			video1.setUser(user);
			video1.setVotes(1);
			Video video2 = new Video("Vulfpeck - 1612","https://www.youtube.com/watch?v=jRHQPG1xd9o");
			video2.setUser(user);
			video2.setVotes(1);
			Video video3 = new Video("What happens if youre allergic to water?","https://www.youtube.com/watch?v=8aUVN523r_A");
			video3.setUser(user);
			video3.setVotes(1);
			Video video4 = new Video("Rayna meets a robot","https://www.youtube.com/watch?v=h1E-FlguwGw");
			video4.setVotes(1);
			video4.setUser(user);
			Video video5 = new Video("Reggae Shark","https://www.youtube.com/watch?v=A3ytTKZf344");
			video5.setVotes(1);
			video5.setUser(user);
			videoRepository.save(video1);
			videoRepository.save(video2);
			videoRepository.save(video3);
			videoRepository.save(video4);
			videoRepository.save(video5);
		};

	}
}
