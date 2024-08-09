import React, { useEffect, useState } from 'react'
import { useParams } from 'react-router-dom'
import { CRow, CCol, CCard, CCardBody, CCardTitle, CCardText, CContainer } from '@coreui/react'
import axios from 'axios'

const ProjectDetail = () => {
  const { projectId } = useParams()
  const [project, setProject] = useState(null)
  const [employee, setEmployee] = useState(null)
  const [customer, setCustomer] = useState(null)

  useEffect(() => {
    const fetchProject = async () => {
      try {
        const token = document.cookie.split('; ').find(row => row.startsWith('token=')).split('=')[1]
        const projectResponse = await axios.get(`http://localhost:8080/api/projects/${projectId}`, {
          headers: { 'Authorization': `Bearer ${token}` }
        })
        setProject(projectResponse.data)
        console.log(projectResponse.data);
        
        const employeeResponse = await axios.get(`http://localhost:8080/api/user/${projectResponse.data.employeeId}`, {
          headers: { 'Authorization': `Bearer ${token}` }
        })
        setEmployee(employeeResponse.data)
        const customerResponse = await axios.get(`http://localhost:8080/api/customer/${projectResponse.data.customerId}`, {
          headers: { 'Authorization': `Bearer ${token}` }
        })
        setCustomer(customerResponse.data)
      } catch (err) {
        console.error('Failed to fetch data', err)
      }
    }
    fetchProject()
  }, [projectId])

  return (
    <CContainer>
      <h2>Detail</h2>
      {project ? (
        <CRow>
          {/* Cột bên trái hiển thị thông tin project */}
          <CCol sm="6">
            <CCard>
              <CCardBody>
                <CCardTitle>{project.projectName}</CCardTitle>
                <CCardText>Mô tả: {project.requestDescriptionFromCustomer}</CCardText>
                <CCardText>Ngày bắt đầu: {new Date(project.startDate).toLocaleDateString()}</CCardText>
                <CCardText>Ngày kết thúc dự kiến: {new Date(project.expectedEndDate).toLocaleDateString()}</CCardText>
                <CCardText>Trạng thái: {project.projectStatus}</CCardText>
              </CCardBody>
            </CCard>
          </CCol>
          {/* Cột bên phải chia thành 2 ô, thông tin Employee và Customer */}
          <CCol sm="6">
            <CCard>
              <CCardBody>
                <CCardTitle>Thông tin quản lý</CCardTitle>
                {employee ? (
                  <>
                    <CCardText>Tên: {employee.fullName}</CCardText>
                    <CCardText>Email: {employee.email}</CCardText>
                    <CCardText>Số điện thoại: {employee.phoneNumber}</CCardText>
                  </>
                ) : (
                  <CCardText>Đang tải thông tin nhân viên...</CCardText>
                )}
              </CCardBody>
            </CCard>
            <CCard>
              <CCardBody>
                <CCardTitle>Thông tin khách hàng</CCardTitle>
                {customer ? (
                  <>
                    <CCardText>Tên: {customer.fullName}</CCardText>
                    <CCardText>Số điện thoai: {customer.phoneNumber}</CCardText>
                    <CCardText>Email: {customer.email}</CCardText>
                    <CCardText>Địa chỉ: {customer.address}</CCardText>
                  </>
                ) : (
                  <CCardText>Đang tải thông tin khách hàng...</CCardText>
                )}
              </CCardBody>
            </CCard>
          </CCol>
        </CRow>
      ) : (
        <p>Đang tải thông tin dự án...</p>
      )}
    </CContainer>
  )
}

export default ProjectDetail;