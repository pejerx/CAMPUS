import { useState } from "react";
import "../css/style.css";
import LoginModal from "../components/login_modal";
import RegistrationModal from "../components/registration_modal";

function DashboardPage() {
  const [showLogin, setShowLogin] = useState(false);
  const [showRegister, setShowRegister] = useState(false);

  return (
    <div className="dashboard-page">
      <nav className="navbar">
        <h1 className="logo">
          CAMPUS<span>.</span>
        </h1>

        <div className="nav-links">
          <a href="#">Search Items</a>
          <a href="#">Guidelines</a>
          <a href="#">FAQs</a>
          <a href="#">Contact</a>
          <button onClick={() => setShowLogin(true)}>Login / Sign Up</button>
        </div>
      </nav>

      <div className="floating-dots">
        {Array.from({ length: 18 }).map((_, index) => (
          <span key={index}></span>
        ))}
      </div>

      <main className="hero-section">
        <h2>Find What You've Lost</h2>
        <p>
          Reunited is a platform that helps you find your lost items and
          reconnect with what matters most to you.
        </p>
        <button className="search-btn">Search for my item</button>
      </main>

      {showLogin && (
        <LoginModal
            onClose={() => setShowLogin(false)}
            onRegister={() => {
                setShowLogin(false);
                setShowRegister(true);
        }}
    />
    )}

      {showRegister && (
        <RegistrationModal
            onClose={() => setShowRegister(false)}
            onLogin={() => {
                setShowRegister(false);
                setShowLogin(true);
        }}
    />
    )}
    </div>
  );
}

export default DashboardPage;