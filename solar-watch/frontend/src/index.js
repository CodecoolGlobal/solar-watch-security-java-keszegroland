import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import reportWebVitals from './reportWebVitals';
import { Navigate, RouterProvider, createBrowserRouter } from 'react-router-dom';
import Register from './Pages/Register/Register';
import MainPage from './Pages/MainPage/MainPage';
import Signin from './Pages/Signin/Signin';
import ProtectedRoute from './ProtectedRoute';
import AuthProvider from './AuthProvider';
import AdminPage from './Pages/AdminPage/AdminPage';
import ReportHandler from './Pages/ReportHandler/ReportHandler';

const router = createBrowserRouter(
  [
    {
      path: "/",
      element: <Navigate to="solarwatch" replace />
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
  ]
);

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <AuthProvider>
      <RouterProvider router={router}></RouterProvider>
    </AuthProvider>
  </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
