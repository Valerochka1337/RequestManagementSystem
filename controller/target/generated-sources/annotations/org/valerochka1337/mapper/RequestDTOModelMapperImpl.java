package org.valerochka1337.mapper;

import java.time.format.DateTimeFormatter;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import org.valerochka1337.dto.RequestDTO;
import org.valerochka1337.dto.RequestDTO.RequestDTOBuilder;
import org.valerochka1337.model.RequestModel;
import org.valerochka1337.model.RequestModel.RequestModelBuilder;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-06T15:44:09+0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.23 (Amazon.com Inc.)"
)
@Component
public class RequestDTOModelMapperImpl implements RequestDTOModelMapper {

    @Override
    public RequestDTO toDTO(RequestModel model) {
        if ( model == null ) {
            return null;
        }

        RequestDTOBuilder requestDTO = RequestDTO.builder();

        if ( model.getCreationDate() != null ) {
            requestDTO.creationDate( DateTimeFormatter.ofPattern( "yyyy-MM-dd HH:mm" ).format( model.getCreationDate() ) );
        }
        if ( model.getSentDate() != null ) {
            requestDTO.sentDate( DateTimeFormatter.ofPattern( "yyyy-MM-dd HH:mm" ).format( model.getSentDate() ) );
        }
        requestDTO.author( mapUserToDTO( model.getAuthor() ) );
        requestDTO.id( model.getId() );
        if ( model.getStatus() != null ) {
            requestDTO.status( model.getStatus().name() );
        }
        requestDTO.message( model.getMessage() );

        return requestDTO.build();
    }

    @Override
    public RequestModel toModel(RequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        RequestModelBuilder requestModel = RequestModel.builder();

        requestModel.creationDate( mapStringToLocalDate( dto.getCreationDate() ) );
        requestModel.sentDate( mapStringToLocalDate( dto.getSentDate() ) );
        requestModel.status( mapStringToStatus( dto.getStatus() ) );
        requestModel.author( mapUserToEntity( dto.getAuthor() ) );
        requestModel.id( dto.getId() );
        requestModel.message( dto.getMessage() );

        return requestModel.build();
    }
}
