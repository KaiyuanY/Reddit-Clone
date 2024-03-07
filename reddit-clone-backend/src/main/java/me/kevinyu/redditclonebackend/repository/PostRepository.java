package me.kevinyu.redditclonebackend.repository;

import me.kevinyu.redditclonebackend.model.Post;
import me.kevinyu.redditclonebackend.model.Subreddit;
import me.kevinyu.redditclonebackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllBySubreddit(Subreddit subreddit);
    List<Post> findByUser(User user);
}
