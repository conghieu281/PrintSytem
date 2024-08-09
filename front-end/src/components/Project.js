import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { CButton, CModal, CModalBody, CModalFooter, CForm, CFormLabel, CFormInput, CRow, CCol, CCard, CCardBody, CCardHeader } from '@coreui/react';
import { useNavigate } from 'react-router-dom';

const Projects = () => {
  const navigate = useNavigate();
  const [projects, setProjects] = useState([]);
  const [selectedProject, setSelectedProject] = useState(null);
  const [showCreateModal, setShowCreateModal] = useState(false);
  const [newProject, setNewProject] = useState({
    projectName: '',
    requestDescriptionFromCustomer: '',
    startDate: '',
    employeeId:5,
    customerId:1,
    expectedEndDate: '',
  });

  useEffect(() => {
    fetchProjects();
  }, []);

  const fetchProjects = async () => {
    try {
      const token = document.cookie.split('; ').find(row => row.startsWith('token=')).split('=')[1]
      const response = await axios.get('http://localhost:8080/api/projects/', {
        headers: { 'Authorization': `Bearer ${token}` }
      });
      const projectsData = await Promise.all(
        response.data.map(async project => {
          console.log(project);
          if (project.employeeId) {
            const userResponse = await axios.get(`http://localhost:8080/api/user/${project.employeeId}`, {
              headers: { 'Authorization': `Bearer ${token}` }
            });
            console.log(userResponse.data);
            return { ...project, manager: userResponse.data.userName };
          } else {
            return { ...project, manager: 'Unknown' };
          }
        })
      )
      setProjects(projectsData);
    } catch (error) {
      console.error('Error fetching projects:', error);
    }
  };

  const handleCreate = async () => {
    try {
      const token = document.cookie.split('; ').find(row => row.startsWith('token=')).split('=')[1]
      await axios.post('http://localhost:8080/api/projects/create', newProject, {
        headers: { 'Authorization': `Bearer ${token}` }});
      fetchProjects();
      setShowCreateModal(false);
      setNewProject({
        projectName: '',
        requestDescriptionFromCustomer: '',
        startDate: '',
        expectedEndDate: '',
      });
    } catch (error) {
      console.error('Error creating project:', error);
    }
  };

  const handleView = (projectId) => {
    navigate(`/menu/projects/${projectId}`);
  };

  const handleDelete = async (id) => {
    try {
      await axios.delete(`http://localhost:8080/api/projects/${id}`);
      fetchProjects();
    } catch (error) {
      console.error('Error deleting project:', error);
    }
  };

  return (
    <>
      <CButton color="primary" onClick={() => setShowCreateModal(true)} className="mb-3">Create New Project</CButton>

      <CRow>
        {projects.map((project) => (
          <CCol sm="4" key={project.id} className="mb-3">
            <CCard>
              <CCardHeader>
                {project.projectName}
              </CCardHeader>
              <CCardBody>
                <p>{project.requestDescriptionFromCustomer}</p>
                <p>Start Date: {new Date(project.startDate).toLocaleDateString()}</p>
                <p>Manager: {project.manager}</p>
                <p>Status: {project.projectStatus}</p>
                <CButton color="success" onClick={() => handleView(project.id)}>View</CButton>
                <CButton color="danger" onClick={() => handleDelete(project.id)}>Delete</CButton>
              </CCardBody>
            </CCard>
          </CCol>
        ))}
      </CRow>

      <CModal visible={showCreateModal} onClose={() => setShowCreateModal(false)}>
        <CModalBody>
          <CForm>
            <CFormLabel htmlFor="projectName">Project Name</CFormLabel>
            <CFormInput
              id="projectName"
              value={newProject.projectName}
              onChange={(e) => setNewProject({ ...newProject, projectName: e.target.value })}
            />

            <CFormLabel htmlFor="description">Description</CFormLabel>
            <CFormInput
              id="description"
              value={newProject.requestDescriptionFromCustomer}
              onChange={(e) => setNewProject({ ...newProject, requestDescriptionFromCustomer: e.target.value })}
            />

            <CFormLabel htmlFor="startDate">Start Date</CFormLabel>
            <CFormInput
              id="startDate"
              type="date"
              value={newProject.startDate}
              onChange={(e) => setNewProject({ ...newProject, startDate: e.target.value })}
            />

            <CFormLabel htmlFor="expectedEndDate">Expected End Date</CFormLabel>
            <CFormInput
              id="expectedEndDate"
              type="date"
              value={newProject.expectedEndDate}
              onChange={(e) => setNewProject({ ...newProject, expectedEndDate: e.target.value })}
            />
          </CForm>
        </CModalBody>
        <CModalFooter>
          <CButton color="primary" onClick={handleCreate}>Create</CButton>
          <CButton color="secondary" onClick={() => setShowCreateModal(false)}>Close</CButton>
        </CModalFooter>
      </CModal>
    </>
  );
};

export default Projects;