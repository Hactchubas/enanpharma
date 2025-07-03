<template>
  <div class="profile-container">
    <h2>Your Profile</h2>
    <form @submit.prevent="handleSubmit" class="profile-form">
      <div class="form-group">
        <label for="username">Username</label>
        <input v-model="form.username" id="username" type="text" />
      </div>
      <div class="form-group">
        <label for="firstName">First Name</label>
        <input v-model="form.firstName" id="firstName" type="text" />
      </div>
      <div class="form-group">
        <label for="lastName">Last Name</label>
        <input v-model="form.lastName" id="lastName" type="text" />
      </div>
      <div class="form-group">
        <label for="email">Email</label>
        <input v-model="form.email" id="email" type="email" />
      </div>
      <div class="form-group">
        <label for="address">Address</label>
        <input v-model="form.address" id="address" type="text" />
      </div>
      <div class="form-group">
        <label for="password">New Password</label>
        <input v-model="form.password" id="password" type="password" />
      </div>

      <div class="button-group">
        <button type="submit" class="save-btn" @click="handleEdit">Save Changes</button>
        <button type="button" class="delete-btn" @click="handleDelete">Delete Account</button>
      </div>
    </form>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import api from "../api/axios";

const router = useRouter();
const form = ref({
  firstName: "",
  username: "",
  lastName: "",
  email: "",
  address: "",
  password: "",
  roles: ["USER"],
});

const token = localStorage.getItem("token");
const username = localStorage.getItem("username");


onMounted(fetchUserData);

async function fetchUserData(userId = null) {
  try {
    let request = ""
    if (userId) {
      request = "/api/users/" + userId
    } else {
      request = "/api/users/username/" + username
    }
    const res = await api.get(request, {
      headers: {
        Authorization: "Bearer " + token,
      },
    });

    if (res.status == 200) {
      const data = res.data;
      form.value.firstName = data.firstName;
      form.value.lastName = data.lastName;
      form.value.email = data.email;
      form.value.address = data.address;
      form.value.roles = data.roles;
      form.value.username = data.username

      localStorage.setItem('userId', data.id)
      localStorage.setItem('username', data.username)

    } else {
      alert("Failed to load profile.");
      router.push("/login");
    }
  } catch (err) {
    console.error(err);
    alert("Error loading profile.");
    router.push("/login");
  }
}

async function handleEdit() {
  try {
    const userId = localStorage.getItem('userId')
    const res = await fetch("http://localhost:8080/api/users/" + userId, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
        Authorization: "Bearer " + token,
      },
      body: JSON.stringify(form.value),
    });

    if (res.ok) {
      alert("Updated successfully.");
      // fetchUserData(userId)
    } else {
      alert("Failed to update.");
    }
  } catch (e) {
    console.error(e);
    alert("Error occurred.");
  }
}

async function handleDelete() {
  if (!confirm("Are you sure you want to delete your account?")) return;

  try {
    const userId = localStorage.getItem('userId')
    const res = await fetch("http://localhost:8080/api/users/" + userId, {
      method: "DELETE",
      headers: {
        Authorization: "Bearer " + token,
      },
    });

    if (res.ok) {
      localStorage.removeItem("token");
      alert("Account deleted.");
      router.push("/login");
    } else {
      alert("Failed to delete account.");
    }
  } catch (e) {
    console.error(e);
    alert("Error occurred.");
  }
}
</script>


<style scoped>
.profile-container {
  max-width: 500px;
  margin: 40px auto;
  padding: 30px;
  border-radius: 12px;
}

.profile-container h2 {
  text-align: center;
  margin-bottom: 25px;
}

.profile-form {
  display: flex;
  flex-direction: column;
}

.form-group {
  display: flex;
  flex-direction: column;
  margin-bottom: 15px;
}

label {
  font-weight: bold;
  margin-bottom: 5px;
}

input {
  padding: 8px;
  border-radius: 6px;
  border: 1px solid #ccc;
  font-size: 1em;
}

.button-group {
  display: flex;
  justify-content: space-between;
  margin-top: 20px;
  gap: 10px;
}

button {
  padding: 10px 16px;
  border: none;
  border-radius: 6px;
  font-weight: bold;
  cursor: pointer;
}

.save-btn {
  background-color: #007bff;
  color: white;
}

.save-btn:hover {
  background-color: #0056b3;
}

.delete-btn {
  background-color: #dc3545;
  color: white;
}

.delete-btn:hover {
  background-color: #a71d2a;
}
</style>
