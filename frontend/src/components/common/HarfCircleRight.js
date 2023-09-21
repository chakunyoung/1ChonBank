import React from 'react';

const HalfCircleRight = ({ color }) => {
    const containerStyle = {
        alignItems: 'center',  // 상하 중앙 정렬
        width: '500px',  // 외부 div 너비
        position: 'absolute',
    };

    const halfCircleStyle = {
        position: 'relative',  // 위치를 상대적으로 지정
        height: '180px',
        width: '110px',
        borderRadius: '0px 180px 180px 0',
        background: color || '#E9E9FF',
    };

    return (
        <div style={containerStyle}>
            <div style={halfCircleStyle} />
        </div>
    );
};

export default HalfCircleRight;
