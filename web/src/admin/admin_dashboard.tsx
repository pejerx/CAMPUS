
import {
  IconBell,
  IconChevronDown,
  IconSearch,
} from "@tabler/icons-react";

import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

import "../css/style.css";
import "../css/component_style.css";
import AdminSidebar from "./component_admin_sidebar";
import { getAllReports, updateReportStatus } from "./admin_api";

type ItemReport = {
  id: number;
  itemName?: string;
  itemType?: string;
  location?: string;
  description?: string;
  status?: string;
  imageUrl?: string;
};

function AdminDashboardPage() {
  const navigate = useNavigate();

  const [reports, setReports] = useState<ItemReport[]>([]);
  const [search, setSearch] = useState("");

  

  const loadReports = async () => {
    try {
      const data = await getAllReports();
      setReports(data);
    } catch (error) {
      console.error(error);
      alert("Failed to load item reports.");
    }
  };

  useEffect(() => {
    loadReports();
  }, []);

  const handleStatusChange = async (id: number, status: string) => {
    try {
      await updateReportStatus(String(id), status as any);
      loadReports();
    } catch (error) {
      console.error(error);
      alert("Failed to update item status.");
    }
  };

  const filteredReports = reports.filter((report) =>
    `${report.itemName} ${report.itemType} ${report.location} ${report.status}`
      .toLowerCase()
      .includes(search.toLowerCase())
  );

  const lostCount = reports.filter((report) => report.itemType === "Lost").length;
  const foundCount = reports.filter((report) => report.itemType === "Found").length;
  const unclaimedCount = reports.filter(
    (report) => report.status === "Unclaimed"
  ).length;

  return (
    <div className="lf-dashboard">
      <AdminSidebar active="dashboard" />
      <main className="lf-main">
        <header className="lf-header">
          <div className="lf-search">
            <input
              type="text"
              placeholder="Search reports"
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

        <section className="lf-hero">
          <h1>Admin Dashboard</h1>

          <div className="lf-action-row">
            <div className="lf-action-card card-lost">
              <span>Lost Reports</span>
              <div>{lostCount}</div>
            </div>

            <div className="lf-action-card card-found">
              <span>Found Reports</span>
              <div>{foundCount}</div>
            </div>

            <div className="lf-action-card card-report">
              <span>Unclaimed Items</span>
              <div>{unclaimedCount}</div>
            </div>
          </div>
        </section>

        <section className="lf-content">
          <div className="lf-left" style={{ width: "100%" }}>
            <div className="lf-section-title">
              <h2>Reported Items</h2>
              <button onClick={loadReports}>Refresh</button>
            </div>

            <div className="lf-popular-grid">
              {filteredReports.map((report) => (
                <div className="lf-item-card" key={report.id}>
                  <div className="lf-item-image">
                    {report.imageUrl ? (
                      <img
                        src={`http://localhost:8080${report.imageUrl}`}
                        alt={report.itemName}
                        style={{
                          width: "100%",
                          height: "100%",
                          objectFit: "cover",
                          borderRadius: "16px",
                        }}
                      />
                    ) : (
                      "📦"
                    )}
                  </div>

                  <h3>{report.itemName || "Unnamed Item"}</h3>
                  <p>{report.location || "No location provided"}</p>

                  <div className="lf-item-footer">
                    <span
                      className={
                        report.itemType === "Lost"
                          ? "lf-badge lost"
                          : "lf-badge found"
                      }
                    >
                      {report.itemType || "Unknown"}
                    </span>
                  </div>

                  <p style={{ marginTop: "10px" }}>
                    Status: <strong>{report.status || "Unclaimed"}</strong>
                  </p>

                  <select
                    value={report.status || "Unclaimed"}
                    onChange={(e) =>
                      handleStatusChange(report.id, e.target.value)
                    }
                    style={{
                      width: "100%",
                      padding: "10px",
                      marginTop: "10px",
                      borderRadius: "10px",
                      border: "1px solid #ddd",
                    }}
                  >
                    <option value="Unclaimed">Unclaimed</option>
                    <option value="Claimed">Claimed</option>
                    <option value="Pending">Pending</option>
                    <option value="Approved">Approved</option>
                    <option value="Rejected">Rejected</option>
                  </select>
                </div>
              ))}
            </div>

            {filteredReports.length === 0 && (
              <p style={{ marginTop: "20px" }}>No reported items found.</p>
            )}
          </div>
        </section>
      </main>
    </div>
  );
}

export default AdminDashboardPage;