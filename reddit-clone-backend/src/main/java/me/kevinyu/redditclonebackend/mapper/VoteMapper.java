package me.kevinyu.redditclonebackend.mapper;

import me.kevinyu.redditclonebackend.dto.VoteDto;
import me.kevinyu.redditclonebackend.model.Post;
import me.kevinyu.redditclonebackend.model.User;
import me.kevinyu.redditclonebackend.model.Vote;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface VoteMapper {
    @Mapping(target = "voteType", source = "voteDto.voteType")
    @Mapping(target = "post", source = "post")
    @Mapping(target = "user", source = "user")
    public Vote map(VoteDto voteDto, Post post, User user);
}
