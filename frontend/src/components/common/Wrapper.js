import React from 'react';
import { motion } from 'framer-motion';
import { pageEffect } from './pageEffect';


const Wrapper = {
  initial: {
    opacity: 0,
    x: '-100%',
  },
  animate: {
    opacity: 1,
    x: '0%',
  },
  exit: {
    opacity: 0,
    x: '100%',
  },
};


export default Wrapper;