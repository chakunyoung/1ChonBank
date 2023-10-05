import React from 'react';
import { Link } from 'react-router-dom';
import './MissionList.css';
import Profile from 'components/common/Profile';
import Footer from 'components/common/Footer';
import MissionCard from "components/common/MissionCard";
import { IoIosAdd } from "react-icons/io";
import MissionIcon from "assets/missionicon.png";
import { useSelector } from 'react-redux';
import Wrapper from 'components/common/Wrapper';
import { motion } from "framer-motion";

const MissionList = () => {

  const role = useSelector((state) => state.auth.user.roles);

  return (
    <motion.div
    initial={Wrapper.initial}
    animate={Wrapper.animate}
    exit={Wrapper.exit}
    >
    <div className='missionlist-container'>
      <Profile />
      <div className='mission-title'>
        미션 목록
      </div>
      <div className='mission-card-list'>
        <MissionCard />
      </div>
      
      {role === 'ROLE_PARENT' && (
        <Link to="/mission" className="make-mission">

          <span className="button-text">미션 추가</span>
          <IoIosAdd style={{ fontSize: '40px' }} />
        </Link>)
      }
      <div className='missionlist-footer'>
        <Footer />
      </div>
    </div>
    </motion.div>
  );
};

export default MissionList;