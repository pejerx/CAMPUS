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
import { getPublicReports } from "../item-report/explore_api";

type ItemReport = {
  id: number;
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
};

function ExplorePage() {
  const navigate = useNavigate();

  const [reports, setReports] = useState<ItemReport[]>([]);
  const [search, setSearch] = useState("");
  const [filter, setFilter] = useState("All");
  const [showReportModal, setShowReportModal] = useState(false);

  const handleLogout = () => {
    localStorage.removeItem("user");
    navigate("/", { replace: true });
  };

  const loadReports = async () => {
    try {
      const data = await getPublicReports();
      setReports(data);
    } catch (error) {
      console.error(error);
      alert("Failed to load items.");
    }
  };

  useEffect(() => {
    loadReports();
  }, []);

  const getItemType = (item: ItemReport) => {
    return item.reportType || item.itemType || "Unknown";
  };

  const getLocation = (item: ItemReport) => {
    return item.location || item.lastSeenLocation || "No location provided";
  };

  const getImageSource = (item: ItemReport) => {
    if (item.imageUrl) return item.imageUrl;

    if (item.imagePath) {
      if (item.imagePath.startsWith("http")) return item.imagePath;
      return `http://localhost:8080/${item.imagePath}`;
    }

    return "";
  };

  const filteredReports = reports.filter((item) => {
    const type = getItemType(item);

    const matchesSearch = `${item.itemName} ${item.category} ${getLocation(
      item
    )} ${item.status}`
      .toLowerCase()
      .includes(search.toLowerCase());

    const matchesFilter = filter === "All" || type === filter;

    return matchesSearch && matchesFilter;
  });

  return (
    <div className="lf-dashboard">
      <aside className="lf-sidebar">
        <div className="lf-profile">
          <div className="lf-avatar">U</div>
          <small>Welcome Back</small>
          <h3>Hi, User!</h3>
        </div>

        <nav className="lf-menu">
          <button onClick={() => navigate("/user-dashboard")}>
            <IconHome size={17} /> Home
          </button>

          <button className="active">
            <IconPackage size={17} /> Explore
          </button>

          <button onClick={() => setShowReportModal(true)}>
            <IconReport size={17} /> Report Item
          </button>

          <button>
            <IconClipboardText size={17} /> My Reports
          </button>
        </nav>

        <button className="lf-logout" onClick={handleLogout}>
          <IconLogout size={17} /> Log Out
        </button>
      </aside>

      <main className="lf-main">
        <header className="lf-header">
          <div className="lf-search">
            <input
              type="text"
              placeholder="Search lost and found items"
              value={search}
              onChange={(e) => setSearch(e.target.value)}
            />
            <IconSearch size={18} />
          </div>

          <div className="lf-header-right">
            <IconBell size={21} />

            <div className="lf-user-chip">
              <div className="lf-small-avatar">U</div>
              <span>User</span>
              <IconChevronDown size={16} />
            </div>
          </div>
        </header>

        <section className="explore-header">
          <div>
            <h1>Explore Lost and Found Items</h1>
            <p>Browse approved public reports from the campus lost and found.</p>
          </div>

          <div className="explore-tabs">
            <button
              className={filter === "All" ? "active" : ""}
              onClick={() => setFilter("All")}
            >
              All
            </button>

            <button
              className={filter === "Lost" ? "active" : ""}
              onClick={() => setFilter("Lost")}
            >
              Lost
            </button>

            <button
              className={filter === "Found" ? "active" : ""}
              onClick={() => setFilter("Found")}
            >
              Found
            </button>
          </div>
        </section>

        <section className="explore-grid">
          {filteredReports.map((item) => {
            const type = getItemType(item);
            const imageSource = getImageSource(item);

            return (
              <div className="explore-card" key={item.id}>
                <div className="explore-image">
                  {imageSource ? (
                    <img src={imageSource} alt={item.itemName || "Item"} />
                  ) : (
                    <span>📦</span>
                  )}
                </div>

                <div className="explore-info">
                  <div className="explore-meta">
                    <span>{item.category || "Uncategorized"}</span>
                    <span>{getLocation(item)}</span>
                  </div>

                  <h3>{item.itemName || "Unnamed Item"}</h3>

                  <p className="explore-description">
                    {item.description || "No description provided."}
                  </p>

                  <div className="explore-footer">
                    <span
                      className={
                        type === "Lost" ? "lf-badge lost" : "lf-badge found"
                      }
                    >
                      {type}
                    </span>

                    <span className="explore-status">
                      {item.status || "Unclaimed"}
                    </span>
                  </div>

                  {type === "Found" ? (
                    <button className="explore-btn">Request Claim</button>
                  ) : (
                    <button className="explore-btn outline">View Details</button>
                  )}
                </div>
              </div>
            );
          })}
        </section>

        {filteredReports.length === 0 && (
          <p className="explore-empty">No public items found.</p>
        )}

        {showReportModal && (
          <ReportItemModal onClose={() => setShowReportModal(false)} />
        )}
      </main>
    </div>
  );
}

export default ExplorePage;