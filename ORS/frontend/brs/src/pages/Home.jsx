import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { fetchAllBuses } from '../feature/auth/busSlice';
import BusCard from '../components/BusCard';
import Banner from '../components/Banner'; // Import the Banner component

const Home = () => {
  const dispatch = useDispatch();
  const { buses, status, error } = useSelector((state) => state.bus);  // Ensure 'bus' matches the key in store.js

  useEffect(() => {
    const token = localStorage.getItem('token'); // Fetch the token from localStorage
    if (token) {
      dispatch(fetchAllBuses(token));
    }
  }, [dispatch]);

  return (
    <div className="home">
      <Banner /> {/* Add the Banner component */}
      <h1 className="text-3xl font-bold my-4">Available Buses</h1>
      {status === 'loading' && <p>Loading...</p>}
      {status === 'failed' && <p>Error: {error}</p>}
      {status === 'succeeded' && (
        <div className="bus-list grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
          {buses.map((bus) => (
            <BusCard key={bus.id} bus={bus} />
          ))}
        </div>
      )}
    </div>
  );
};

export default Home;
