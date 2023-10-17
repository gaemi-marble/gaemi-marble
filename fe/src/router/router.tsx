import { createBrowserRouter } from "react-router-dom";
import Home from "../components/Home";
import Login from "../components/Login";

export const router = createBrowserRouter([
  {
    path: "/",
    element: <Home />,
  },
  {
    path: "/signin",
    element: <Login />,
  },
]);
