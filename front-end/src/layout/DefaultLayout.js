import React from 'react'
import AppSidebar from '../components/AppSidebar';
import AppFooter from '../components/AppFooter';
import AppHeader from '../components/AppHeader';
import AppContent from '../components/AppContent';
import { Outlet } from 'react-router-dom'

const DefaultLayout = () => {
  return (
    <div>
      <AppSidebar />
      <div className="wrapper d-flex flex-column min-vh-100">
        <AppHeader />
        <div className="body flex-grow-1">
          <Outlet/>
        </div>
        <AppFooter />
      </div>
    </div>
  )
}

export default DefaultLayout
