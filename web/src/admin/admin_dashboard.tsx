
import {
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
  reportType?: string;
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

  const lostCount = reports.filter(
    (report) =>
      (report.reportType || report.itemType)?.toUpperCase() ===
      "LOST"
  ).length;

  const foundCount = reports.filter(
    (report) =>
      (report.reportType || report.itemType)?.toUpperCase() ===
      "FOUND"
  ).length;
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
          <div className="admin-table">

  <div className="admin-table-header">

    <h3>Recent Reports</h3>
    <button
      className="explore-btn outline"
      onClick={() => navigate("/admin-reported-items")}
    >
      View All
    </button>
  </div>

  <table>
    <thead>
      <tr>
        <th>Item</th>
        <th>Type</th>
        <th>Status</th>
        <th>Location</th>
      </tr>
    </thead>
    <tbody>
      {reports
        .slice(0, 5)
        .map((report) => (
          <tr key={report.id}>
            <td>
              {report.itemName}
            </td>
            <td>
              <span
                className={
                  (report.reportType || report.itemType)?.toUpperCase() === "LOST"
                    ? "lf-badge lost"
                    :
                     "lf-badge found"
                }
              >
                {(report.reportType || report.itemType)?.toUpperCase()}
              </span>
            </td>
            <td>
              {report.status}
            </td>
            <td>
              {report.location}
            </td>
          </tr>
      ))}

    </tbody>
  </table>
</div>
<div className="admin-table">
  <div className="admin-table-header">
    <h3>Needs Approval</h3>
    <button
      className="explore-btn outline"
      onClick={() => navigate("/admin-reported-items")}
    >
      Open Queue
    </button>
  </div>
  <table>
    <thead>
      <tr>
        <th>Item</th>
        <th>Type</th>
        <th>Location</th>
        <th>Actions</th>
      </tr>
    </thead>
    <tbody>
      {reports
        .filter(
          (report) =>
            report.status === "Under Review"
        )
        .slice(0, 5)
        .map((report) => (
          <tr key={report.id}>
            <td>

              {report.itemName}

            </td>
            <td>

              <span
                className={
                  report.itemType === "Lost"
                    ? "lf-badge lost"
                    : "lf-badge found"
                }
              >
                {report.itemType}
              </span>
            </td>
            <td>

              {report.location}

            </td>
            <td>

              <div className="admin-action">

                <button
                  className="admin-approve"
                  onClick={() =>
                    navigate("/admin-reported-items")
                  }
                >
                  Review
                </button>
              </div>
            </td>
          </tr>

      ))}

    </tbody>

  </table>

  {reports.filter(
    (report) =>
      report.status === "Under Review"
  ).length === 0 && (

    <p
      style={{
        marginTop: "20px",
        color: "#777",
      }}
    >
      No reports waiting for approval.
    </p>

  )}

</div>
            
          </div>
        </section>
      </main>
    </div>
  );
}

export default AdminDashboardPage;