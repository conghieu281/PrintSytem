import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { CButton, CModal, CModalHeader, CModalBody, CModalFooter, CTable, CTableHead, CTableRow, CTableHeaderCell, CTableBody, CTableDataCell, CModalTitle, CFormInput, CFormTextarea } from '@coreui/react';

const Team = () => {
  const [teams, setTeams] = useState([]);
  const [selectedTeam, setSelectedTeam] = useState(null);
  const [teamMembers, setTeamMembers] = useState([]);
  const [visible, setVisible] = useState(false);
  const [newTeam, setNewTeam] = useState({ name: '', description: '' });
  const [showCreateModal, setShowCreateModal] = useState(false);
  const [error, setError] = useState('');

  const fetchTeams = async () => {
    try {
      const token = document.cookie.split('; ').find(row => row.startsWith('token=')).split('=')[1];
      const response = await axios.get('http://localhost:8080/api/teams/', {
        headers: {
          'Authorization': `Bearer ${token}`
        }
      });
      console.log(response.data);
      setTeams(response.data);
    } catch (err) {
      setError('Failed to fetch teams.');
    }
  };

  useEffect(() => {
    fetchTeams();
  }, []);

  const handleView = async (teamId) => {
    try {
      const token = document.cookie.split('; ').find(row => row.startsWith('token=')).split('=')[1]
      const response = await axios.get(`http://localhost:8080/api/teams/${teamId}`, {
        headers: {
          'Authorization': `Bearer ${token}`
        }
      })
      setSelectedTeam(response.data.team)
      setTeamMembers(response.data.users || []); 
      setVisible(true)
    } catch (err) {
      setError('Failed to fetch team details.')
    }
  }

  const fetchUserNameById = async (userId) => {
    try {
      const token = document.cookie.split('; ').find(row => row.startsWith('token=')).split('=')[1];
      const response = await axios.get(`http://localhost:8080/api/users/${userId}`, {
        headers: { 'Authorization': `Bearer ${token}` }
      });
      return response.data.fullName; // Hoặc trường tương ứng với tên người dùng
    } catch (error) {
      console.error('Failed to fetch user details.', error);
      return 'Unknown';
    }
  };

  const handleCreateTeam = async () => {
    try {
      const token = document.cookie.split('; ').find(row => row.startsWith('token=')).split('=')[1];
      await axios.post('http://localhost:8080/api/teams/create', newTeam, {
        headers: {
          'Authorization': `Bearer ${token}`
        }
      });
      setNewTeam({ name: '', description: '' });
      fetchTeams();
    } catch (err) {
      if (err.response && err.response.status === 403) {
        alert('Bạn không đủ quyền để thực hiện chức năng này.');
      } else {
        setError('Failed to create new team.');
      }
    }
  };

  const handleChangeManager = async (teamId, newManagerId) => {
    try {
      const token = document.cookie.split('; ').find(row => row.startsWith('token=')).split('=')[1];
      await axios.put(`http://localhost:8080/api/teams/${teamId}/manager/${newManagerId}`, {}, {
        headers: {
          'Authorization': `Bearer ${token}`
        }
      });
      fetchTeams();
    } catch (err) {
      if (err.response && err.response.status === 403) {
        alert('Bạn không đủ quyền để thực hiện chức năng này.');
      } else {
        setError('Failed to change team manager.');
      }
    }
  };

  return (
    <div>
      {error && <div className="alert alert-danger">{error}</div>}
      <CButton color="primary" onClick={() => setShowCreateModal(true)}>Create New Team</CButton>

      <CTable>
        <CTableHead>
          <CTableRow>
            <CTableHeaderCell>Team Name</CTableHeaderCell>
            <CTableHeaderCell>Description</CTableHeaderCell>
            <CTableHeaderCell>Number of members</CTableHeaderCell>
            <CTableHeaderCell>Actions</CTableHeaderCell>
          </CTableRow>
        </CTableHead>

        <CTableBody>
          {teams.map((team) => (
            <CTableRow key={team.id}>
              <CTableDataCell>{team.name}</CTableDataCell>
              <CTableDataCell>{team.description}</CTableDataCell>
              <CTableDataCell>{team.numberOfMember}</CTableDataCell>
              <CTableDataCell>
                <CButton color="info" onClick={() => handleView(team.id)}>View</CButton>
              </CTableDataCell>
            </CTableRow>
          ))}
        </CTableBody>
      </CTable>

      <CModal visible={visible} onClose={() => setVisible(false)} size="lg">
        <CModalHeader onClose={() => setVisible(false)}>
          <CModalTitle>{selectedTeam ? selectedTeam.name : 'Create New Team'}</CModalTitle>
        </CModalHeader>
        <CModalBody>
          <h5>{selectedTeam?.name}</h5>
          <p>{selectedTeam?.description}</p>
          <p>Leader: {selectedTeam?.managerId}</p>
          <CTable striped hover>
            <CTableHead>
              <CTableRow>
                <CTableHeaderCell>Full Name</CTableHeaderCell>
                <CTableHeaderCell>Email</CTableHeaderCell>
                <CTableHeaderCell>Actions</CTableHeaderCell>
              </CTableRow>
            </CTableHead>
            <CTableBody>
              {teamMembers.map(user => (
                <CTableRow key={user.id}>
                  <CTableDataCell>{user.fullName}</CTableDataCell>
                  <CTableDataCell>{user.email}</CTableDataCell>
                  <CTableDataCell>
                    <CButton color="warning" onClick={() => handleChangeManager(selectedTeam.id, user.id)}>Make Manager</CButton>
                  </CTableDataCell>
                </CTableRow>
              ))}
            </CTableBody>
          </CTable>
        </CModalBody>
        <CModalFooter>
          <CButton color="secondary" onClick={() => setVisible(false)}>Close</CButton>
        </CModalFooter>
      </CModal>


      <CModal visible={showCreateModal} onClose={() => setShowCreateModal(false)}>
        <CModalHeader>
          <CModalTitle>Create New Team</CModalTitle>
        </CModalHeader>
        <CModalBody>
          <CFormInput
            label="Team Name"
            value={newTeam.name}
            onChange={(e) => setNewTeam({ ...newTeam, name: e.target.value })}
          />
          <CFormTextarea
            label="Team Description"
            value={newTeam.description}
            onChange={(e) => setNewTeam({ ...newTeam, description: e.target.value })}
          />
        </CModalBody>
        <CModalFooter>
          <CButton color="primary" onClick={handleCreateTeam}>Create</CButton>
          <CButton color="secondary" onClick={() => setShowCreateModal(false)}>Cancel</CButton>
        </CModalFooter>
      </CModal>
    </div >
  );
};

export default Team;