import {
  IconChevronDown,
  IconSearch,
} from "@tabler/icons-react";

import { useState } from "react";
import "../css/style.css";
import { useNavigate } from "react-router-dom";
import "../css/component_style.css";
import ReportItemModal from "../item-report/report_item_form";
import UserSidebar from "./component_user_sidebar";
import NotificationDropdown from "../notification/component_notifcation_dropdown";

function UserDashboardPage() {
  const navigate = useNavigate();
  const [showReportModal, setShowReportModal] = useState(false);
  const handleLogout = () => {

    localStorage.removeItem("user");
    navigate("/", { replace: true });

};

  const actionCards = [
    { title: "Lost Items", icon: "🔎", className: "card-lost" },
    { title: "Found Items", icon: "📦", className: "card-found" },
    { title: "Report", icon: "📝", className: "card-report" },
  ];

  const popularItems = [
    { name: "Black Wallet", type: "Found", location: "Library", icon: "👛" },
    { name: "Student ID", type: "Lost", location: "Canteen", icon: "🪪" },
    { name: "Umbrella", type: "Found", location: "Main Gate", icon: "☂️" },
  ];

  const reportHistory = [
    {
      item: "Black Wallet",
      action: "Reported Lost",
      status: "Pending",
      icon: "👛",
    },
  ];

  return (
    <div className="lf-dashboard">
      <UserSidebar active="home" onReportClick={() => setShowReportModal(true)} />
      <main className="lf-main">
        <header className="lf-header">
          <div className="lf-search">
            <input type="text" placeholder="Search" />
            <IconSearch size={18} />
          </div>

          <NotificationDropdown />
            <div className="lf-user-chip">
              <div className="lf-small-avatar">U</div>
              <span>User</span>
              <IconChevronDown size={16} />
            </div>
        </header>

        <section className="lf-hero">
          <h1>What do you want to do today?</h1>

          <div className="lf-action-row">
            {actionCards.map((card) => (
              <button
                className={`lf-action-card ${card.className}`}
                key={card.title}
                onClick={() => {
                  if (card.title === "Report") {
                    setShowReportModal(true);
                  }

                  if (card.title === "Lost Items" || card.title === "Found Items") {
                    navigate("/explore");
                  }
                }}
      >
                <span>{card.title}</span>
                <div>{card.icon}</div>
              </button>
            ))}
            {showReportModal && (
              <ReportItemModal onClose={() => setShowReportModal(false)} />
        )}
          </div>
        </section>

        <section className="lf-content">
          <div className="lf-left">
            <div className="lf-section-title">
              <h2>Popular</h2>
              <button>See all</button>
            </div>

            <div className="lf-popular-grid">
              {popularItems.map((item) => (
                <div className="lf-item-card" key={item.name}>
                  <div className="lf-item-image">{item.icon}</div>

                  <h3>{item.name}</h3>
                  <p>{item.location}</p>

                  <div className="lf-item-footer">
                    <span
                      className={
                        item.type === "Lost"
                          ? "lf-badge lost"
                          : "lf-badge found"
                      }
                    >
                      {item.type}
                    </span>

                    {item.type === "Found" ? (
                      <sl-button size="small" pill className="lf-sl-btn">
                        Request Claim
                      </sl-button>
                    ) : (
                      <sl-button size="small" pill outline className="lf-sl-btn">
                        View
                      </sl-button>
                    )}
                  </div>
                </div>
              ))}
            </div>

            <div className="lf-section-title history">
              <h2>Report History</h2>
              <button>See all</button>
            </div>

            {reportHistory.map((history) => (
              <div className="lf-history-card" key={history.item}>
                <div className="lf-history-image">{history.icon}</div>

                <div className="lf-history-info">
                  <h3>{history.item}</h3>
                  <p>{history.action}</p>
                  <strong>{history.status}</strong>
                </div>

                <sl-button size="small" pill className="lf-sl-btn">
                  View
                </sl-button>
              </div>
            ))}
          </div>

          <div className="lf-right">
            <div className="lf-banner">
              <h2>Find Lost and Found Items Here!</h2>
            </div>

            <div className="lf-info-box">
              <h2>Submit a Report or Claim Request</h2>

              <div className="lf-info-stats">
                <div>
                  <h3>15</h3>
                  <p>Lost</p>
                </div>

                <div>
                  <h3>12</h3>
                  <p>Found</p>
                </div>

                <div>
                  <h3>25</h3>
                  <p>Claims</p>
                </div>
              </div>

              <p>
                Report lost items, post found items, and track claim requests in
                one place.
              </p>
            </div>
          </div>
        </section>
      </main>
    </div>
  );
}

export default UserDashboardPage;