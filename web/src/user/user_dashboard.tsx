import {
  IconChevronDown,
  IconSearch,
} from "@tabler/icons-react";

import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

import "../css/style.css";
import "../css/component_style.css";

import ReportItemModal from "../item-report/report_item_form";
import UserSidebar from "./component_user_sidebar";
import NotificationDropdown from "../notification/component_notifcation_dropdown";

import { getPublicReports } from "../explore/explore_api";
import { getMyReports } from "../item-report/report_api";

type ItemReport = {
  id: number;
  userId?: string;
  itemName?: string;
  reportType?: string;
  itemType?: string;
  category?: string;
  location?: string;
  lastSeenLocation?: string;
  description?: string;
  status?: string;
  imagePath?: string;
  imageUrl?: string;
  createdAt?: string;
};

function UserDashboardPage() {

  const navigate = useNavigate();

  const user = JSON.parse(
    localStorage.getItem("user") || "{}"
  );

  const [showReportModal, setShowReportModal] =
    useState(false);

  const [publicReports, setPublicReports] =
    useState<ItemReport[]>([]);

  const [myReports, setMyReports] =
    useState<ItemReport[]>([]);

  const loadDashboard = async () => {

    try {

      const publicData =
        await getPublicReports();

      setPublicReports(publicData);

      if (user?.id) {

        const myData =
          await getMyReports(user.id);

        setMyReports(myData);

      }

    } catch (error) {

      console.error(error);

    }

  };

  useEffect(() => {

    loadDashboard();

  }, []);

  const handleLogout = () => {

    localStorage.removeItem("user");

    navigate("/", {
      replace: true,
    });

  };

  const actionCards = [
    {
      title: "Lost Items",
      icon: "🔎",
      className: "card-lost",
    },
    {
      title: "Found Items",
      icon: "📦",
      className: "card-found",
    },
    {
      title: "Report",
      icon: "📝",
      className: "card-report",
    },
  ];

  /*
   * Dashboard Preview
   */

  const popularItems =
    publicReports.slice(0, 3);

  const reportHistory =
    [...myReports]
      .sort(
        (a, b) =>
          new Date(
            b.createdAt || ""
          ).getTime() -
          new Date(
            a.createdAt || ""
          ).getTime()
      )
      .slice(0, 3);

  /*
   * Statistics
   */

  const lostCount =
    publicReports.filter(
      (item) =>
        (item.reportType || item.itemType) ===
        "Lost"
    ).length;

  const foundCount =
    publicReports.filter(
      (item) =>
        (item.reportType || item.itemType) ===
        "Found"
    ).length;

  const myReportCount =
    myReports.length;

  const getLocation = (
    item: ItemReport
  ) => {

    return (
      item.location ||
      item.lastSeenLocation ||
      "Unknown Location"
    );

  };

  const getImage = (
    item: ItemReport
  ) => {

    if (item.imagePath) {

      return `http://localhost:8080/${item.imagePath}`;

    }

    return "";

  };

  return (
  <div className="lf-dashboard">
    <UserSidebar
      active="home"
      onReportClick={() =>
        setShowReportModal(true)
      }
    />

    <main className="lf-main">

      <header className="lf-header">

        <div className="lf-search">
          <input
            type="text"
            placeholder="Search public items..."
            onFocus={() =>
              navigate("/explore")
            }
            readOnly
          />
          <IconSearch size={18} />
        </div>

        <NotificationDropdown />

        <div className="lf-user-chip">

          <div className="lf-small-avatar">
            {user.firstName
              ?.charAt(0)
              .toUpperCase() || "U"}
          </div>

          <span>
            {user.firstName || "User"}
          </span>

          <IconChevronDown
            size={16}
          />

        </div>

      </header>

      <section className="lf-hero">

        <h1>
          What do you want to do today?
        </h1>

        <div className="lf-action-row">

          {actionCards.map((card) => (

            <button
              key={card.title}
              className={`lf-action-card ${card.className}`}
              onClick={() => {

                if (
                  card.title === "Report"
                ) {
                  setShowReportModal(true);
                  return;
                }

                navigate("/explore");

              }}
            >

              <span>
                {card.title}
              </span>

              <div>
                {card.icon}
              </div>

            </button>

          ))}

        </div>

        {showReportModal && (

          <ReportItemModal
            onClose={() => {
              setShowReportModal(false);
              loadDashboard();
            }}
          />

        )}

      </section>

      <section className="lf-content">

        <div className="lf-left">

          <div className="lf-section-title">

            <h2>
              Recent Public Items
            </h2>

            <button
              onClick={() =>
                navigate("/explore")
              }
            >
              See all
            </button>

          </div>

          <div className="lf-popular-grid">

            {popularItems.length === 0 ? (

              <p className="explore-empty">
                No public reports yet.
              </p>

            ) : (

              popularItems.map((item) => (

                <div
                  className="lf-item-card"
                  key={item.id}
                >

                  <div className="lf-item-image">

                    {item.imagePath ? (

                      <img
                        src={getImage(item)}
                        alt={item.itemName}
                        className="lf-item-photo"
                      />

                    ) : (

                      <div
                        className="lf-item-placeholder"
                      >
                        📦
                      </div>

                    )}

                  </div>

                  <h3>
                    {item.itemName}
                  </h3>

                  <p>
                    {getLocation(item)}
                  </p>

                  <div
                    className="lf-item-footer"
                  >

                    <span
                      className={
                        (item.reportType ||
                          item.itemType) ===
                        "Lost"
                          ? "lf-badge lost"
                          : "lf-badge found"
                      }
                    >

                      {item.reportType ||
                        item.itemType}

                    </span>

                    <sl-button
                      size="small"
                      pill
                      className="lf-sl-btn"
                      onClick={() =>
                        navigate("/explore")
                      }
                    >
                      View
                    </sl-button>

                  </div>

                </div>

              ))

            )}

          </div>

          <div className="lf-section-title history">

  <h2>
    My Recent Reports
  </h2>

  <button
    onClick={() =>
      navigate("/my-reports")
    }
  >
    See all
  </button>

</div>

{reportHistory.length === 0 ? (

  <p className="explore-empty">
    You haven't submitted any reports yet.
  </p>

) : (

  reportHistory.map((history) => (

    <div
      className="lf-history-card"
      key={history.id}
    >

      <div className="lf-history-image">

        {history.imagePath ? (

          <img
            src={getImage(history)}
            alt={history.itemName}
            className="lf-item-photo"
          />

        ) : (

          <div className="lf-item-placeholder">
            📦
          </div>

        )}

      </div>

      <div className="lf-history-info">

        <h3>
          {history.itemName}
        </h3>

        <p>
          {history.reportType} Item
        </p>

        <strong>
          {history.status}
        </strong>

      </div>

      <sl-button
        size="small"
        pill
        className="lf-sl-btn"
        onClick={() =>
          navigate("/my-reports")
        }
      >
        View
      </sl-button>

    </div>

  ))

)}

</div>

<div className="lf-right">

  <div className="lf-banner">

    <h2>
      Find Lost and Found Items Here!
    </h2>

  </div>

  <div className="lf-info-box">

    <h2>
      CAMPUS Overview
    </h2>

    <div className="lf-info-stats">

      <div>

        <h3>
          {lostCount}
        </h3>

        <p>
          Lost
        </p>

      </div>

      <div>

        <h3>
          {foundCount}
        </h3>

        <p>
          Found
        </p>

      </div>

      <div>

        <h3>
          {myReportCount}
        </h3>

        <p>
          My Reports
        </p>

      </div>

    </div>

    <p>

      Browse recently reported lost and found
      items, submit your own reports, and
      monitor your report history in one place.

    </p>

  </div>

</div>

</section>

</main>

</div>

);

}

export default UserDashboardPage;