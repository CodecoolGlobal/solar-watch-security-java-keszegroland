import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import Register from './Pages/Register/Register';
import MainPage from './Pages/MainPage/MainPage';
import Signin from './Pages/Signin/Signin';
import ProtectedRoute from './ProtectedRoute.jsx';
import AuthProvider from './AuthProvider.jsx';
import AdminPage from './Pages/AdminPage/AdminPage';
import ReportHandler from './Pages/ReportHandler/ReportHandler';
import { Navigate, RouterProvider, createBrowserRouter } from 'react-router-dom';

const router = createBrowserRouter([
  {
    path: "/",
    element: <Navigate to="/solarwatch" replace />
  },
  {
    path: "/user/register",
    element: <Register />
  },
  {
    path: "/user/signin",
    element: <Signin />
  },
  {
    path: "/admin",
    element: <ProtectedRoute roles={["ROLE_ADMIN"]} />,
    children: [
      {
        path: "",
        element: <AdminPage />
      },
      {
        path: "update/:sunId",
        element: <ReportHandler />
      }
    ]
  },
  {
    path: "/solarwatch",
    element: <ProtectedRoute roles={["ROLE_USER", "ROLE_ADMIN"]} />,
    children: [
      {
        path: "",
        element: <MainPage />
      }
    ]
  }
]);

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <AuthProvider>
      <RouterProvider router={router}></RouterProvider>
    </AuthProvider>
  </React.StrictMode>
);
