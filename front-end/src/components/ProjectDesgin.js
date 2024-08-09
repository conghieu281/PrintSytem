import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';
import {jwtDecode} from 'jwt-decode';
import { CContainer, CRow, CCol, CButton, CModal, CModalHeader, CModalTitle, CModalBody, CModalFooter, CForm, CFormText, CFormInput, CFormLabel, CCard, CCardHeader, CCardTitle, CCardBody, CCardText } from '@coreui/react';
import { cilCheckCircle, cilXCircle } from '@coreui/icons';
import CIcon from '@coreui/icons-react';

const ProjectDesign = () => {
  const [designs, setDesigns] = useState([]);
  const [modalVisible, setModalVisible] = useState(false);
  const [selectedFile, setSelectedFile] = useState(null);
  const { projectId } = useParams();
  const [projectStatus, setProjectStatus] = useState(null);
  const [userId, setUserId] = useState(null);

  useEffect(() => {
    const fetchProjectData = async () => {
      try {
        const token = document.cookie.split('; ').find(row => row.startsWith('token=')).split('=')[1]
        const decodedToken = jwtDecode(token);
        setUserId(decodedToken.userId);
        const [designResponse, projectResponse] = await Promise.all([
          axios.get(`http://localhost:8080/api/designs/project/${projectId}`, {
            headers: { 'Authorization': `Bearer ${token}` }
          }),
          axios.get(`http://localhost:8080/api/projects/${projectId}`, {
            headers: { 'Authorization': `Bearer ${token}` }
          })
        ]);
        setDesigns(designResponse.data);
        console.log(designResponse.data);
        
        setProjectStatus(projectResponse.data.projectStatus);
      } catch (error) {
        console.error('Error fetching project data:', error);
      }
    };

    fetchProjectData();
  }, [projectId]);

  const handleApprove = async (designId) => {
    try {
      const token = document.cookie.split('; ').find(row => row.startsWith('token=')).split('=')[1]
      await axios.post(`http://localhost:8080/api/designs/${projectId}/designs/${designId}/approve`, {}, {
        headers: { 'Authorization': `Bearer ${token}` },
        params: { leaderId: userId }
      });
      //setProjectStatus('PROJECT_STATUS_PRINTING');
      //fetchProjectData();
      alert("Design approved successfully!");
    } catch (error) {
      if (error.response && error.response.status === 403) {
        alert("You do not have permission to approve a design. Only users with the 'Leader' role can approve designs.");
      } else {
      console.error("Error approving design:", error);
    }}
  };

  const handleReject = async (designId) => {
    try {
      const token = document.cookie.split('; ').find(row => row.startsWith('token=')).split('=')[1]
      await axios.post(`http://localhost:8080/api/designs/${projectId}/designs/${designId}/reject`, {}, {
        headers: { 'Authorization': `Bearer ${token}` },
        params: { leaderId: userId }
      });
      
      //fetchProjectData();
      alert("Design rejected successfully!");
    } catch (error) {
      console.error("Error rejecting design:", error);
    }
  };

  const handleFileUpload = (event) => {
    setSelectedFile(event.target.files[0]);
  };

  const handleAddDesign = async () => {
    const formData = new FormData();
    formData.append('file', selectedFile);
    formData.append('projectId', projectId);
    formData.append('designerId', userId);
    try {
      const token = document.cookie.split('; ').find(row => row.startsWith('token=')).split('=')[1]
      await axios.post(`http://localhost:8080/api/designs/create`, formData, {
        headers: {
          'Content-Type': 'multipart/form-data',
          'Authorization': `Bearer ${token}`
        }
      });
      setModalVisible(false);
      setDesigns((prevDesigns) => [...prevDesigns, { /* dữ liệu thiết kế mới */ }]);
      alert("Design added successfully!");
    } catch (error) {
      if (error.response && error.response.status === 403) {
        alert("You do not have permission to add a design. Only users with the 'Designer' role can add designs.");
      } else if (error.response) {
        // Server trả về phản hồi với mã lỗi khác 2xx
        console.error("Server responded with error:", error.response.data);
        alert(`Error: ${error.response.data}`); // In ra lỗi từ server
      } else if (error.request) {
        // Yêu cầu đã được gửi nhưng không nhận được phản hồi
        console.error("No response received:", error.request);
      } else {
        // Đã xảy ra lỗi khi thiết lập yêu cầu
        console.error("Error setting up request:", error.message);
      }
    }
  };

  return (
    <CContainer>
      <CButton color="primary" onClick={() => setModalVisible(true)}>Add Design</CButton>

      <CRow className="mt-3">
        {designs.map((design, index) => (
          <CCol sm="4" key={index} className="mb-3">
            <CCard>
              <CCardHeader>
                <CCardTitle>Design ID:{design.id}</CCardTitle>
              </CCardHeader>
              <CCardBody>
                <img src={design.filePath} alt='picture' style={{ width: '100%', height: '150px', objectFit: 'cover' }} />
                <CCardText>Designer ID:: {design.designerId}</CCardText>
                <CCardText>Design Time: {design.designTime}</CCardText>
                <CCardText>Status: {design.designStatus}</CCardText>
                <div className="d-flex justify-content-between">
                <CButton color="success" disabled={projectStatus !== 'PROJECT_STATUS_DESIGNING' || design.designStatus === 'DESIGN_STATUS_CANCEL'} onClick={() => handleApprove(design.id)}>
                <CIcon icon={cilCheckCircle} /> Approve
              </CButton>
              <CButton color="danger" disabled={design.designStatus !== 'DESIGN_STATUS_PENDING'} onClick={() => handleReject(design.id)}>
                <CIcon icon={cilXCircle} /> Reject
              </CButton>
                </div>
              </CCardBody>
            </CCard>
          </CCol>
        ))}
      </CRow>

      {/* Modal for adding a new design */}
      <CModal visible={modalVisible} onClose={() => setModalVisible(false)}>
        <CModalHeader>
          <CModalTitle>Add Design</CModalTitle>
        </CModalHeader>
        <CModalBody>
          <CForm>

            <CFormLabel htmlFor="fileInput">Upload Design File</CFormLabel>
            <CFormInput type="file" id="fileInput" onChange={handleFileUpload} />
            <CFormText className="help-block">Please upload your design file here</CFormText>

          </CForm>
        </CModalBody>
        <CModalFooter>
          <CButton color="primary" onClick={handleAddDesign}>Submit</CButton>
          <CButton color="secondary" onClick={() => setModalVisible(false)}>Cancel</CButton>
        </CModalFooter>
      </CModal>
    </CContainer>
  );
};

export default ProjectDesign;
