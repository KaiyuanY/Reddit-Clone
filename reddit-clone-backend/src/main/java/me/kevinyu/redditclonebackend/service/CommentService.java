package me.kevinyu.redditclonebackend.service;

import lombok.AllArgsConstructor;
import me.kevinyu.redditclonebackend.dto.CommentDto;
import me.kevinyu.redditclonebackend.exception.PostNotFoundException;
import me.kevinyu.redditclonebackend.exception.RedditException;
import me.kevinyu.redditclonebackend.mapper.CommentMapper;
import me.kevinyu.redditclonebackend.model.Comment;
import me.kevinyu.redditclonebackend.model.NotificationEmail;
import me.kevinyu.redditclonebackend.model.Post;
import me.kevinyu.redditclonebackend.model.User;
import me.kevinyu.redditclonebackend.repository.CommentRepository;
import me.kevinyu.redditclonebackend.repository.PostRepository;
import me.kevinyu.redditclonebackend.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CommentService {
    private static final String POST_URL = "";
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;
    private final MailContentBuilder mailContentBuilder;
    private final MailService mailService;

    public void save(CommentDto commentsDto) {
        Post post = postRepository.findById(commentsDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException(commentsDto.getPostId().toString()));
        Comment comment = commentMapper.map(commentsDto, post, authService.getCurrentUser());
        commentRepository.save(comment);

        String message = mailContentBuilder.build(post.getUser().getUsername() + " posted a comment on your post." + POST_URL);
        sendCommentNotification(message, post.getUser());

    }
    private void sendCommentNotification(String message, User user) {
        mailService.sendMail(new NotificationEmail(user.getUsername() + " Commented on your post", user.getEmail(), message));
    }

    public List<CommentDto> getAllCommentsForPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId.toString()));
        return commentRepository.findByPost(post)
                .stream()
                .map(commentMapper::mapToDto).toList();
    }

    public List<CommentDto> getAllCommentsForUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return commentRepository.findAllByUser(user)
                .stream()
                .map(commentMapper::mapToDto)
                .toList();
    }

    public boolean containsSwearWords(String comment) {
        if (comment.contains("shit")) {
            throw new RedditException("Comments contains unacceptable language");
        }
        return false;
    }
}
