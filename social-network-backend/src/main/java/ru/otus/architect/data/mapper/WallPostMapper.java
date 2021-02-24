package ru.otus.architect.data.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.otus.architect.data.dto.wall.WallPostRequest;
import ru.otus.architect.data.dto.wall.WallPostResponse;
import ru.otus.architect.data.model.WallPost;

@Mapper
public interface WallPostMapper {

    WallPostMapper mapper = Mappers.getMapper(WallPostMapper.class);

    WallPost wallPostRequestToWallPost(WallPostRequest wallPostRequest);

    WallPostResponse wallPostToWallPostResponse(WallPost wallPost);

}
