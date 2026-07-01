import "../css/component_style.css";

type LoginModalProps = {
  onClose: () => void;
  onRegister: () => void;
};

function LoginModal({ onClose, onRegister }: LoginModalProps) {

  return (
    <div className="modal-overlay">
      <div className="auth-modal">
        <button className="close-btn" onClick={onClose}>
          ×
        </button>

        <h2>Login to CAMPUS</h2>

        <label>ID / Email</label>
        <input type="text" placeholder="Enter your ID or email" />

        <label>Password</label>
        <input type="password" placeholder="Enter your password" />
        
        <button className="auth-submit">
            Login
        </button>

        <p className="switch-text">
            Don't have an account?{" "}
            <button onClick={onRegister}>
                Sign up
            </button>
        </p>

          <>
            <button className="forgot-btn">Forgot password?</button>
            <div className="bottom-line"></div>
          </>
      </div>
    </div>
  );
}

export default LoginModal;