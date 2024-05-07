package org.valerochka1337.mapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.valerochka1337.entity.Request;
import org.valerochka1337.entity.Role;
import org.valerochka1337.entity.User;
import org.valerochka1337.entity.User.UserBuilder;
import org.valerochka1337.model.RequestModel;
import org.valerochka1337.model.UserModel;
import org.valerochka1337.model.UserModel.UserModelBuilder;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-07T22:02:15+0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.23 (Amazon.com Inc.)"
)
@Component
public class UserModelEntityMapperImpl implements UserModelEntityMapper {

    @Autowired
    private RequestModelEntityMapper requestModelEntityMapper;

    @Override
    public UserModel toModel(User entity) {
        if ( entity == null ) {
            return null;
        }

        UserModelBuilder userModel = UserModel.builder();

        userModel.id( entity.getId() );
        userModel.username( entity.getUsername() );
        userModel.password( entity.getPassword() );
        Set<Role> set = entity.getRoles();
        if ( set != null ) {
            userModel.roles( new HashSet<Role>( set ) );
        }
        userModel.requests( requestSetToRequestModelList( entity.getRequests() ) );

        return userModel.build();
    }

    @Override
    public User toEntity(UserModel model) {
        if ( model == null ) {
            return null;
        }

        UserBuilder user = User.builder();

        user.id( model.getId() );
        user.username( model.getUsername() );
        user.password( model.getPassword() );
        Set<Role> set = model.getRoles();
        if ( set != null ) {
            user.roles( new HashSet<Role>( set ) );
        }
        user.requests( requestModelListToRequestSet( model.getRequests() ) );

        return user.build();
    }

    protected List<RequestModel> requestSetToRequestModelList(Set<Request> set) {
        if ( set == null ) {
            return null;
        }

        List<RequestModel> list = new ArrayList<RequestModel>( set.size() );
        for ( Request request : set ) {
            list.add( requestModelEntityMapper.toModel( request ) );
        }

        return list;
    }

    protected Set<Request> requestModelListToRequestSet(List<RequestModel> list) {
        if ( list == null ) {
            return null;
        }

        Set<Request> set = new HashSet<Request>( Math.max( (int) ( list.size() / .75f ) + 1, 16 ) );
        for ( RequestModel requestModel : list ) {
            set.add( requestModelEntityMapper.toEntity( requestModel ) );
        }

        return set;
    }
}
