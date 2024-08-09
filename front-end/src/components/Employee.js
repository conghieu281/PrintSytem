import React, { useState, useEffect } from 'react'
import axios from 'axios'
import {
  CButton,
  CCard,
  CCardBody,
  CCol,
  CContainer,
  CRow,
  CModal,
  CModalHeader,
  CModalBody,
  CModalFooter,
  CForm,
  CFormInput,
  CTable,
  CTableHead,
  CTableRow,
  CTableHeaderCell,
  CTableBody,
  CTableDataCell,
} from '@coreui/react'
import CIcon from '@coreui/icons-react'
import { cilUserPlus, cilTrash, cilSwapHorizontal } from '@coreui/icons'

const Employee = () => {
  const [users, setUsers] = useState([])
  const [error, setError] = useState('')
  const [visibleMove, setVisibleMove] = useState(false)
  const [currentUserId, setCurrentUserId] = useState(null)
  const [newTeamId, setNewTeamId] = useState('')

  // Fetch all users
  const fetchUsers = async () => {
    try {
      const token = document.cookie.split('; ').find(row => row.startsWith('token=')).split('=')[1]
      const response = await axios.get('http://localhost:8080/api/user', {
        headers: { 'Authorization': `Bearer ${token}` }
      })
      const usersData = await Promise.all(
        response.data.map(async user => {
          if (user.teamId) {
            const teamResponse = await axios.get(`http://localhost:8080/api/teams/${user.teamId}`, {
              headers: { 'Authorization': `Bearer ${token}` }
            });
            console.log(teamResponse.data);
            return { ...user, teamName: teamResponse.data.team.name };
          } else {
            return { ...user, teamName: 'No Team' };
          }
        })
      )
      setUsers(usersData)
    } catch (err) {
      setError('Failed to fetch users.')
    }
  }

  useEffect(() => {
    fetchUsers()
  }, [])

  const handleMoveToTeam = async () => {
    try {
      const token = document.cookie.split('; ').find(row => row.startsWith('token=')).split('=')[1]
      await axios.put(`http://localhost:8080/api/teams/${newTeamId}/moveEmployee/${currentUserId}`, {}, {
        headers: { 'Authorization': `Bearer ${token}` }
      })
      setVisibleMove(false)
      fetchUsers() // Refresh users list
    } catch (err) {
      setError('Failed to move user to new team.')
    }
  }

  const handleBanUser = async (userId) => {
    // try {
    //   const token = document.cookie.split('; ').find(row => row.startsWith('token=')).split('=')[1]
    //   await axios.put(`http://localhost:8080/api/user/ban/${userId}`, {}, {
    //     headers: { 'Authorization': `Bearer ${token}` }
    //   })
    //   fetchUsers() // Refresh users list
    // } catch (err) {
    //   setError('Failed to ban user.')
    // }
  }

  const handleAddUser = () => {
    // Logic to handle adding a new user
  }

  return (
    <CContainer>
      <div className="d-flex justify-content-end mb-4">
        <CButton color="primary" onClick={handleAddUser}>
          <CIcon icon={cilUserPlus} /> Add User
        </CButton>
      </div>

      <CTable>
        <CTableHead>
          <CTableRow>
            <CTableHeaderCell>User Name</CTableHeaderCell>
            <CTableHeaderCell>Full Name</CTableHeaderCell>
            <CTableHeaderCell>Email</CTableHeaderCell>
            <CTableHeaderCell>Phone</CTableHeaderCell>
            <CTableHeaderCell>Team</CTableHeaderCell>
            <CTableHeaderCell>Roles</CTableHeaderCell>
            <CTableHeaderCell>Actions</CTableHeaderCell>
          </CTableRow>
        </CTableHead>
        <CTableBody>
          {users.map(user => (
            <CTableRow key={user.id}>
              <CTableDataCell>{user.userName}</CTableDataCell>
              <CTableDataCell>{user.fullName}</CTableDataCell>
              <CTableDataCell>{user.email}</CTableDataCell>
              <CTableDataCell>{user.phoneNumber}</CTableDataCell>
              <CTableDataCell>{user.teamName}</CTableDataCell>
              <CTableDataCell>
                {/* {user.permissions.map(permission => (
                  <span key={permission.id}>{permission.roleId} </span>
                ))} */}
                2
              </CTableDataCell>
              <CTableDataCell>
                <CButton color="warning" onClick={() => {
                  setCurrentUserId(user.id)
                  setVisibleMove(true)
                }}>
                  <CIcon icon={cilSwapHorizontal} /> Move Team
                </CButton>
                <CButton color="danger" onClick={() => handleBanUser(user.id)}>
                  <CIcon icon={cilTrash} /> Ban
                </CButton>
              </CTableDataCell>
            </CTableRow>
          ))}
        </CTableBody>
      </CTable>

      {/* Modal for moving user to another team */}
      <CModal visible={visibleMove} onClose={() => setVisibleMove(false)}>
        <CModalHeader>Move to Another Team</CModalHeader>
        <CModalBody>
          <CForm>
            <CFormInput
              type="text"
              value={newTeamId}
              onChange={(e) => setNewTeamId(e.target.value)}
              placeholder="Enter new team ID"
            />
          </CForm>
        </CModalBody>
        <CModalFooter>
          <CButton color="primary" onClick={handleMoveToTeam}>Move</CButton>
          <CButton color="secondary" onClick={() => setVisibleMove(false)}>Cancel</CButton>
        </CModalFooter>
      </CModal>

      {error && <div className="alert alert-danger">{error}</div>}
    </CContainer>
  )
}

export default Employee;
