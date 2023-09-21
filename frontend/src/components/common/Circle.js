import React from 'react';

const QuarterCircle = ({ color }) => {
    const circleContainerStyle = {
        alignItems: 'center',  // 상하 중앙 정렬
        width: '500px',  // 외부 div 너비
        position: 'relative', // 상위 요소에 대해 상대적인 위치 설정
    };

    const style = {
        width: '170px',
        height: '170px',
        background: color || '#E9E9FF',
        borderRadius: '0 0 0 170px',
        position: 'absolute',
        top: '0',  // 상단에 위치
        right: '0',
    };

    return (
        <div style={circleContainerStyle}>
            <div style={style} />
        </div>
    );
};

export default QuarterCircle;
