// src/pages/MyReservations.jsx
import React, { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { fetchUserReservations, cancelReservation } from '../feature/auth/reservationSlice'; // Adjust the import path if needed
import '../App.css'; // Import the CSS file

const MyReservations = () => {
  const dispatch = useDispatch();
  const token = useSelector((state) => state.auth.token); // Assuming token is stored in auth slice
  const reservationState = useSelector((state) => state.reservation); // Adjust based on your state structure

  const { reservations = [], status, error } = reservationState;

  const [loading, setLoading] = useState(true);

  useEffect(() => {
    if (token) {
      dispatch(fetchUserReservations(token))
        .unwrap()
        .then(() => setLoading(false))
        .catch(() => setLoading(false));
    }
  }, [dispatch, token]);

  if (loading) return <p className="loading">Loading...</p>;

  if (status === 'failed') return <p className="error">{error || 'Failed to load reservations'}</p>;

  return (
    <div className="my-reservations">
      <h2>My Reservations</h2>
      {reservations.length === 0 ? (
        <p>No reservations found.</p>
      ) : (
        <ul>
          {reservations.map((reservation) => (
            <li key={reservation.id} className="reservation-item">
              <h3>Bus Number: {reservation.bus.busNumber}</h3>
              <p>Route: {reservation.bus.route}</p>
              <p>Seats Reserved: {reservation.numberOfSeats}</p>
              <p>Reservation Time: {new Date(reservation.reservationTime).toLocaleString()}</p>
              <button onClick={() => handleCancel(reservation.id)}>Cancel Reservation</button>
            </li>
          ))}
        </ul>
      )}
    </div>
  );

  function handleCancel(reservationId) {
    dispatch(cancelReservation({ reservationId, token }));
  }
};

export default MyReservations;
