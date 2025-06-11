import { reactive } from "vue";
import api from "../api/axios";

const state = reactive({
  token: localStorage.getItem("token") || null,
  user: localStorage.getItem("userId") || null,
  roles: localStorage.getItem("roles") || null,
});

function saveToken(token) {
  state.token = token;
  localStorage.setItem("token", token);
}

function clearToken() {
  state.token = null;
  state.user = null;
  localStorage.removeItem("token");
}

function isLoggedIn() {
  return !!state.token;
}

async function getUserInfo() {
  if (!isLoggedIn()) return null;
  try {
    const res = await api.get("http://localhost:8080/api/users/" + state.user, {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
        Authorization: "Bearer " + state.token,
      },
    });

    if ((res.status = 200)) {
      return res.data;
    } else {
      alert("Failed to update.");
    }
  } catch (e) {
    console.error(e);
    alert("Error occurred.");
  }
  return null;
}

export default {
  state,
  saveToken,
  clearToken,
  isLoggedIn,
  getUserInfo,
};
