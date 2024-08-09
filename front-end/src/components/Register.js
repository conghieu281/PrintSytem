import React, { useState } from 'react'
import axios from 'axios'
import {
  CButton,
  CCard,
  CCardBody,
  CCol,
  CContainer,
  CForm,
  CFormInput,
  CInputGroup,
  CInputGroupText,
  CRow,
} from '@coreui/react'
import CIcon from '@coreui/icons-react'
import { cilLockLocked, cilUser } from '@coreui/icons'

const Register = () => {
  const [formData, setFormData] = useState({
    username: '',
    fullname: '',
    email: '',
    dob: '',
    phone: '',
    password: '',
    repeatPassword: '',
  })
  const [errors, setErrors] = useState({})

  const handleChange = (e) => {
    const { name, value } = e.target
    setFormData({ ...formData, [name]: value })
  }

  const validateForm = () => {
    const newErrors = {}
    const { username, fullname, email, dob, phone, password, repeatPassword } = formData

    if (!username) newErrors.username = 'Username is required'
    if (!fullname) newErrors.fullname = 'Fullname is required'
    if (!email) newErrors.email = 'Email is required'
    if (!dob) newErrors.dob = 'Date of Birth is required'
    if (!phone) newErrors.phone = 'Phone number is required'
    if (!password) newErrors.password = 'Password is required'
    if (!repeatPassword) newErrors.repeatPassword = 'Repeat password is required'
    if (password !== repeatPassword) newErrors.passwordMatch = 'Passwords do not match'

    setErrors(newErrors)
    return Object.keys(newErrors).length === 0
  }

  const handleSubmit = async (e) => {
    e.preventDefault()

    if (!validateForm()) {
      if (errors.passwordMatch) {
        alert(errors.passwordMatch)
      }
      return
    }

    try {
      axios.post('http://localhost:8080/api/register', {
        userName: formData.username,
        password: formData.password,
        fullName: formData.fullname,
        dateOfBirth: formData.dob,
        email: formData.email,
        phoneNumber: formData.phone,
      }, {
        //withCredentials: true // Đảm bảo rằng điều này được đặt nếu bạn đang sử dụng cookies hoặc tokens
      })
        .then(response => {
          alert('User registered successfully:', response.data);
        })
        .catch(error => {
          console.error('Registration error:', error.response ? error.response.data : error.message);
        });
    } catch (error) {
      console.error('Registration error:', error)
      // Handle error (e.g., show error message)
    }
  }

  // const handleSubmit = async (e) => {
  //   e.preventDefault()

  //   if (!validateForm()) {
  //     if (errors.passwordMatch) {
  //       alert(errors.passwordMatch)
  //     }
  //     return
  //   }

  //   try {
  //     console.log(formData);
  //     const response = await fetch('http://localhost:8080/api/register', {
  //       method: 'POST',
  //       headers: {
  //         'Content-Type': 'application/json'
  //       },
  //       body: JSON.stringify({
  //         "userName": formData.username,
  //         "password": formData.password,
  //         "fullName": formData.fullname,
  //         "dateOfBirth": formData.dob,
  //         "email": formData.email,
  //         "phoneNumber": formData.phone,
  //       }),
  //       // credentials: 'include' // Đảm bảo rằng điều này được đặt nếu bạn đang sử dụng cookies hoặc tokens
  //     });

  //     if (!response.ok) {
  //       const errorData = await response.json();
  //       throw new Error(errorData.message || 'Something went wrong');
  //     }

  //     const data = await response.json();
  //     alert('User registered successfully:', data);
  //   } catch (error) {
  //     console.error('Registration error:', error.message);
  //   }
  // }

  return (
    <div className="bg-body-tertiary min-vh-100 d-flex flex-row align-items-center">
      <CContainer>
        <CRow className="justify-content-center">
          <CCol md={9} lg={7} xl={6}>
            <CCard className="mx-4">
              <CCardBody className="p-4">
                <CForm onSubmit={handleSubmit}>
                  <h1>Register</h1>
                  <p className="text-body-secondary">Create your account</p>

                  <CInputGroup className="mb-3">
                    <CInputGroupText>
                      <CIcon icon={cilUser} />
                    </CInputGroupText>
                    <CFormInput
                      name="username"
                      value={formData.username}
                      onChange={handleChange}
                      placeholder="Username"
                      autoComplete="username"
                    />
                  </CInputGroup>
                  {errors.username && <p className="text-danger">{errors.username}</p>}

                  <CInputGroup className="mb-3">
                    <CInputGroupText>
                      <CIcon icon={cilUser} />
                    </CInputGroupText>
                    <CFormInput
                      name="fullname"
                      value={formData.fullname}
                      onChange={handleChange}
                      placeholder="Fullname"
                      autoComplete="fullname"
                    />
                  </CInputGroup>
                  {errors.fullname && <p className="text-danger">{errors.fullname}</p>}

                  <CInputGroup className="mb-3">
                    <CInputGroupText>@</CInputGroupText>
                    <CFormInput
                      name="email"
                      value={formData.email}
                      onChange={handleChange}
                      placeholder="Email"
                      autoComplete="email"
                    />
                  </CInputGroup>
                  {errors.email && <p className="text-danger">{errors.email}</p>}

                  <CInputGroup className="mb-3">
                    <CInputGroupText>
                      <CIcon icon={cilLockLocked} />
                    </CInputGroupText>
                    <CFormInput
                      name="dob"
                      type="date"
                      value={formData.dob}
                      onChange={handleChange}
                      placeholder="DoB"
                      autoComplete="DoB"
                    />
                  </CInputGroup>
                  {errors.dob && <p className="text-danger">{errors.dob}</p>}

                  <CInputGroup className="mb-3">
                    <CInputGroupText>
                      <CIcon icon={cilUser} />
                    </CInputGroupText>
                    <CFormInput
                      name="phone"
                      value={formData.phone}
                      onChange={handleChange}
                      placeholder="Phone"
                      autoComplete="phone"
                    />
                  </CInputGroup>
                  {errors.phone && <p className="text-danger">{errors.phone}</p>}

                  <CInputGroup className="mb-3">
                    <CInputGroupText>
                      <CIcon icon={cilLockLocked} />
                    </CInputGroupText>
                    <CFormInput
                      name="password"
                      type="password"
                      value={formData.password}
                      onChange={handleChange}
                      placeholder="Password"
                      autoComplete="new-password"
                    />
                  </CInputGroup>
                  {errors.password && <p className="text-danger">{errors.password}</p>}

                  <CInputGroup className="mb-4">
                    <CInputGroupText>
                      <CIcon icon={cilLockLocked} />
                    </CInputGroupText>
                    <CFormInput
                      name="repeatPassword"
                      type="password"
                      value={formData.repeatPassword}
                      onChange={handleChange}
                      placeholder="Repeat password"
                      autoComplete="new-password"
                    />
                  </CInputGroup>
                  {errors.repeatPassword && <p className="text-danger">{errors.repeatPassword}</p>}

                  <div className="d-grid">
                    <CButton color="success" type="submit">
                      Create Account
                    </CButton>
                  </div>
                </CForm>
              </CCardBody>
            </CCard>
          </CCol>
        </CRow>
      </CContainer>
    </div>
  )
}

export default Register
