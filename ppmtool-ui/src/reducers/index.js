import { combineReducers } from "redux";
import ErrorReducer from "./errorReducer";
import projectReducer from "./projectReducer";
import backlogReducer from "./backlogReducer";

export default combineReducers({
  errors: ErrorReducer,
  project: projectReducer,
  backlog: backlogReducer
});
