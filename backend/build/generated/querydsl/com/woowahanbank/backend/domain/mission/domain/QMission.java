package com.woowahanbank.backend.domain.mission.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMission is a Querydsl query type for Mission
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMission extends EntityPathBase<Mission> {

    private static final long serialVersionUID = 1035421987L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMission mission = new QMission("mission");

    public final com.woowahanbank.backend.domain.user.domain.QUser child;

    public final com.woowahanbank.backend.domain.family.domain.QFamily family;

    public final StringPath missionDescription = createString("missionDescription");

    public final NumberPath<Long> missionId = createNumber("missionId", Long.class);

    public final StringPath missionName = createString("missionName");

    public final NumberPath<Integer> missionPoint = createNumber("missionPoint", Integer.class);

    public final StringPath missionStatus = createString("missionStatus");

    public final DateTimePath<java.util.Date> missionTerminateDate = createDateTime("missionTerminateDate", java.util.Date.class);

    public final com.woowahanbank.backend.domain.user.domain.QUser parent;

    public QMission(String variable) {
        this(Mission.class, forVariable(variable), INITS);
    }

    public QMission(Path<? extends Mission> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMission(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMission(PathMetadata metadata, PathInits inits) {
        this(Mission.class, metadata, inits);
    }

    public QMission(Class<? extends Mission> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.child = inits.isInitialized("child") ? new com.woowahanbank.backend.domain.user.domain.QUser(forProperty("child"), inits.get("child")) : null;
        this.family = inits.isInitialized("family") ? new com.woowahanbank.backend.domain.family.domain.QFamily(forProperty("family")) : null;
        this.parent = inits.isInitialized("parent") ? new com.woowahanbank.backend.domain.user.domain.QUser(forProperty("parent"), inits.get("parent")) : null;
    }

}

