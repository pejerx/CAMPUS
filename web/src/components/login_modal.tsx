import { useState } from "react";
import { useNavigate } from "react-router-dom";
import "../css/component_style.css";

type LoginModalProps = {
  onClose: () => void;
  onRegister: () => void;
};

function LoginModal({ onClose, onRegister }: LoginModalProps) {
  const navigate = useNavigate();

  const [idOrEmail, setIdOrEmail] = useState("");
  const [password, setPassword] = useState("");

  const handleLogin = async () => {
    try {
      const response = await fetch("http://localhost:8080/api/auth/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          id: idOrEmail,
          password: password,
        }),
      });

      if (!response.ok) {
        const errorMessage = await response.text();
        alert(errorMessage);
        return;
      }

      const user = await response.json();

      localStorage.setItem("user", JSON.stringify(user));

      onClose();

      navigate("/user-dashboard");
      
    } catch (error) {
      console.error(error);
      alert("Unable to connect to server.");
    }
  };

  return (
    <div className="modal-overlay">
      <div className="auth-modal">
        <button className="close-btn" onClick={onClose}>
          ×
        </button>

        <h2>Login to CAMPUS</h2>

        <label>ID / Email</label>
        <input
          type="text"
          placeholder="Enter your ID or email"
          value={idOrEmail}
          onChange={(e) => setIdOrEmail(e.target.value)}
        />

        <label>Password</label>
        <input
          type="password"
          placeholder="Enter your password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />

        <button className="auth-submit" onClick={handleLogin}>
          Login
        </button>

        <p className="switch-text">
          Don't have an account?{" "}
          <button onClick={onRegister}>Sign up</button>
        </p>

        <button className="forgot-btn">Forgot password?</button>
        <div className="bottom-line"></div>
      </div>
    </div>
  );
}

export default LoginModal;