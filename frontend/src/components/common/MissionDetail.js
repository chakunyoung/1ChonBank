import './MissionDetail.css';
import React from 'react';
import { useParams } from 'react-router-dom';

const MissionDetail = () => {
    const { missionId } = useParams();

    return (
        <div>
            <h2>Mission ID: {missionId}</h2>
        </div>
    );
};

export default MissionDetail;
