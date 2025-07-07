import { reactive } from "vue";
import api from "../api/axios";

const tokenFromStorage = localStorage.getItem("token");
const userFromStorage = localStorage.getItem("username");

async function getUserInfo() {
  try {
    const res = await api.get("/api/users/username/" + auth.user, {
      headers: {
        "Content-Type": "application/json",
        Authorization: "Bearer " + auth.token,
      },
    });

    if ((res.status = 200)) {
      return res.data;
    } else {
      alert("Failed to update.");
    }
  } catch (e) {
    console.error(e);
    if (e.status == 401) {
      auth.logout();
    } else {
      alert("Error: " + e);
    }
  }
  return null;
}

export const auth = reactive({
  token: tokenFromStorage,
  user: userFromStorage,
  isLoggedIn: () => !!auth.token,

  login(token, user) {
    auth.token = token;
    auth.user = user;
    localStorage.setItem("token", token);
    localStorage.setItem("username", user);
  },

  logout() {
    auth.token = null;
    auth.user = null;
    localStorage.removeItem("token");
    localStorage.removeItem("username");
  },

  getUserInfo,
});

export default auth;
//export default {
//  state,
//  saveToken,
//  clearToken,
//  isLoggedIn,
//  getUserInfo,
//  login,
//};
