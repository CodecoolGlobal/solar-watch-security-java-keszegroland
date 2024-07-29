import React from "react";
import { Navigate, Outlet } from "react-router-dom";
import { useAuth } from "./AuthProvider.js";

function ProtectedRoute({ roles }) {
  const { user } = useAuth();

  //console.log("ProtectedRoute - User:", user.role);

  if (!user) {
    return <Navigate to={"/user/signin"} />
  }

  if (!roles.includes(user.role)) {
    return <Navigate to={"/solarwatch"} />;
  }

  return <Outlet />;
}

export default ProtectedRoute;
