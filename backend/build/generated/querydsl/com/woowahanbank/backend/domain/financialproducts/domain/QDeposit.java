package com.woowahanbank.backend.domain.financialproducts.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDeposit is a Querydsl query type for Deposit
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDeposit extends EntityPathBase<Deposit> {

    private static final long serialVersionUID = 970553844L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDeposit deposit = new QDeposit("deposit");

    public final com.woowahanbank.backend.domain.family.domain.QFamily family;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath info = createString("info");

    public final StringPath name = createString("name");

    public final NumberPath<Integer> period = createNumber("period", Integer.class);

    public final NumberPath<Integer> rate = createNumber("rate", Integer.class);

    public QDeposit(String variable) {
        this(Deposit.class, forVariable(variable), INITS);
    }

    public QDeposit(Path<? extends Deposit> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDeposit(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDeposit(PathMetadata metadata, PathInits inits) {
        this(Deposit.class, metadata, inits);
    }

    public QDeposit(Class<? extends Deposit> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.family = inits.isInitialized("family") ? new com.woowahanbank.backend.domain.family.domain.QFamily(forProperty("family")) : null;
    }

}

