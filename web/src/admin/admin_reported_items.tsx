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

import { approveReport, getReportedItems, rejectReport } from "./admin_api";

type ItemReport = {
  id: string;
  userId?: string;
  reportType?: string;
  itemName?: string;
  category?: string;
  description?: string;
  location?: string;
  imagePath?: string;
  status?: string;
};

function AdminReportedItemsPage() {
  const navigate = useNavigate();

  const [reports, setReports] = useState<ItemReport[]>([]);
  const [search, setSearch] = useState("");
  const [statusFilter, setStatusFilter] = useState("Under Review");

  const handleLogout = () => {
    localStorage.removeItem("user");
    localStorage.removeItem("admin");
    navigate("/", { replace: true });
  };

  const loadReportedItems = async () => {
    try {
      const data = await getReportedItems();
      setReports(data);
    } catch (error) {
      console.error(error);
      alert("Failed to load reported items.");
    }
  };

  useEffect(() => {
    loadReportedItems();
  }, []);

  const handleApprove = async (id: string) => {
    try {
      await approveReport(id);
      await loadReportedItems();
      alert("Item approved. It is now visible to users.");
    } catch (error) {
      console.error(error);
      alert("Failed to approve item.");
    }
  };

  const handleReject = async (id: string) => {
    try {
      await rejectReport(id);
      await loadReportedItems();
      alert("Item rejected.");
    } catch (error) {
      console.error(error);
      alert("Failed to reject item.");
    }
  };

  const getImageSource = (item: ItemReport) => {
    if (!item.imagePath) return "";

    if (item.imagePath.startsWith("http")) {
      return item.imagePath;
    }

    return `http://localhost:8080/api/reports/${item.imagePath}`;
  };

  const filteredReports = reports.filter((item) => {
    const matchesSearch = `${item.itemName} ${item.reportType} ${item.category} ${item.location} ${item.status}`
      .toLowerCase()
      .includes(search.toLowerCase());

    const matchesStatus =
      statusFilter === "All" || item.status === statusFilter;

    return matchesSearch && matchesStatus;
  });

  return (
    <div className="lf-dashboard">
      <aside className="lf-sidebar">
        <div className="lf-profile">
          <div className="lf-avatar">A</div>
          <small>Welcome Back</small>
          <h3>Hi, Admin!</h3>
        </div>

        <nav className="lf-menu">
          <button onClick={() => navigate("/admin-dashboard")}>
            <IconHome size={17} /> Dashboard
          </button>

          <button className="active">
            <IconReport size={17} /> Reported Items
          </button>

          <button onClick={() => navigate("/admin-claim-requests")}>
            <IconClipboardText size={17} /> Claim Requests
          </button>

          <button>
            <IconPackage size={17} /> Items
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
              placeholder="Search reported items"
              value={search}
              onChange={(e) => setSearch(e.target.value)}
            />
            <IconSearch size={18} />
          </div>

          <div className="lf-header-right">
            <IconBell size={21} />

            <div className="lf-user-chip">
              <div className="lf-small-avatar">A</div>
              <span>Admin</span>
              <IconChevronDown size={16} />
            </div>
          </div>
        </header>

        <section className="explore-header">
          <div>
            <h1>Reported Items</h1>
            <p>Approve or reject submitted lost and found reports.</p>
          </div>

          <div className="explore-tabs">
            <button
                className={statusFilter === "Under Review" ? "active" : ""}
                onClick={() => setStatusFilter("Under Review")}
            >
                Under Review
            </button>

            <button
                className={statusFilter === "Unclaimed" ? "active" : ""}
                onClick={() => setStatusFilter("Unclaimed")}
            >
                Unclaimed
            </button>

            <button
                className={statusFilter === "Pending Claim" ? "active" : ""}
                onClick={() => setStatusFilter("Pending Claim")}
            >
                Pending Claim
            </button>

            <button
                className={statusFilter === "Claimed" ? "active" : ""}
                onClick={() => setStatusFilter("Claimed")}
            >
                Claimed
            </button>

            <button
                className={statusFilter === "Rejected" ? "active" : ""}
                onClick={() => setStatusFilter("Rejected")}
            >
                Rejected
            </button>

            <button
                className={statusFilter === "All" ? "active" : ""}
                onClick={() => setStatusFilter("All")}
            >
                All
            </button>
            </div>
        </section>

        <section className="explore-grid">
          {filteredReports.map((item) => {
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
                    <span>{item.location || "No location"}</span>
                  </div>

                  <h3>{item.itemName || "Unnamed Item"}</h3>

                  <p className="explore-description">
                    {item.description || "No description provided."}
                  </p>

                  <div className="explore-footer">
                    <span
                      className={
                        item.reportType === "Lost"
                          ? "lf-badge lost"
                          : "lf-badge found"
                      }
                    >
                      {item.reportType || "Unknown"}
                    </span>

                    <span className="explore-status">
                      {item.status === "Under Review" ? (
                        <div className="admin-action-row">
                            <button
                            className="admin-approve-btn"
                            onClick={() => handleApprove(item.id)}
                            >
                            Approve
                            </button>

                            <button
                            className="admin-reject-btn"
                            onClick={() => handleReject(item.id)}
                            >
                            Reject
                            </button>
                        </div>
                        ) : (
                        <p className="admin-final-status">
                            Current status: {item.status}
                        </p>
                        )}
                    </span>
                  </div>
                </div>
              </div>
            );
          })}
        </section>

        {filteredReports.length === 0 && (
          <p className="explore-empty">No reported items found.</p>
        )}
      </main>
    </div>
  );
}

export default AdminReportedItemsPage;