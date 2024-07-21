import { createSelector } from 'reselect';

// Selector to get the bus slice of the state
const selectBusState = (state) => state.bus;

// Memoized selector to get all buses from the bus state
export const selectAllBuses = createSelector(
  [selectBusState],
  (busState) => busState.buses || []
);
