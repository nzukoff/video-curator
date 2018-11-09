package com.zukoff.repositories;

import com.zukoff.entities.Comment;
import com.zukoff.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ICommentRepository extends CrudRepository<Comment,Integer> {
    @Query(value = "SELECT COUNT(*) FROM comments WHERE video_id = :video_id", nativeQuery = true)
    public int countCommentsFromVideo(@Param("video_id") int video_id);

    @Query(value = "SELECT COUNT(*) FROM comments WHERE user_id = :user_id", nativeQuery = true)
    public int countCommentsFromUser(@Param("user_id") int user_id);

    Comment findOneById(int id);
//    void delete(int id);
}
