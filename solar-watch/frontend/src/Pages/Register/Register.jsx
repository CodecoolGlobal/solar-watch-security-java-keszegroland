import UserForm from "../../Components/UserForm/UserForm";
import "./register.css";

function Register() {
  return <div className="register">
    <UserForm formType={"register"} />
  </div>
}

export default Register;