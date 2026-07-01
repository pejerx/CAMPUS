import "../css/component_style.css";

type RegistrationModalProps = {
  onClose: () => void;
  onLogin: () => void;
};

function RegistrationModal({ onClose, onLogin }: RegistrationModalProps) {
  return (
    <div className="modal-overlay">
      <div className="auth-modal register-modal">
        <button className="close-btn" onClick={onClose}>
          ×
        </button>

        <h2>Create Account</h2>

        <label>First Name</label>
        <input type="text" />

        <label>Last Name</label>
        <input type="text" />

        <label>Role</label>
        <select className="auth-select" defaultValue="">
          <option value="" disabled>
            Select role
          </option>
          <option value="faculty_member">Faculty Member</option>
          <option value="security">Security</option>
          <option value="support_staff">Support Staff</option>
          <option value="operations">Operations</option>
          <option value="administration">Administration</option>
        </select>

        <label>Email</label>
        <input type="email" placeholder="Enter email" />

        <label>Password</label>
        <input type="password" placeholder="Enter password" />

        <label>Confirm Password</label>
        <input type="password" placeholder="Confirm password" />

        <button className="auth-submit">Register</button>

        <p className="switch-text">
          Already have an account?{" "}
          <button onClick={onLogin}>Login</button>
        </p>
      </div>
    </div>
  );
}

export default RegistrationModal;