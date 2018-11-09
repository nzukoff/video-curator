package com.zukoff.repositories;

import com.zukoff.entities.User;
import com.zukoff.entities.Video;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface IVideoRepository extends CrudRepository<Video,Integer> {
    @Query(value = "SELECT COUNT(*) FROM videos WHERE user_id = :user_id", nativeQuery = true)
    public int countVideos(@Param("user_id") int user_id);
    Video findOneById(int id);
    Video deleteById(int id);
}
