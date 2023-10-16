package codesquad.gaemimarble.user.dto;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import codesquad.gaemimarble.user.dto.request.UserSignUpRequest;
import codesquad.gaemimarble.user.entity.User;

@Mapper
public interface UserMapper {
	UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

	User toUser(UserSignUpRequest userSignUpRequest);
}
