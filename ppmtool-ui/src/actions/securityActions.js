import axios from "axios";
import { GET_ERRORS, SET_CURRENT_USER } from "./types";
import setJWTToken from "../securityUtils/setJWTToken";
import jwt_decode from "jwt-decode";

export const createNewUser = (newUser, history) => async (dispatch) => {
  try {
    await axios.post("/api/users/register", newUser);
    history.push("/login");
    dispatch({
      type: GET_ERRORS,
      payload: {},
    });
  } catch (error) {
    dispatch({
      type: GET_ERRORS,
      payload: error.response.data,
    });
  }
};

export const login = (LoginRequst) => async (dispatch) => {
  try {
    //post => Login request
    const res = await axios.post("/api/users/login", LoginRequst);
    //Extract the token
    const { token } = res.data;
    //Store token in the local storage
    localStorage.setItem("jwtToken", token);
    //Set our token header ***
    setJWTToken(token);
    //decode token on React
    const decoded = jwt_decode(token);
    //dispatch to our securityReducer

    dispatch({
      type: SET_CURRENT_USER,
      payload: decoded,
    });
  } catch (error) {
    dispatch({
      type: GET_ERRORS,
      payload: error.response.data,
    });
  }
};

export const logout = () => (dispatch) => {
  localStorage.removeItem("jwtToken");
  setJWTToken(false);
  dispatch({
    type: SET_CURRENT_USER,
    payload: {},
  });
};
