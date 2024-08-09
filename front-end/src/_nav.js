import React from 'react'
import CIcon from '@coreui/icons-react'
import { cilCalculator, cilSatelite, cilDrop, cilPencil, cilBarChart, cilUser, cilPeople } from '@coreui/icons'
import { CNavItem } from '@coreui/react'

const _nav = [
  {
    component: CNavItem,
    name: 'Thống kê',
    to: 'dashboard',
    icon: <CIcon icon={cilBarChart} customClassName="nav-icon" />,
  },
  {
    component: CNavItem,
    name: 'Quản lí kho',
    to: 'resource',
    icon: <CIcon icon={cilDrop} customClassName="nav-icon" />,
  },
  {
    component: CNavItem,
    name: 'Quản lý giao hàng',
    to: 'delivery',
    icon: <CIcon icon={cilPencil} customClassName="nav-icon" />,
  },
  {
    component: CNavItem,
    name: 'Quản lý dự án',
    to: 'projects',
    icon: <CIcon icon={cilSatelite} customClassName="nav-icon" />,
  },
  {
    component: CNavItem,
    name: 'Trang chủ',
    to: '/menu',
    icon: <CIcon icon={cilCalculator} customClassName="nav-icon" />,
  },
  {
    component: CNavItem,
    name: 'Quản lý nhân viên',
    to: 'employee',
    icon: <CIcon icon={cilPeople} customClassName="nav-icon" />,
  },
  {
    component: CNavItem,
    name: 'Quản lý khách hàng',
    to: 'customer',
    icon: <CIcon icon={cilCalculator} customClassName="nav-icon" />,
  },
  {
    component: CNavItem,
    name: 'Quản lý phòng ban',
    to: 'team',
    icon: <CIcon icon={cilCalculator} customClassName="nav-icon" />,
  },
]

export default _nav
