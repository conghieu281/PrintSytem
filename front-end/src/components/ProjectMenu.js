import React, { useEffect, useState } from 'react';
import { CContainer, CAlert } from '@coreui/react';
import { Outlet, useNavigate, useParams } from 'react-router-dom';
import CIcon from '@coreui/icons-react';
import { cilColorBorder, cilClone, cilSpreadsheet, cilTruck } from '@coreui/icons';
import axios from 'axios';

const ProjectMenu = () => {
    const navigate = useNavigate();
    const { projectId } = useParams();
    const [projectStatus, setProjectStatus] = useState('');
    const [alertMessage, setAlertMessage] = useState('');

    useEffect(() => {
        const token = document.cookie.split('; ').find(row => row.startsWith('token=')).split('=')[1]
        axios.get(`http://localhost:8080/api/projects/${projectId}`, {
            headers: { 'Authorization': `Bearer ${token}` }
          })
            .then(response => {
                setProjectStatus(response.data.projectStatus);
            })
            .catch(error => {
                console.error('There was an error fetching the project status!', error);
            });
    }, [projectId]);

    const handleNavigation = (path, allowed) => {
        if (!allowed) {
            setAlertMessage('You cannot proceed to this phase at this time.');
            return;
        }
        navigate(path);
    };

    const isDesigning = projectStatus === 'PROJECT_STATUS_DESIGNING';
    const isPrinting = projectStatus === 'PROJECT_STATUS_PRINTING';
    const isDone = projectStatus === 'PROJECT_STATUS_DONE';

    return (
        <CContainer>
            {alertMessage && <CAlert color="warning">{alertMessage}</CAlert>}
            <div style={{ display: 'flex', justifyContent: 'space-evenly', padding: '10px' }}>
                <div onClick={() => handleNavigation('detail', true)} style={{ cursor: 'pointer', display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
                    <CIcon icon={cilSpreadsheet} size="3xl" />
                    Detail
                </div>
                <div onClick={() => handleNavigation('design', isDesigning || isPrinting || isDone)} style={{ cursor: 'pointer', display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
                    <CIcon icon={cilColorBorder} size="3xl" />
                    Design
                </div>
                <div onClick={() => handleNavigation('print', isPrinting || isDone)} style={{ color: isDesigning ? 'black' : 'white', cursor: isDesigning ? 'not-allowed' : 'pointer', display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
                    <CIcon icon={cilClone} size="3xl" />
                    Print
                </div>
                <div onClick={() => handleNavigation('shipping', isDone)} style={{ color: isPrinting || isDesigning ? 'black' : 'white', cursor: isPrinting || isDesigning ? 'not-allowed' : 'pointer', display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
                    <CIcon icon={cilTruck} size="3xl" />
                    Delivery
                </div>
            </div>
            <Outlet />
        </CContainer>
    );
};

export default ProjectMenu;
