package com.woowahanbank.backend.domain.family.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFamily is a Querydsl query type for Family
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFamily extends EntityPathBase<Family> {

    private static final long serialVersionUID = 976343267L;

    public static final QFamily family = new QFamily("family");

    public final StringPath familyName = createString("familyName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<com.woowahanbank.backend.domain.user.domain.User, com.woowahanbank.backend.domain.user.domain.QUser> users = this.<com.woowahanbank.backend.domain.user.domain.User, com.woowahanbank.backend.domain.user.domain.QUser>createList("users", com.woowahanbank.backend.domain.user.domain.User.class, com.woowahanbank.backend.domain.user.domain.QUser.class, PathInits.DIRECT2);

    public QFamily(String variable) {
        super(Family.class, forVariable(variable));
    }

    public QFamily(Path<? extends Family> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFamily(PathMetadata metadata) {
        super(Family.class, metadata);
    }

}

