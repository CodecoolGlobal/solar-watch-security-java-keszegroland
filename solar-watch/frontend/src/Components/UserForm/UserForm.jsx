import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import "./userForm.css";
import { FaLock, FaUser } from "react-icons/fa";
import { useAuth } from "../../AuthProvider.js";

async function handleRequest(url, client) {
  const response = await fetch(url, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(client),
  });
  return response.json();
}

function UserForm({ formType }) {
  const navigate = useNavigate();
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const { login } = useAuth();

  async function handleSubmit(e) {
    e.preventDefault();
    const client = { username, password };
    const url = formType === "register" ? "/api/user/register" : "/api/user/signin";
    const data = await handleRequest(url, client);
    saveJwtTokenToLocalStorage(data);
  }

  function saveJwtTokenToLocalStorage(data) {
    if (formType === "signin" && data.jwt) {
      localStorage.setItem("token", data.jwt);
      login(data.jwt);
      navigate("/");
    } else if (formType === "register") {
      navigate("/user/signin");
    }
  }

  const isRegister = formType === "register";
  const headerText = isRegister ? "Register" : "Sign In";
  const buttonText = isRegister ? "CONTINUE" : "SIGN IN";
  const linkText = isRegister
    ? "Already have an account?"
    : "Don't have an account?";
  const linkPath = isRegister ? "/user/signin" : "/user/register";

  return (
    <div className={isRegister ? "register-container" : "log-in-container"}>
      <form className="user-details-form" onSubmit={handleSubmit}>
        <h1 id="header">{headerText}</h1>
        <div className="input-box">
          <input
            type="text"
            placeholder="Username"
            required
            onChange={(e) => setUsername(e.target.value)}
            id="username"
          />
          <FaUser className="icon" />
        </div>
        <div className="input-box">
          <input
            type="password"
            placeholder="Password"
            required
            onChange={(e) => setPassword(e.target.value)}
            id="password"
          />
          <FaLock className="icon" />
        </div>
        <button className="submit-button" type="submit">
          {buttonText}
        </button>
        <p className="link-to-text">
          {linkText} <Link className="link-to" to={linkPath}>Click here!</Link>
        </p>
      </form>
    </div>
  );
}

export default UserForm;
