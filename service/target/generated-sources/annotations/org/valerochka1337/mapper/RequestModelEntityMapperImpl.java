package org.valerochka1337.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import org.valerochka1337.entity.Request;
import org.valerochka1337.entity.Request.RequestBuilder;
import org.valerochka1337.model.RequestModel;
import org.valerochka1337.model.RequestModel.RequestModelBuilder;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-06T15:39:43+0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.23 (Amazon.com Inc.)"
)
@Component
public class RequestModelEntityMapperImpl implements RequestModelEntityMapper {

    @Override
    public RequestModel toModel(Request entity) {
        if ( entity == null ) {
            return null;
        }

        RequestModelBuilder requestModel = RequestModel.builder();

        requestModel.author( mapUserToModel( entity.getAuthor() ) );
        requestModel.id( entity.getId() );
        requestModel.status( entity.getStatus() );
        requestModel.creationDate( entity.getCreationDate() );
        requestModel.sentDate( entity.getSentDate() );
        requestModel.message( entity.getMessage() );

        return requestModel.build();
    }

    @Override
    public Request toEntity(RequestModel model) {
        if ( model == null ) {
            return null;
        }

        RequestBuilder request = Request.builder();

        request.author( mapUserToEntity( model.getAuthor() ) );
        request.id( model.getId() );
        request.status( model.getStatus() );
        request.creationDate( model.getCreationDate() );
        request.sentDate( model.getSentDate() );
        request.message( model.getMessage() );

        return request.build();
    }
}
