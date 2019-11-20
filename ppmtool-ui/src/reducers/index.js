import { combineReducers } from "redux";
import ErrorReducer from "./errorReducer";
import projectReducer from "./projectReducer";

export default combineReducers({
  errors: ErrorReducer,
  project: projectReducer
});
