package com.forum.postandreplyservice.repository;

import com.forum.postandreplyservice.entity.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends MongoRepository<Post, String> {
    @Query("{ 'status' : ?0 }")
    List<Post> findByStatus(String status);

    @Query("{ 'userId' : ?0 }")
    List<Post> findByUserId(Long userId);

    @Query("{ 'status' : ?0 }")
    List<Post> findByStatus(String status, Sort sort);

    @Query("{ 'userId' : ?0, 'status' : ?1 }")
    List<Post> findByUserIdAndStatus(Long userId, String status);

    @Query("{ 'userId' : ?0, 'status' : ?1 }")
    List<Post> findByUserIdAndStatus(Long userId, String status, Sort sort);

    @Query("{$and: [{_id: {$in: ?0}}, {status: ?1}]}")
    List<Post> findAllByIdAndStatus(List<String> postIds, String status);

    Optional<Post> findTopByUserIdOrderByDateCreatedDesc(Long userId);
}
