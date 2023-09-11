import React from 'react';
import { Transition } from 'react-transition-group';
import './styles.css'

const PageTransition = ({ in: inProp, children }) => (
  <Transition in={inProp} timeout={300}>
    {state => (
      <div className={`page page-${state}`}>
        {children}
      </div>
    )}
  </Transition>
);

export default PageTransition;
