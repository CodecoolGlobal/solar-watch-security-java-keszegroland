import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import "./clientForm.css";
import { FaLock, FaUser } from "react-icons/fa";

async function registerNewClient(client) {
  const response = await fetch("/api/user/register", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(client)
  });
  return response;
}

function ClientForm() {
  const navigate = useNavigate();
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  async function handleRegistration(e) {
    e.preventDefault();
    const client = { username, password };
    await registerNewClient(client);
    navigate("/");
  }

  return <div className="client-form">
    <form className="registration-form" onSubmit={handleRegistration}>
      <h1 id="header">Create account</h1>
      <div className="input-box">
        <input type="text" placeholder="Username" required onChange={(e) => setUsername(e.target.value)} id="username"></input>
        <FaUser className="icon" />
      </div>
      <div className="input-box">
        <input type="password" placeholder="Password" required onChange={(e) => setPassword(e.target.value)} id="password"></input>
        <FaLock className="icon" />
      </div>
      <button className="sumbit-button" type="submit">Continue</button>
      <p className="link-to-signin-text">Already have an account? <Link className="link-to-signin" to={"/user/signin"}>Login</Link></p>
    </form>
  </div>
}

export default ClientForm;