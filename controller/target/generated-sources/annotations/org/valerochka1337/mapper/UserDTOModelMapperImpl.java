package org.valerochka1337.mapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.valerochka1337.dto.RequestDTO;
import org.valerochka1337.dto.RequestDTO.RequestDTOBuilder;
import org.valerochka1337.dto.UserDTO;
import org.valerochka1337.dto.UserDTO.UserDTOBuilder;
import org.valerochka1337.entity.Request;
import org.valerochka1337.entity.Request.RequestBuilder;
import org.valerochka1337.entity.Status;
import org.valerochka1337.entity.User;
import org.valerochka1337.entity.User.UserBuilder;
import org.valerochka1337.model.RequestModel;
import org.valerochka1337.model.UserModel;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-06T15:39:43+0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.23 (Amazon.com Inc.)"
)
public class UserDTOModelMapperImpl implements UserDTOModelMapper {

    @Override
    public UserDTO toDTO(UserModel model) {
        if ( model == null ) {
            return null;
        }

        UserDTOBuilder userDTO = UserDTO.builder();

        userDTO.roles( mapRolesToDTO( model.getRoles() ) );
        userDTO.id( model.getId() );
        userDTO.username( model.getUsername() );
        userDTO.firstName( model.getFirstName() );
        userDTO.lastName( model.getLastName() );
        userDTO.requests( requestModelListToRequestDTOList( model.getRequests() ) );

        return userDTO.build();
    }

    @Override
    public User toEntity(UserDTO dto) {
        if ( dto == null ) {
            return null;
        }

        UserBuilder user = User.builder();

        user.roles( mapRolesToModel( dto.getRoles() ) );
        user.id( dto.getId() );
        user.username( dto.getUsername() );
        user.firstName( dto.getFirstName() );
        user.lastName( dto.getLastName() );
        user.requests( requestDTOListToRequestSet( dto.getRequests() ) );

        return user.build();
    }

    protected RequestDTO requestModelToRequestDTO(RequestModel requestModel) {
        if ( requestModel == null ) {
            return null;
        }

        RequestDTOBuilder requestDTO = RequestDTO.builder();

        requestDTO.id( requestModel.getId() );
        if ( requestModel.getStatus() != null ) {
            requestDTO.status( requestModel.getStatus().name() );
        }
        if ( requestModel.getCreationDate() != null ) {
            requestDTO.creationDate( DateTimeFormatter.ISO_LOCAL_DATE_TIME.format( requestModel.getCreationDate() ) );
        }
        if ( requestModel.getSentDate() != null ) {
            requestDTO.sentDate( DateTimeFormatter.ISO_LOCAL_DATE_TIME.format( requestModel.getSentDate() ) );
        }
        requestDTO.message( requestModel.getMessage() );
        requestDTO.author( toDTO( requestModel.getAuthor() ) );

        return requestDTO.build();
    }

    protected List<RequestDTO> requestModelListToRequestDTOList(List<RequestModel> list) {
        if ( list == null ) {
            return null;
        }

        List<RequestDTO> list1 = new ArrayList<RequestDTO>( list.size() );
        for ( RequestModel requestModel : list ) {
            list1.add( requestModelToRequestDTO( requestModel ) );
        }

        return list1;
    }

    protected Request requestDTOToRequest(RequestDTO requestDTO) {
        if ( requestDTO == null ) {
            return null;
        }

        RequestBuilder request = Request.builder();

        request.id( requestDTO.getId() );
        if ( requestDTO.getStatus() != null ) {
            request.status( Enum.valueOf( Status.class, requestDTO.getStatus() ) );
        }
        if ( requestDTO.getCreationDate() != null ) {
            request.creationDate( LocalDateTime.parse( requestDTO.getCreationDate() ) );
        }
        if ( requestDTO.getSentDate() != null ) {
            request.sentDate( LocalDateTime.parse( requestDTO.getSentDate() ) );
        }
        request.message( requestDTO.getMessage() );
        request.author( toEntity( requestDTO.getAuthor() ) );

        return request.build();
    }

    protected Set<Request> requestDTOListToRequestSet(List<RequestDTO> list) {
        if ( list == null ) {
            return null;
        }

        Set<Request> set = new HashSet<Request>( Math.max( (int) ( list.size() / .75f ) + 1, 16 ) );
        for ( RequestDTO requestDTO : list ) {
            set.add( requestDTOToRequest( requestDTO ) );
        }

        return set;
    }
}
