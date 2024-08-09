import React, { useEffect, useState } from 'react'
import axios from 'axios'
import { CButton, CCard, CCardBody, CCol, CContainer, CRow, CCardHeader, CCardText, CCardTitle, CModal, CModalBody, CModalHeader, CModalFooter, CTable, CTableBody, CTableDataCell, CTableHead, CTableHeaderCell, CTableRow, CForm, CInputGroup, CInputGroupText, CFormInput } from '@coreui/react'
import CIcon from '@coreui/icons-react'
import { cilPlus, cilTrash, cilZoom } from '@coreui/icons'

const Resource = () => {
  const [resources, setResources] = useState([])
  const [resourceDetails, setResourceDetails] = useState([])
  const [error, setError] = useState('')
  const [visible, setVisible] = useState(false)  // State to handle modal visibility
  const [visibleAdd, setVisibleAdd] = useState(false)
  const [newResource, setNewResource] = useState({
    resourceName: '',
    image: '',
    resourceType: '',
    resourceStatus: '',
    availableQuantity: 0,
  })

  useEffect(() => {
    const fetchResources = async () => {
      try {
        const token = document.cookie.split('; ').find(row => row.startsWith('token=')).split('=')[1]
        const response = await axios.get('http://localhost:8080/api/resources/', {
          headers: {
            'Authorization': `Bearer ${token}`
          }
        })
        setResources(response.data)
      } catch (err) {
        setError('Failed to fetch resources.')
      }
    }
    fetchResources()
  }, [])

  const handleDelete = async (id) => {
    try {
      const token = document.cookie.split('; ').find(row => row.startsWith('token=')).split('=')[1]
      await axios.delete(`http://localhost:8080/api/resources/${id}`, {
        headers: {
          'Authorization': `Bearer ${token}`
        }
      })
      setResources(resources.filter(resource => resource.id !== id))
    } catch (err) {
      setError('Failed to delete resource.')
    }
  }

  const handleAddResource = async () => {
    try {
      const token = document.cookie.split('; ').find(row => row.startsWith('token=')).split('=')[1]
      await axios.post('http://localhost:8080/api/resources/create', newResource, {
        headers: { 'Authorization': `Bearer ${token}` }
      })
      setVisibleAdd(false)
      fetchResources() // Refresh resources list
    } catch (err) {
      setError('Failed to add resource.')
    }
  }

  const handleInputChange = (e) => {
    const { name, value } = e.target
    setNewResource(prevState => ({ ...prevState, [name]: value }))
  }

  const handleView = async (resourceId) => {
    try {
      const token = document.cookie.split('; ').find(row => row.startsWith('token=')).split('=')[1];
      const response = await axios.get(`http://localhost:8080/api/resources/resourceProperties/detail/?resourceId=${resourceId}`, {
        headers: {
          'Authorization': `Bearer ${token}`
        }
      });
      setResourceDetails(response.data);
      setVisible(true);  // Show modal after fetching details
    } catch (err) {
      setError('Failed to fetch resource details.');
    }
  }

  const handleAddDetail = () => {
    // Logic để thêm chi tiết tài nguyên mới
    alert('Add new resource detail')
  }

  const handleDeleteDetail = (id) => {
    // Logic để xóa chi tiết tài nguyên
    alert(`Delete resource detail with ID: ${id}`)
  }
  console.log(resources.map((e) => {
    console.log(e.propertyList);
  }))

  return (
    <CContainer>
      <div className="d-flex justify-content-end mb-4">
      <CButton color="primary" onClick={() => setVisibleAdd(true)}>
          <CIcon icon={cilPlus} /> Add Resource
        </CButton>
      </div>
      <CRow>
        {resources.map((resource) => (
          <CCol sm="4" key={resource.id}>
            <CCard>
              <CCardHeader>
                <CCardTitle>{resource.resourceName}</CCardTitle>
              </CCardHeader>
              <CCardBody>
                <img src={resource.image} alt={resource.resourceName} style={{ width: '100%', height: '150px', objectFit: 'cover' }} />
                <CCardText>Type: {resource.resourceType}</CCardText>
                <CCardText>Status: {resource.resourceStatus}</CCardText>
                <CCardText>Available Quantity: {resource.availableQuantity}</CCardText>
                <div className="d-flex justify-content-between">
                  <CButton color="info" onClick={() => handleView(resource.id)}>
                    <CIcon icon={cilZoom} /> View
                  </CButton>
                  <CButton color="danger" onClick={() => handleDelete(resource.id)}>
                    <CIcon icon={cilTrash} /> Delete
                  </CButton>
                </div>
              </CCardBody>
            </CCard>
          </CCol>
        ))}
      </CRow>
      {error && <div className="alert alert-danger">{error}</div>}

      {/* Modal for displaying resource details */}
      <CModal visible={visible} onClose={() => setVisible(false)} size='xl'>
        <CModalHeader>Resource Details</CModalHeader>
        <CModalBody>
          <CTable>
            <CTableHead>
              <CTableRow>
                <CTableHeaderCell>Detail Name</CTableHeaderCell>
                <CTableHeaderCell>Image</CTableHeaderCell>
                <CTableHeaderCell>Price</CTableHeaderCell>
                <CTableHeaderCell>Quantity</CTableHeaderCell>
                <CTableHeaderCell>Actions</CTableHeaderCell>
              </CTableRow>
            </CTableHead>
            <CTableBody>
              {resourceDetails.map((detail) => (
                <CTableRow key={detail.id}>
                  <CTableDataCell>{detail.propertyDetailName}</CTableDataCell>
                  <CTableDataCell><img src={detail.image} alt={detail.propertyDetailName} style={{ width: '50px', height: '50px' }} /></CTableDataCell>
                  <CTableDataCell>{detail.price}</CTableDataCell>
                  <CTableDataCell>{detail.quantity}</CTableDataCell>
                  <CTableDataCell>
                    <CButton color="primary" size="sm" onClick={handleAddDetail}><CIcon icon={cilPlus} /></CButton>
                    <CButton color="danger" size="sm" onClick={() => handleDeleteDetail(detail.id)}><CIcon icon={cilTrash} /></CButton>
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


      {/* Modal for adding new resource */}
      <CModal visible={visibleAdd} onClose={() => setVisibleAdd(false)} size="lg">
        <CModalHeader>Add New Resource</CModalHeader>
        <CModalBody>
          <CForm>
            <CInputGroup className="mb-3">
              <CInputGroupText>Resource Name</CInputGroupText>
              <CFormInput
                name="resourceName"
                value={newResource.resourceName}
                onChange={handleInputChange}
                placeholder="Enter resource name"
              />
            </CInputGroup>
            <CInputGroup className="mb-3">
              <CInputGroupText>Image URL</CInputGroupText>
              <CFormInput
                name="image"
                value={newResource.image}
                onChange={handleInputChange}
                placeholder="Enter image URL"
              />
            </CInputGroup>
            <CInputGroup className="mb-3">
              <CInputGroupText>Resource Type</CInputGroupText>
              <CFormInput
                name="resourceType"
                value={newResource.resourceType}
                onChange={handleInputChange}
                placeholder="Enter resource type"
              />
            </CInputGroup>
            <CInputGroup className="mb-3">
              <CInputGroupText>Resource Status</CInputGroupText>
              <CFormInput
                name="resourceStatus"
                value={newResource.resourceStatus}
                onChange={handleInputChange}
                placeholder="Enter resource status"
              />
            </CInputGroup>
            <CInputGroup className="mb-3">
              <CInputGroupText>Available Quantity</CInputGroupText>
              <CFormInput
                type="number"
                name="availableQuantity"
                value={newResource.availableQuantity}
                onChange={handleInputChange}
                placeholder="Enter available quantity"
              />
            </CInputGroup>
          </CForm>
        </CModalBody>
        <CModalFooter>
          <CButton color="primary" onClick={handleAddResource}>Add Resource</CButton>
          <CButton color="secondary" onClick={() => setVisibleAdd(false)}>Cancel</CButton>
        </CModalFooter>
      </CModal>


    </CContainer>
  )
}

export default Resource
