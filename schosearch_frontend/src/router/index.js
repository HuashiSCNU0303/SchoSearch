import { createRouter, createWebHistory } from "vue-router";

const routes = [
  {
    path: "/",
    name: "Main",
    component: () => import("@/views/MainPage"),
  },
  {
    path: "/search",
    name: "Search",
    component: () => import("@/views/SearchResultPage"),
    children: [
      {
        path: "",
        name: "Results",
        component: () => import("@/components/SearchResult"),
      },
    ],
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
