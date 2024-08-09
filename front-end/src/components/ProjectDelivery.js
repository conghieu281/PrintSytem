import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { CButton, CCard, CCardBody, CCardTitle, CFormSelect } from '@coreui/react';
import { useParams } from 'react-router-dom'

const ProjectDelivery = () => {
  const [deliveries, setDeliveries] = useState([]);
  const [users, setUsers] = useState([]);
  const [selectedUser, setSelectedUser] = useState('');
  const { projectId } = useParams(); // Lấy projectId từ URL
  
  // Lấy danh sách deliveries dựa trên projectId
  useEffect(() => {
    const fetchDeliveries = async () => {
      const token = document.cookie.split('; ').find(row => row.startsWith('token=')).split('=')[1];
      const response = await axios.get(`http://localhost:8080/api/delivery/project/${projectId}`, {
        headers: { 'Authorization': `Bearer ${token}` }
      });
      setDeliveries(response.data);
    };

    fetchDeliveries();
  }, [projectId]);

  // Lấy danh sách user có role là delivery
  useEffect(() => {
    const fetchUsers = async () => {
      const token = document.cookie.split('; ').find(row => row.startsWith('token=')).split('=')[1];
      const response = await axios.get(`http://localhost:8080/api/user/deliver`, {
        headers: { 'Authorization': `Bearer ${token}` }
      });
      setUsers(response.data);
    };

    fetchUsers();
  }, []);

  const handleCreateDelivery = async () => {
    try {
      const token = document.cookie.split('; ').find(row => row.startsWith('token=')).split('=')[1];
      const response = await axios.post(`http://localhost:8080/api/delivery/create/${projectId}`, {}, {
        headers: { 'Authorization': `Bearer ${token}` }
      });
      setDeliveries([...deliveries, response.data]);
      alert('Delivery created successfully!');
    } catch (error) {
      console.error('Error creating delivery:', error);
      alert('Failed to create delivery');
    }
  };

  const handleAssignDelivery = async (deliveryId) => {
    if (!selectedUser) {
      alert('Please select a user');
      return;
    }

    try {
      const token = document.cookie.split('; ').find(row => row.startsWith('token=')).split('=')[1];
      await axios.post(`http://localhost:8080/api/delivery/assign/${deliveryId}/${selectedUser}`, {}, {
        headers: { 'Authorization': `Bearer ${token}` }
      });
      alert('Delivery assigned successfully!');
    } catch (error) {
      console.error('Error assigning delivery:', error);
      alert('Failed to assign delivery');
    }
  };

  const handleConfirmDelivery = async (deliveryId, success) => {
    try {
      const token = document.cookie.split('; ').find(row => row.startsWith('token=')).split('=')[1];
      await axios.post(`http://localhost:8080/api/delivery/confirm/${deliveryId}`, {}, {
        headers: { 'Authorization': `Bearer ${token}` }
      });
      alert('Delivery confirmed successfully!');
    } catch (error) {
      console.error('Error confirming delivery:', error);
      alert('Failed to confirm delivery');
    }
  };

  return (
    <div>
      <CButton onClick={handleCreateDelivery} color="primary" className="mb-4">Create Delivery</CButton>
      
      {deliveries.map(delivery => (
        <CCard key={delivery.id} className="mb-4">
          <CCardBody>
            <CCardTitle>Delivery ID: {delivery.id}</CCardTitle>
            <p>Status: {delivery.deliveryStatus}</p>
            <p>Estimate Delivery Time: {delivery.estimateDeliveryTime}</p>
            <p>Actual Delivery Time: {delivery.actualDeliveryTime}</p>
            <p>Address: {delivery.deliveryAddress}</p>
            <CFormSelect
              className="mb-2"
              value={delivery.deliverId}
              onChange={(e) => setSelectedUser(e.target.value)}
            >
              <option value="">Select User</option>
              {users.map(user => (
                <option key={user.id} value={user.id}>{user.userName}</option>
              ))}
            </CFormSelect>
            <CButton
              color="success"
              onClick={() => handleAssignDelivery(delivery.id)}
              className="me-2"
            >
              Assign to User
            </CButton>
            <CButton
              color="primary"
              onClick={() => handleConfirmDelivery(delivery.id, true)}
              className="me-2"
            >
              Confirm Delivery
            </CButton>
            <CButton
              color="danger"
              onClick={() => handleConfirmDelivery(delivery.id, false)}
            >
              Cancel Delivery
            </CButton>
          </CCardBody>
        </CCard>
      ))}
    </div>
  );
};

export default ProjectDelivery;