import { createStore } from "redux";
function counterReducer(state = { count: 0 }, action: { type: any }) {
  switch (action.type) {
    case "inc":
      return { count: state.count + 1 };
    default:
      return state;
  }
}
const store = createStore(counterReducer);
export default store;
