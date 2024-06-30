package org.swp391.valuationdiamond.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.swp391.valuationdiamond.dto.EvaluationRequestDTO;
import org.swp391.valuationdiamond.entity.primary.EvaluationRequest;
import org.swp391.valuationdiamond.entity.primary.User;

@Mapper
public interface EvaluationRequestMapper {

  EvaluationRequestMapper INSTANCE = Mappers.getMapper(EvaluationRequestMapper.class);

  @Mapping(source = "userId.userId", target = "userId")
  EvaluationRequestDTO toDTO(EvaluationRequest evaluationRequest);

  @Mapping(source = "userId", target = "userId.userId")
  EvaluationRequest toEntity(EvaluationRequestDTO evaluationRequestDTO);

  default String mapUser(User user) {
    return user == null ? null : user.getUserId();
  }

  default User mapUserId(String userId) {
    if (userId == null) {
      return null;
    }
    User user = new User();
    user.setUserId(userId);
    return user;
  }
}
