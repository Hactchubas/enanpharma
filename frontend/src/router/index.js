import { createRouter, createWebHistory } from "vue-router";
import LoginView from "../views/LoginView.vue";
import HomeView from "../views/HomeView.vue";
import SignUpView from "../views/SignUpView.vue";
import ProfileView from "../views/ProfileView.vue";
import CategoriesView from "../views/CategoriesView.vue";
import ProductsView from "../views/ProductsView.vue";

const routes = [
  { path: "/login", name: "Login", component: LoginView },
  { path: "/categories", name: "Categories", component: CategoriesView },
  { path: "/products", name: "Products", component: ProductsView },
  { path: "/signup", name: "SignUpView", component: SignUpView },
  { path: "/profile", name: "Profile", component: ProfileView },
  { path: "/", name: "Home", component: HomeView },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
