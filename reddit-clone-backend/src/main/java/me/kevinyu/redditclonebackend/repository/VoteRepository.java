package me.kevinyu.redditclonebackend.repository;

import me.kevinyu.redditclonebackend.model.Post;
import me.kevinyu.redditclonebackend.model.User;
import me.kevinyu.redditclonebackend.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User currentUser);
}
