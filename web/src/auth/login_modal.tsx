import { useState } from "react";
import { useNavigate } from "react-router-dom";
import "../css/component_style.css";

type LoginModalProps = {
  onClose: () => void;
  onRegister: () => void;
};

function LoginModal({ onClose, onRegister }: LoginModalProps) {
  const navigate = useNavigate();

  const [loginType, setLoginType] = useState<"USER" | "ADMIN">("USER");
  const [idOrEmail, setIdOrEmail] = useState("");
  const [password, setPassword] = useState("");

  const handleLogin = async () => {
    try {
      if (loginType === "ADMIN") {
        if (
          idOrEmail.trim() !== "1" ||
          password.trim() !== "school-admin-access"
        ) {
          alert("Invalid admin credentials.");
          return;
        }

        const admin = {
          id: "1",
          firstName: "Admin",
          role: "ADMIN",
        };

        localStorage.setItem("admin", JSON.stringify(admin));
        localStorage.setItem("user", JSON.stringify(admin));

        onClose();
        navigate("/admin-dashboard");
        return;
      }

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

        <div className="login-toggle">
          <button
            type="button"
            className={loginType === "USER" ? "active" : ""}
            onClick={() => setLoginType("USER")}
          >
            User
          </button>

          <button
            type="button"
            className={loginType === "ADMIN" ? "active" : ""}
            onClick={() => setLoginType("ADMIN")}
          >
            Admin
          </button>
        </div>

        <label>{loginType === "ADMIN" ? "Admin ID" : "ID / Email"}</label>
        <input
          type="text"
          placeholder={
            loginType === "ADMIN"
              ? "Enter admin ID"
              : "Enter your ID or email"
          }
          value={idOrEmail}
          onChange={(e) => setIdOrEmail(e.target.value)}
        />

        <label>Password</label>
        <input
          type="password"
          placeholder={
            loginType === "ADMIN"
              ? "Enter admin password"
              : "Enter your password"
          }
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />

        <button className="auth-submit" onClick={handleLogin}>
          Login
        </button>

        {loginType === "USER" && (
          <>
            <p className="switch-text">
              Don't have an account?{" "}
              <button onClick={onRegister}>Sign up</button>
            </p>

            <button className="forgot-btn">Forgot password?</button>
          </>
        )}

        <div className="bottom-line"></div>
      </div>
    </div>
  );
}

export default LoginModal;