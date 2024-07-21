import React, { useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { createReservation } from '../feature/auth/reservationSlice'; // Update import path if necessary

const BusCard = ({ bus }) => {
  const dispatch = useDispatch();
  const token = useSelector((state) => state.auth.token); // Assuming token is stored in auth slice
  const [isReserved, setIsReserved] = useState(false); // Local state to track reservation status
  const [loading, setLoading] = useState(false); // Local state to track loading status
  const [error, setError] = useState(null); // Local state to handle errors

  const handleReserve = () => {
    if (!token) {
      alert('Please login to make a reservation.');
      return;
    }

    const reservationData = {
      bus: {
        id: bus.id,
      },
      numberOfSeats: 1, // Adjust as needed
      reservationTime: new Date().toISOString(), // Use ISO string format for dates
    };

    setLoading(true); // Set loading to true before dispatching the action
    setError(null); // Clear any previous errors

    dispatch(createReservation({ reservationData, token }))
      .unwrap()
      .then(() => {
        setIsReserved(true);
        alert('Reservation successful!');
      })
      .catch((error) => {
        console.error('Reservation failed:', error);
        setError('Reservation failed. Please try again.');
      })
      .finally(() => {
        setLoading(false); // Set loading to false after the action completes
      });
  };

  return (
    <div className="bus-card p-4 border border-gray-200 rounded-lg shadow-md">
      {bus.images && bus.images.length > 0 && (
        <img
          src={bus.images[0]} // Display the first image in the list
          alt={`Bus ${bus.busNumber}`}
          className="w-full h-40 object-cover rounded-lg mb-4"
        />
      )}
      <h3 className="text-xl font-semibold">{bus.busNumber}</h3>
      <p className="text-gray-600">Route: {bus.route}</p>
      <p className="text-gray-600">Capacity: {bus.capacity}</p>

      <button
        onClick={handleReserve}
        disabled={isReserved || loading}
        className={`mt-2 px-4 py-2 rounded ${isReserved ? 'bg-gray-400' : 'bg-blue-500'} text-white`}
      >
        {loading ? 'Reserving...' : isReserved ? 'Reserved' : 'Reserve'}
      </button>

      {error && <p className="text-red-500 mt-2">{error}</p>} {/* Display error message if any */}
    </div>
  );
};

export default BusCard;
