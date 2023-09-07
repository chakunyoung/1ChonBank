package com.woowahanbank.backend.domain.financialproducts.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLoan is a Querydsl query type for Loan
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLoan extends EntityPathBase<Loan> {

    private static final long serialVersionUID = 1232933210L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLoan loan = new QLoan("loan");

    public final com.woowahanbank.backend.domain.family.domain.QFamily family;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath info = createString("info");

    public final StringPath name = createString("name");

    public final NumberPath<Integer> period = createNumber("period", Integer.class);

    public final NumberPath<Integer> rate = createNumber("rate", Integer.class);

    public QLoan(String variable) {
        this(Loan.class, forVariable(variable), INITS);
    }

    public QLoan(Path<? extends Loan> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLoan(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLoan(PathMetadata metadata, PathInits inits) {
        this(Loan.class, metadata, inits);
    }

    public QLoan(Class<? extends Loan> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.family = inits.isInitialized("family") ? new com.woowahanbank.backend.domain.family.domain.QFamily(forProperty("family")) : null;
    }

}

