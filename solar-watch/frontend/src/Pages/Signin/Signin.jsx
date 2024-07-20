import UserForm from "../../Components/UserForm/UserForm";
import "./signin.css";

function Login() {

  return <div className="signin">
    <UserForm formType={"signin"} />
  </div>
}

export default Login;
