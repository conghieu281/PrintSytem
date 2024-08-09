import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';
import { CRow, CCol, CCard, CCardBody, CCardHeader, CButton, CFormInput } from '@coreui/react';
import { jwtDecode } from 'jwt-decode';


const ProjectPrint = () => {
  const { projectId } = useParams();
  const [printJobs, setPrintJobs] = useState([]);
  const [resources, setResources] = useState([]);
  const [selectedDesign, setSelectedDesign] = useState(null);
  const [selectedResources, setSelectedResources] = useState({});
  const [userId, setUserId] = useState(null);
  const [printJobId, setprintJobId] = useState(null);
  const [isSubmitDisabled, setIsSubmitDisabled] = useState(true);

  useEffect(() => {
    const fetchPrintJobs = async () => {
      try {
        const token = document.cookie.split('; ').find(row => row.startsWith('token=')).split('=')[1];
        const decodedToken = jwtDecode(token);
        setUserId(decodedToken.userId);

        const designResponse = await axios.get(`http://localhost:8080/api/designs/project/${projectId}`, {
          headers: { 'Authorization': `Bearer ${token}` }
        })
          ;
        const approvedDesign = designResponse.data.find(design => design.designStatus === 'DESIGN_STATUS_APPROVED');
        setSelectedDesign(approvedDesign);


        if (approvedDesign) {
          const printJobsResponse = await axios.get(`http://localhost:8080/api/printjobs/design/${approvedDesign.id}`, {
            headers: { 'Authorization': `Bearer ${token}` }
          });

          setPrintJobs(printJobsResponse.data)
        }

        // Fetch resources (cột bên phải)
        const resourcesResponse = await axios.get('http://localhost:8080/api/resources/resourceProperties/detail-list/', {
          headers: { 'Authorization': `Bearer ${token}` }
        });
        setResources(resourcesResponse.data);

      } catch (error) {
        console.error('Error fetching print jobs or resources:', error);
      }
    };

    fetchPrintJobs();
  }, [projectId]);

  // Hàm xử lý khi người dùng thay đổi giá trị nhập vào
  const handleResourceChange = (resourceDetailId, quantity) => {
    setSelectedResources(prevState => ({
      ...prevState,
      [resourceDetailId]: quantity
    }));
  };

  // Kiểm tra nếu tất cả các trường đã được nhập số lượng
  useEffect(() => {
    const allFieldsFilled = resources.every(resource =>
      selectedResources[resource.id] && selectedResources[resource.id] > 0
    );
    setIsSubmitDisabled(!allFieldsFilled);
  }, [selectedResources, resources]);

  // Hàm xử lý khi nhấn nút submit
  const handlePreparePrint = async () => {
    try {
      const token = document.cookie.split('; ').find(row => row.startsWith('token=')).split('=')[1];
      console.log(printJobId);
      
      const request = {
        id: 3,
        designId: selectedDesign.id,
        resourceDetails: Object.keys(selectedResources).map(resourceDetailId => ({
          resourceDetailId: parseInt(resourceDetailId),
          quantity: parseInt(selectedResources[resourceDetailId])
        }))
      };
      await axios.post('http://localhost:8080/api/printjobs/prepare', request, {
        headers: { 'Authorization': `Bearer ${token}` }
      });
      
      console.log(request);
      
      alert('Print job prepared successfully!');
    } catch (error) {
      console.error('Error preparing print job:', error);
      alert('Failed to prepare print job');
    }
  };

  const handleDonePrint = async (id) => {
    try {
      const token = document.cookie.split('; ').find(row => row.startsWith('token=')).split('=')[1];
      await axios.put(
        `http://localhost:8080/api/printjobs/confirmCompleted/${id}`, 
        {}, // Nếu không có dữ liệu gửi kèm, truyền một đối tượng rỗng
        {
          headers: { 'Authorization': `Bearer ${token}` }
        }
      );
      alert('Print job completed successfully!');
    } catch (error) {
      console.error('Error completing print job:', error);
      alert('Failed to complete print job');
    }
  };

  return (
    <CRow>
      <CCol sm="6">
        <CCard>
          <CCardHeader>Print Jobs</CCardHeader>
          <CCardBody>
            {printJobs.map(job => (
              <div key={job.id}>
                <p>{job.id}</p>
                <p>DesignID: {job.designId}</p>
                <p>Status: {job.printJobStatus}</p>
                <CButton
                  color="primary"
                  disabled={job.printJobStatus !== 'PRINT_JOB_PRINTING'}
                  onClick={() => handleDonePrint(job.id)}
                >
                  Done
                </CButton>
              </div>
            ))}
          </CCardBody>
        </CCard>
      </CCol>

      <CCol sm="6">
        <CCard>
          <CCardBody>
            {resources.map(resource => (
              <div key={resource.id}>
                <p>{resource.propertyDetailName}</p>
                <CFormInput
                  type="number"
                  max={resource.quantity}
                  value={selectedResources[resource.id] || ''}
                  onChange={(e) => handleResourceChange(resource.id, e.target.value)}
                />
              </div>
            ))}
          </CCardBody>
          <CButton
            onClick={handlePreparePrint}
            disabled={isSubmitDisabled}
            color="primary"
          >
            Submit
          </CButton>
        </CCard>
      </CCol>
    </CRow>
  );
};

export default ProjectPrint;
