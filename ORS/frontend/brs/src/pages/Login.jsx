// src/pages/Login.jsx
import React, { useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { loginUserThunk } from '../feature/auth/authSlice';
import '../App.css'; // Import the CSS file

const Login = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const { status, error } = useSelector((state) => state.auth);

  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();
    dispatch(loginUserThunk({ username, password }))
      .unwrap()
      .then((response) => {
        console.log('Login Successful');
        console.log('JWT Token:', response.token);
        console.log('Username:', response.username);
        console.log('Role:', response.role);
        localStorage.setItem('token', response.token); // Save token
        navigate('/home'); // Redirect to the home page after login
      })
      .catch((err) => {
        console.error('Login Failed:', err.message);
      });
  };

  return (
    <div className="login-container">
      <h2>Login</h2>
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
        <button type="submit">Login</button>
        {status === 'loading' && <p className="loading">Loading...</p>}
        {status === 'failed' && <p className="error">{error?.message || 'Login failed'}</p>}
      </form>
    </div>
  );
};

export default Login;
