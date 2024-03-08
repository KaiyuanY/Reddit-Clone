package me.kevinyu.redditclonebackend.repository;

import me.kevinyu.redditclonebackend.model.Comment;
import me.kevinyu.redditclonebackend.model.Post;
import me.kevinyu.redditclonebackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);

    List<Comment> findAllByUser(User user);
}
