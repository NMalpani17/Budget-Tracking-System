import React, { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import "../styles/SignupForm.css";

const SignupForm = () => {
  const navigate = useNavigate();
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");

  const handleSignup = async () => {
    const loginInfo = {
      email: email,
      password: password,
      firstName: firstName,
      lastName: lastName,
    };
    try {
      const response = await fetch("http://localhost:8080/signup", {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(loginInfo),
      });

      if (response.status === 200) {
        console.log("Signup successful:", response.data);
        // You can redirect the user or perform other actions upon successful signup
        navigate("/login");
      } else {
        console.error("Invalid response:", response);
        // Handle the case where response or response.data is undefined or null
      }
    } catch (error) {
      console.error(
        "Signup failed:",
        error.response ? error.response.data : error.message
      );
      // Handle errors appropriately (show error messages, etc.)
    }
  };

  return (
    <div className="two-column-layout">
      <div className="column left-column">
        <img src="https://assets.materialup.com/uploads/74037e1f-4d66-4694-9b01-6b7b3622672a/thumnail.jpg" alt="Left Column Image" />
      </div>
      <div className="column right-column">
    <div className="signup-container">
      <h2 className="heading">Signup</h2>
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

        <label className="label">First Name:</label>
        <input
          type="text"
          value={firstName}
          onChange={(e) => setFirstName(e.target.value)}
          className="input"
        />

        <label className="label">Last Name:</label>
        <input
          type="text"
          value={lastName}
          onChange={(e) => setLastName(e.target.value)}
          className="input"
        />

        <button type="button" onClick={handleSignup} className="button">
          Sign Up
        </button>

        <p>
          Already have an account? <Link to="/login">Login</Link>
        </p>
      </form>
    </div>
    </div>
    </div>
  );
};

export default SignupForm;
