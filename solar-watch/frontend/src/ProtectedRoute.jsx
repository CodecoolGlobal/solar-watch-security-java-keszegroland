import React from "react";
import { Navigate, Outlet } from "react-router-dom";
import { useAuth } from "./AuthProvider.jsx";

function ProtectedRoute({ roles }) {
  const { user } = useAuth();

  if (!user) {
    return <Navigate to={"/user/signin"} />
  }

  if (roles && !roles.includes(user.role)) {
    return <Navigate to={"/solarwatch"} />;
  }

  return <Outlet />;
}

export default ProtectedRoute;
