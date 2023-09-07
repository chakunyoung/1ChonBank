package com.woowahanbank.backend.domain.financialproducts.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSavings is a Querydsl query type for Savings
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSavings extends EntityPathBase<Savings> {

    private static final long serialVersionUID = 1289048079L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSavings savings = new QSavings("savings");

    public final com.woowahanbank.backend.domain.family.domain.QFamily family;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath info = createString("info");

    public final StringPath name = createString("name");

    public final NumberPath<Integer> period = createNumber("period", Integer.class);

    public final NumberPath<Integer> rate = createNumber("rate", Integer.class);

    public QSavings(String variable) {
        this(Savings.class, forVariable(variable), INITS);
    }

    public QSavings(Path<? extends Savings> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSavings(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSavings(PathMetadata metadata, PathInits inits) {
        this(Savings.class, metadata, inits);
    }

    public QSavings(Class<? extends Savings> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.family = inits.isInitialized("family") ? new com.woowahanbank.backend.domain.family.domain.QFamily(forProperty("family")) : null;
    }

}

