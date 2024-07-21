// src/pages/Profile.jsx
import React from 'react';
import { useSelector } from 'react-redux';

const Profile = () => {
  const { user, token } = useSelector((state) => state.auth);

  return (
    <div className="profile-container p-4">
     heelo
    </div>
  );
};

export default Profile;
``
