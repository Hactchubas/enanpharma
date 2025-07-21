import { createRouter, createWebHistory } from "vue-router";
import LoginView from "../views/LoginView.vue";
import HomeView from "../views/HomeView.vue";
import SignUpView from "../views/SignUpView.vue";
import ProfileView from "../views/ProfileView.vue";
import CategoriesView from "../views/CategoriesView.vue";
import ProductsView from "../views/ProductsView.vue";
import CartView from "../views/CartView.vue";
import CheckoutView from "../views/CheckoutView.vue";
import OrdersView from "../views/OrdersView.vue";
import OrdersOverview from "../views/OrdersOverview.vue";
import auth from "../api/auth.js";

const routes = [
  { path: "/login", name: "Login", component: LoginView },
  {
    path: "/categories",
    name: "Categories",
    component: CategoriesView,
    beforeEnter: async (to, from, next) => {
      const userInfo = await auth.getUserInfo();
      if (auth.isLoggedIn() && userInfo && userInfo.roles.includes('ADMIN')) {
        next();
      } else {
        next("/");
      }
    },
  },
  {
    path: "/products",
    name: "Products",
    component: ProductsView,
    beforeEnter: async (to, from, next) => {
      const userInfo = await auth.getUserInfo();
      if (auth.isLoggedIn() && userInfo && userInfo.roles.includes('ADMIN')) {
        next();
      } else {
        next("/");
      }
    },
  },
  { path: "/signup", name: "SignUpView", component: SignUpView },
  { path: "/profile", name: "Profile", component: ProfileView },
  { path: "/cart", name: "Cart", component: CartView },
  { path: "/checkout", name: "Checkout", component: CheckoutView },
  { path: "/orders", name: "Orders", component: OrdersView },
  {
    path: "/admin/orders",
    name: "AdminOrders",
    component: OrdersOverview,
    beforeEnter: async (to, from, next) => {
      const userInfo = await auth.getUserInfo();
      if (auth.isLoggedIn() && userInfo && userInfo.roles.includes('ADMIN')) {
        next();
      } else {
        next("/");
      }
    },
  },
  { path: "/", name: "Home", component: HomeView },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
