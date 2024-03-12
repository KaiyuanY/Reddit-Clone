package me.kevinyu.redditclonebackend.service;

import lombok.AllArgsConstructor;
import me.kevinyu.redditclonebackend.dto.VoteDto;
import me.kevinyu.redditclonebackend.exception.RedditException;
import me.kevinyu.redditclonebackend.model.Vote;
import me.kevinyu.redditclonebackend.exception.PostNotFoundException;
import me.kevinyu.redditclonebackend.mapper.VoteMapper;
import me.kevinyu.redditclonebackend.model.Post;
import me.kevinyu.redditclonebackend.model.VoteType;
import me.kevinyu.redditclonebackend.repository.PostRepository;
import me.kevinyu.redditclonebackend.repository.VoteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;
    private final PostRepository postRepository;
    private final AuthService authService;
    private final VoteMapper voteMapper;

    @Transactional
    public void vote(VoteDto voteDto){
        Post post = postRepository.findById(voteDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException("Post not found with ID - " + voteDto.getPostId()));
        Optional<Vote> voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post, authService.getCurrentUser());
        if (voteByPostAndUser.isPresent() && voteByPostAndUser.get().getVoteType().equals(voteDto.getVoteType())){
            throw new RedditException("You have already " + voteDto.getVoteType() + "ed for this post.");
        }
        if(voteDto.getVoteType().equals(VoteType.UPVOTE)){
            post.setVoteCount(post.getVoteCount() + 1);
        }
        else {
            post.setVoteCount(post.getVoteCount() - 1);
        }
        voteRepository.save(voteMapper.map(voteDto, post, authService.getCurrentUser()));
        postRepository.save(post);
    }
}
