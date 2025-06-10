import { reactive } from "vue";

const state = reactive({
  token: localStorage.getItem("token") || null,
  user: null, // you can store some decoded user info here if needed
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

export default {
  state,
  saveToken,
  clearToken,
  isLoggedIn,
};
