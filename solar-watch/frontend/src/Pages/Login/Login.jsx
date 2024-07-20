import UserForm from "../../Components/UserForm/UserForm";
import "./login.css";

function Login() {

  return <div className="login">
    <UserForm formType={"login"} />
  </div>
}

export default Login;
