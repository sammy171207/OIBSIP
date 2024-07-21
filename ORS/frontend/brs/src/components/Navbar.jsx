// src/components/Navbar.jsx
import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useSelector, useDispatch } from 'react-redux';
// import { logoutUserThunk } from '../feature/auth/authSlice'; // Adjust import if needed

const Navbar = () => {
  const { user, token } = useSelector((state) => state.auth);
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const handleLogout = async () => {
    try {
      await dispatch(logoutUserThunk()).unwrap(); // Ensure you have this thunk defined in authSlice
      navigate('/login'); // Redirect to login page after logout
    } catch (error) {
      console.error('Logout Failed:', error.message);
    }
  };

  return (
    <nav className="bg-blue-600 p-4">
      <div className="container mx-auto flex justify-between items-center">
        <div className="text-white text-lg font-bold">
          <Link to="/home">Home</Link>
        </div>
        <div className="flex space-x-4">
          {token ? (
            <>
              <Link to="/profile" className="text-white">Profile</Link>
              <Link to="/myreservations" className="text-white">My Reservations</Link>
              {/* <button onClick={handleLogout} className="text-white">Logout</button> */}
            </>
          ) : (
            <>
              <Link to="/login" className="text-white">Login</Link>
              <Link to="/signup" className="text-white">Signup</Link>
            </>
          )}
        </div>
      </div>
    </nav>
  );
};

export default Navbar;
