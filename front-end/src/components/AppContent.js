import React, { Suspense } from 'react';
import { Navigate, Route, Routes } from 'react-router-dom';
import { CContainer, CSpinner } from '@coreui/react';

// Import the components
const Dashboard = React.lazy(() => import('./Dashborad'));
const Resource = React.lazy(() => import('./Resource'));
const Delivery = React.lazy(() => import('./Delivery'));
const Projects = React.lazy(() => import('./Project'));
const Employee = React.lazy(() => import('./Employee'));
const Customer = React.lazy(() => import('./Customer'));
const Team = React.lazy(() => import('./Team'));

const AppContent = () => {
  return (
    <CContainer lg>
      <Suspense fallback={<CSpinner color="primary" />}>
        <Routes>
          <Route exact path="home/dashboard" element={<Dashboard />} />
          <Route exact path="/resource" element={<Resource />} />
          <Route exact path="/delivery" element={<Delivery />} />
          <Route exact path="/projects" element={<Projects />} />
          <Route exact path="/employee" element={<Employee />} />
          <Route exact path="/customer" element={<Customer />} />
          <Route exact path="/team" element={<Team />} />
        </Routes>
      </Suspense>
    </CContainer>
  );
};

export default AppContent;
