package com.woowahanbank.backend.domain.user.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = -1856158031L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUser user = new QUser("user");

    public final NumberPath<Long> age = createNumber("age", Long.class);

    public final StringPath email = createString("email");

    public final com.woowahanbank.backend.domain.family.domain.QFamily family;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> money = createNumber("money", Long.class);

    public final StringPath nickname = createString("nickname");

    public final StringPath password = createString("password");

    public final NumberPath<Long> quiz = createNumber("quiz", Long.class);

    public final StringPath roles = createString("roles");

    public final NumberPath<Long> score = createNumber("score", Long.class);

    public final EnumPath<UserType> type = createEnum("type", UserType.class);

    public final StringPath userId = createString("userId");

    public QUser(String variable) {
        this(User.class, forVariable(variable), INITS);
    }

    public QUser(Path<? extends User> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUser(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUser(PathMetadata metadata, PathInits inits) {
        this(User.class, metadata, inits);
    }

    public QUser(Class<? extends User> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.family = inits.isInitialized("family") ? new com.woowahanbank.backend.domain.family.domain.QFamily(forProperty("family")) : null;
    }

}

