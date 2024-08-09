import React, { Suspense, useEffect } from 'react'
import { BrowserRouter as Router, Route, Routes, Navigate } from 'react-router-dom'
import { useSelector } from 'react-redux'

import { CSpinner, useColorModes } from '@coreui/react'
import './scss/style.scss'

// Containers
const DefaultLayout = React.lazy(() => import('./layout/DefaultLayout'))
const Homepage = React.lazy(() => import('./components/Homepage'))
const Login = React.lazy(() => import('./components/Login'))
const Register = React.lazy(() => import('./components/Register'))
const Page404 = React.lazy(() => import('./components/Page404'))
const Page500 = React.lazy(() => import('./components/Page500'))
const Dashboard = React.lazy(() => import('./components/Dashborad'));
const Resource = React.lazy(() => import('./components/Resource'));
const Delivery = React.lazy(() => import('./components/Delivery'));
const Projects = React.lazy(() => import('./components/Project'));
const ProjectMenu = React.lazy(() => import('./components/ProjectMenu'));
const ProjectDetail = React.lazy(() => import('./components/ProjectDetail'));
const ProjectDesign = React.lazy(() => import('./components/ProjectDesgin'));
const ProjectPrint = React.lazy(() => import('./components/ProjectPrint'));
const ProjectDelivery = React.lazy(() => import('./components/ProjectDelivery'));
const Employee = React.lazy(() => import('./components/Employee'));
const Customer = React.lazy(() => import('./components/Customer'));
const Team = React.lazy(() => import('./components//Team'));

const App = () => {
  const { isColorModeSet, setColorMode } = useColorModes('coreui-free-react-admin-template-theme')
  const storedTheme = useSelector((state) => state.theme)

  useEffect(() => {
    const urlParams = new URLSearchParams(window.location.href.split('?')[1])
    const theme = urlParams.get('theme') && urlParams.get('theme').match(/^[A-Za-z0-9\s]+/)[0]
    if (theme) {
      setColorMode(theme)
    }

    if (isColorModeSet()) {
      return
    }

    setColorMode(storedTheme)
  }, [])

  // Protected Route Component
  const ProtectedRoute = ({ children }) => {
    // Lấy token từ cookie
    const token = document.cookie.split('; ').find(row => row.startsWith('token='));

    // Kiểm tra nếu token không tồn tại hoặc không hợp lệ
    if (!token || token.split('=')[1] === '') {
      alert("b ko co quyen truy cap hoac chua dang nhap")
      return <Navigate to="/login" />; // Chuyển hướng đến trang login nếu không có token
    }

    // Token tồn tại, cho phép truy cập vào component con
    return children;
  };

  return (
    <Router>
      <Suspense
        fallback={
          <div className="pt-3 text-center">
            <CSpinner color="primary" variant="grow" />
          </div>
        }
      >
        <Routes>
          <Route path="/" element={<Homepage />} />
          <Route path="/menu" element={<ProtectedRoute><DefaultLayout /></ProtectedRoute>}>
            <Route path="dashboard" element={<Dashboard />} />
            <Route path="resource" element={<Resource />} />
            <Route path="delivery" element={<Delivery />} />
            <Route path="projects" element={<Projects />} />
            <Route path="projects/:projectId" element={<ProjectMenu />} >
              <Route index element={<ProjectDetail />} />
              <Route path="detail" element={<ProjectDetail />} />
              <Route path="design" element={<ProjectDesign />} />
              <Route path="shipping" element={<ProjectDelivery />} />
              <Route path="print" element={<ProjectPrint />} />
            </Route>
            <Route path="employee" element={<Employee />} />
            <Route path="customer" element={<Customer />} />
            <Route path="team" element={<Team />} />
          </Route>
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />
          <Route path="/404" element={<Page404 />} />
          <Route path="/500" element={<Page500 />} />
        </Routes>
      </Suspense>
    </Router>
  )
}

export default App
