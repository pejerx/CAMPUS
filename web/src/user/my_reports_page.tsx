import {
  IconBell,
  IconChevronDown,
  IconClipboardText,
  IconHome,
  IconLogout,
  IconPackage,
  IconReport,
  IconSearch,
} from "@tabler/icons-react";

import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

import "../css/style.css";
import "../css/component_style.css";

import ReportItemModal from "../item-report/report_item_form";
import ExploreCard from "../explore/explore_card";
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

function MyReportsPage() {
  const navigate = useNavigate();

  const user = JSON.parse(
    localStorage.getItem("user") || "{}"
  );

  const [reports, setReports] =
    useState<ItemReport[]>([]);

  const [search, setSearch] =
    useState("");

  const [filter, setFilter] =
    useState("All");

  const [showReportModal, setShowReportModal] =
    useState(false);

  const handleLogout = () => {
    localStorage.removeItem("user");
    navigate("/", { replace: true });
  };

  const loadReports = async () => {
    try {
      const data = await getMyReports(user.id);
      setReports(data);
    } catch (error) {
      console.error(error);
      alert("Failed to load your reports.");
    }
  };

  useEffect(() => {
    if (user?.id) {
      loadReports();
    }
  }, []);

  const getItemType = (
    item: ItemReport
  ) => {
    return (
      item.reportType ||
      item.itemType ||
      "Unknown"
    );
  };

  const filteredReports =
    reports.filter((item) => {
      const type = getItemType(item);

      const matchesSearch = `
        ${item.itemName}
        ${item.category}
        ${item.location}
        ${item.lastSeenLocation}
        ${item.status}
      `
        .toLowerCase()
        .includes(search.toLowerCase());

      const matchesFilter =
        filter === "All" ||
        type === filter;

      return (
        matchesSearch &&
        matchesFilter
      );
    });

  return (
    <div className="lf-dashboard">

      <aside className="lf-sidebar">

        <div className="lf-profile">
          <div className="lf-avatar">
            U
          </div>

          <small>
            Welcome Back
          </small>

          <h3>
            Hi, {user.firstName}!
          </h3>
        </div>

        <nav className="lf-menu">

          <button
            onClick={() =>
              navigate("/user-dashboard")
            }
          >
            <IconHome size={17} />
            Home
          </button>

          <button
            onClick={() =>
              navigate("/explore")
            }
          >
            <IconPackage size={17} />
            Explore
          </button>

          <button
            onClick={() =>
              setShowReportModal(true)
            }
          >
            <IconReport size={17} />
            Report Item
          </button>

          <button className="active">
            <IconClipboardText
              size={17}
            />
            My Reports
          </button>

        </nav>

        <button
          className="lf-logout"
          onClick={handleLogout}
        >
          <IconLogout size={17} />
          Log Out
        </button>

      </aside>

      <main className="lf-main">

        <header className="lf-header">

          <div className="lf-search">

            <input
              type="text"
              placeholder="Search my reports..."
              value={search}
              onChange={(e) =>
                setSearch(
                  e.target.value
                )
              }
            />

            <IconSearch size={18} />

          </div>

          <div className="lf-header-right">

            <IconBell size={21} />

            <div className="lf-user-chip">

              <div className="lf-small-avatar">
                U
              </div>

              <span>
                {user.firstName}
              </span>

              <IconChevronDown
                size={16}
              />

            </div>

          </div>

        </header>

        <section className="explore-header">

          <div>

            <h1>
              My Reports
            </h1>

            <p>
              View and track the
              status of all the
              items you have
              reported.
            </p>

          </div>

          <div className="explore-tabs">

            <button
              className={
                filter === "All"
                  ? "active"
                  : ""
              }
              onClick={() =>
                setFilter("All")
              }
            >
              All
            </button>

            <button
              className={
                filter === "Lost"
                  ? "active"
                  : ""
              }
              onClick={() =>
                setFilter("Lost")
              }
            >
              Lost
            </button>

            <button
              className={
                filter === "Found"
                  ? "active"
                  : ""
              }
              onClick={() =>
                setFilter("Found")
              }
            >
              Found
            </button>

          </div>

        </section>

        <section className="explore-grid">

          {filteredReports.map(
            (item) => (
              <ExploreCard
                key={item.id}
                item={item}
                variant="myReports"
              />
            )
          )}

        </section>

        {filteredReports.length ===
          0 && (
          <p className="explore-empty">
            You haven't submitted
            any reports yet.
          </p>
        )}

        {showReportModal && (
          <ReportItemModal
            onClose={() =>
              setShowReportModal(
                false
              )
            }
          />
        )}

      </main>

    </div>
  );
}

export default MyReportsPage;