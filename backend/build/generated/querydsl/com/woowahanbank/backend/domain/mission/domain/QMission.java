package com.woowahanbank.backend.domain.mission.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMission is a Querydsl query type for Mission
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMission extends EntityPathBase<Mission> {

    private static final long serialVersionUID = 1035421987L;

    public static final QMission mission = new QMission("mission");

    public final NumberPath<Long> missionChildId = createNumber("missionChildId", Long.class);

    public final StringPath missionDescription = createString("missionDescription");

    public final NumberPath<Long> missionFamilyId = createNumber("missionFamilyId", Long.class);

    public final NumberPath<Long> missionId = createNumber("missionId", Long.class);

    public final StringPath missionName = createString("missionName");

    public final NumberPath<Integer> missionPoint = createNumber("missionPoint", Integer.class);

    public final StringPath missionStatus = createString("missionStatus");

    public final DateTimePath<java.util.Date> missionTerminateDate = createDateTime("missionTerminateDate", java.util.Date.class);

    public QMission(String variable) {
        super(Mission.class, forVariable(variable));
    }

    public QMission(Path<? extends Mission> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMission(PathMetadata metadata) {
        super(Mission.class, metadata);
    }

}

