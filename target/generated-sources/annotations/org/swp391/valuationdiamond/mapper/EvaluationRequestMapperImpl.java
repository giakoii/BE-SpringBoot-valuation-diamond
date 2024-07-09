package org.swp391.valuationdiamond.mapper;

import javax.annotation.processing.Generated;
import org.swp391.valuationdiamond.dto.EvaluationRequestDTO;
import org.swp391.valuationdiamond.entity.primary.EvaluationRequest;
import org.swp391.valuationdiamond.entity.primary.User;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-07-09T20:25:27+0700",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.10 (Oracle Corporation)"
)
public class EvaluationRequestMapperImpl implements EvaluationRequestMapper {

    @Override
    public EvaluationRequestDTO toDTO(EvaluationRequest evaluationRequest) {
        if ( evaluationRequest == null ) {
            return null;
        }

        EvaluationRequestDTO.EvaluationRequestDTOBuilder evaluationRequestDTO = EvaluationRequestDTO.builder();

        evaluationRequestDTO.userId( evaluationRequestUserIdUserId( evaluationRequest ) );
        evaluationRequestDTO.requestId( evaluationRequest.getRequestId() );
        evaluationRequestDTO.requestDescription( evaluationRequest.getRequestDescription() );
        evaluationRequestDTO.requestDate( evaluationRequest.getRequestDate() );
        evaluationRequestDTO.requestEmail( evaluationRequest.getRequestEmail() );
        evaluationRequestDTO.guestName( evaluationRequest.getGuestName() );
        evaluationRequestDTO.status( evaluationRequest.getStatus() );
        evaluationRequestDTO.service( evaluationRequest.getService() );
        evaluationRequestDTO.phoneNumber( evaluationRequest.getPhoneNumber() );
        evaluationRequestDTO.meetingDate( evaluationRequest.getMeetingDate() );

        return evaluationRequestDTO.build();
    }

    @Override
    public EvaluationRequest toEntity(EvaluationRequestDTO evaluationRequestDTO) {
        if ( evaluationRequestDTO == null ) {
            return null;
        }

        EvaluationRequest.EvaluationRequestBuilder evaluationRequest = EvaluationRequest.builder();

        evaluationRequest.userId( evaluationRequestDTOToUser( evaluationRequestDTO ) );
        evaluationRequest.requestId( evaluationRequestDTO.getRequestId() );
        evaluationRequest.requestDescription( evaluationRequestDTO.getRequestDescription() );
        evaluationRequest.requestDate( evaluationRequestDTO.getRequestDate() );
        evaluationRequest.requestEmail( evaluationRequestDTO.getRequestEmail() );
        evaluationRequest.guestName( evaluationRequestDTO.getGuestName() );
        evaluationRequest.status( evaluationRequestDTO.getStatus() );
        evaluationRequest.service( evaluationRequestDTO.getService() );
        evaluationRequest.phoneNumber( evaluationRequestDTO.getPhoneNumber() );
        evaluationRequest.meetingDate( evaluationRequestDTO.getMeetingDate() );

        return evaluationRequest.build();
    }

    private String evaluationRequestUserIdUserId(EvaluationRequest evaluationRequest) {
        if ( evaluationRequest == null ) {
            return null;
        }
        User userId = evaluationRequest.getUserId();
        if ( userId == null ) {
            return null;
        }
        String userId1 = userId.getUserId();
        if ( userId1 == null ) {
            return null;
        }
        return userId1;
    }

    protected User evaluationRequestDTOToUser(EvaluationRequestDTO evaluationRequestDTO) {
        if ( evaluationRequestDTO == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.userId( evaluationRequestDTO.getUserId() );

        return user.build();
    }
}
