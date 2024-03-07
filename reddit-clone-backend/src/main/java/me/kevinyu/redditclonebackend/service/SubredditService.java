package me.kevinyu.redditclonebackend.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kevinyu.redditclonebackend.dto.SubredditDto;
import me.kevinyu.redditclonebackend.exception.RedditException;
import me.kevinyu.redditclonebackend.mapper.SubredditMapper;
import me.kevinyu.redditclonebackend.model.Subreddit;
import me.kevinyu.redditclonebackend.repository.SubredditRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class SubredditService {
    private final SubredditRepository subredditRepository;
    private final SubredditMapper subredditMapper;
    @Transactional
    public SubredditDto save(SubredditDto subredditDto){
        Subreddit save = subredditRepository.save(subredditMapper.mapDtoToSubreddit(subredditDto));
        subredditDto.setId(save.getId());
        return subredditDto;
    }
    @Transactional(readOnly = true)
    public List<SubredditDto> getAllSubreddits(){
        return subredditRepository.findAll()
                .stream()
                .map(subredditMapper::mapSubredditToDto)
                .toList();
    }

    public SubredditDto getSubreddit(Long id) {
        Subreddit subreddit = subredditRepository.findById(id)
                .orElseThrow(() -> new RedditException("No subreddit found with ID - " + id));
        return subredditMapper.mapSubredditToDto(subreddit);
    }

}
