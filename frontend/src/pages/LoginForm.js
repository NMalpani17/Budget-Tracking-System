import React, { useState } from "react";
import "../styles/LoginForm.css";
import { useNavigate, Link } from "react-router-dom";

const LoginForm = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();
  const [error, setError] = useState(null);
  const closeModal = () => {
    setError(null);
  };

  const handleLogin = async () => {
    console.log("Login clicked");
    const loginInfo = {
      email: email,
      password: password,
    };
    try {
      const response = await fetch(`http://localhost:8080/signup/login`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(loginInfo),
      });

      if (response.status === 200) {
        console.log("Login successful:", response.data);
        // You can redirect the user or perform other actions upon successful login
        // store login info in local storage
        localStorage.setItem("email", email);

        navigate("/creat-group", { state: { email: email } });
      } else if (response.status === 401) {
        console.error("Invalid response:", response);
        // Handle the case where response or response.data is undefined or null
        console.log("Login failed:", response);
        setError("Please provide proper credentials and try again.");
        console.log("Login failed:", error);
      }
    } catch (error) {
      console.error(
        "Login failed:",
        error.response ? error.response.data : error.message
      );
      setError(error.response ? error.response.data : error.message);
    }
  };

  return (
    <div className="login-container">
      <div class="image-column">
        <img
          src="https://i.etsystatic.com/16745810/r/il/359002/1440948271/il_794xN.1440948271_n621.jpg"
          alt="Your Image"
        />
      </div>
      <div class="form-column">
        <form className="form">
          <label className="label">Email:</label>
          <input
            type="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            className="input"
          />

          <label className="label">Password:</label>
          <input
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            className="input"
          />

          <button type="button" onClick={handleLogin} className="button">
            Log In
          </button>

          <p className="signup-link">
            Don't have an account?
            <Link to="/" className="signup-button">
              Sign Up
            </Link>
          </p>
        </form>
      </div>
      {error && (
        <div className="modal-error">
          <div className="modal-content-error">
            <span className="close" onClick={closeModal}>
              &times;
            </span>
            <p>{error}</p>
          </div>
        </div>
      )}
    </div>
  );
};

export default LoginForm;
