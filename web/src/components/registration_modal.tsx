import { registerUser } from "../api/auth_api";
import { useState } from "react";
import "../css/component_style.css";

type RegistrationModalProps = {
  onClose: () => void;
  onLogin: () => void;
};

function RegistrationModal({ onClose, onLogin }: RegistrationModalProps) {
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [role, setRole] = useState("");
  const [contactNumber, setContactNumber] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");

    const handleRegister = async () => {
      if (password !== confirmPassword) {
        alert("Passwords do not match.");
        return;
      }

      try {
        const result = await registerUser({
        firstName,
        lastName,
        role,
        contactNumber,
        email,
        password,
      });

        console.log(result);
        alert("Registration Successful!");
        onLogin();
      } catch (error: any) {
        alert(error.message);
    }
  };

  return (
    <div className="modal-overlay">
      <div className="auth-modal register-modal">
        <button className="close-btn" onClick={onClose}>
          ×
        </button>

        <h2>Create Account</h2>

        <label>First Name</label>
        <input
          type="text"
          name="firstName"
          placeholder="Enter first name"
          value={firstName}
          onChange={(e) => setFirstName(e.target.value)}
        />

        <label>Last Name</label>
        <input
          type="text"
          name="lastName"
          placeholder="Enter last name"
          value={lastName}
          onChange={(e) => setLastName(e.target.value)}
        />

        <label>Role</label>
        <select
          className="auth-select"
          name="role"
          value={role}
          onChange={(e) => setRole(e.target.value)}
        >
          <option value="" disabled>
            Select role
          </option>
          <option value="student">Student</option>
          <option value="faculty_member">Faculty Member</option>
          <option value="administrator">Administrator</option>
          <option value="support_staff">Support Staff</option>
          <option value="non_support_staff">Non-Support Staff</option>
        </select>

        <label>Contact Number</label>
        <input
          type="tel"
          name="contactNumber"
          placeholder="09XXXXXXXXX"
          value={contactNumber}
          onChange={(e) => setContactNumber(e.target.value)}
        />

        <label>Email</label>
        <input
          type="email"
          name="email"
          placeholder="Enter email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
        />

        <label>Password</label>
        <input
          type="password"
          name="password"
          placeholder="Enter password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />

        <label>Confirm Password</label>
        <input
          type="password"
          name="confirmPassword"
          placeholder="Confirm password"
          value={confirmPassword}
          onChange={(e) => setConfirmPassword(e.target.value)}
        />

        <button className="auth-submit" onClick={handleRegister}>
          Register
        </button>

        <p className="switch-text">
          Already have an account?{" "}
          <button onClick={onLogin}>Login</button>
        </p>
      </div>
    </div>
  );
}

export default RegistrationModal;