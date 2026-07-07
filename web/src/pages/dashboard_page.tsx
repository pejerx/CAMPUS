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
          <a href="#search">Search Items</a>
          <a href="#guidelines">Guidelines</a>
          <a href="#faqs">FAQs</a>
          <a href="#contact">Contact</a>
          <button onClick={() => setShowLogin(true)}>Login / Sign Up</button>
        </div>
      </nav>

      <div className="floating-dots">
        {Array.from({ length: 18 }).map((_, index) => (
          <span key={index}></span>
        ))}
      </div>

      <main className="hero-section" id="search">
        <h2>Find What You've Lost</h2>
        <p>
          Campus Lost and Found helps students, faculty, and staff report,
          search, and claim lost items in one secure place.
        </p>
        <button className="search-btn" onClick={() => setShowLogin(true)}>
          Search for my item
        </button>
      </main>

      <section className="how-section" id="guidelines">
        <h2>How It Works</h2>

        <div className="how-grid">
          <div className="how-card">
            <div className="how-icon">🔍</div>
            <h3>Search</h3>
            <p>
              Browse through reported found items and check if your lost item
              has already been listed.
            </p>
          </div>

          <div className="how-card">
            <div className="how-icon">📝</div>
            <h3>Report</h3>
            <p>
              Report a lost or found item by adding its details, location, date,
              and optional photo.
            </p>
          </div>

          <div className="how-card">
            <div className="how-icon">🔔</div>
            <h3>Get Notified</h3>
            <p>
              Track your reports and receive updates when your claim request is
              reviewed.
            </p>
          </div>

          <div className="how-card">
            <div className="how-icon">✅</div>
            <h3>Claim</h3>
            <p>
              Submit proof of ownership and wait for verification before
              claiming the item.
            </p>
          </div>
        </div>
      </section>

      <section className="faq-section" id="faqs">
        <h2>Frequently Asked Questions</h2>

        <div className="faq-list">
          <details>
            <summary>How do I report a lost item?</summary>
            <p>
              Login to your account, click Report Item, choose Lost Item, then
              fill in the required details.
            </p>
          </details>

          <details>
            <summary>How do I report a found item?</summary>
            <p>
              Click Report Item, choose Found Item, and provide the item
              description, location, date, and photo if available.
            </p>
          </details>

          <details>
            <summary>How can I claim a found item?</summary>
            <p>
              Open the Found Items page, select the matching item, click Request
              Claim, and submit proof of ownership.
            </p>
          </details>

          <details>
            <summary>Can I track my reports?</summary>
            <p>
              Yes. You can view your lost item reports, found item reports, and
              claim requests inside My Reports.
            </p>
          </details>

          <details>
            <summary>Is my personal information visible?</summary>
            <p>
              No. Your personal details are only visible to authorized personnel
              for verification purposes.
            </p>
          </details>
        </div>
      </section>

      <section className="contact-section" id="contact">
        <h2>Contact Us</h2>

        <form className="contact-form">
          <input type="text" placeholder="Full Name" />
          <input type="email" placeholder="Email Address" />
          <input type="text" placeholder="Phone Number" />
          <input type="text" placeholder="Subject" />
          <textarea placeholder="Message"></textarea>
          <button type="button">Send Message</button>
        </form>
      </section>

      <footer className="footer">
        <h2>
          CAMPUS<span>.</span>
        </h2>

        <div className="footer-links">
          <a href="#search">Home</a>
          <a href="#guidelines">Guidelines</a>
          <a href="#faqs">FAQs</a>
          <a href="#contact">Contact</a>
        </div>

        <p>© 2026 Campus Lost and Found Management System.</p>
      </footer>

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