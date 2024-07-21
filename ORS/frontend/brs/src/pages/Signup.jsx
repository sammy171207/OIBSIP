// src/pages/Signup.jsx
import React, { useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { registerUserThunk } from '../feature/auth/authSlice';
import { useNavigate } from 'react-router-dom';
import '../App.css'; // Import the CSS file

const Signup = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const { status, error } = useSelector((state) => state.auth);

  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();
    dispatch(registerUserThunk({ username, password }))
      .unwrap()
      .then((response) => {
        console.log('Registration Successful');
        console.log('JWT Token:', response.token);
        console.log('Message:', response.message);
        console.log('Role:', response.role);
        navigate('/login'); // Redirect to the login page after registration
      })
      .catch((err) => {
        console.error('Registration Failed:', err.message);
      });
  };

  return (
    <div className="signup-container">
      <h2>Sign Up</h2>
      <form onSubmit={handleSubmit}>
        <div>
          <label htmlFor="username">Username:</label>
          <input
            type="text"
            id="username"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            required
          />
        </div>
        <div>
          <label htmlFor="password">Password:</label>
          <input
            type="password"
            id="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
        </div>
        <button type="submit">Register</button>
        {status === 'loading' && <p className="loading">Loading...</p>}
        {status === 'failed' && <p className="error">{error?.message || 'Registration failed'}</p>}
      </form>
    </div>
  );
};

export default Signup;
