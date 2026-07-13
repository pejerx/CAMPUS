import {
  IconBell,
  IconChevronDown,
  IconSearch,
} from "@tabler/icons-react";

import { useEffect, useState } from "react";

import "../css/style.css";
import "../css/component_style.css";

import AdminSidebar from "./component_admin_sidebar";
import AdminReportedItemCard from "./component_reported_item_card";

import {
  getReportedItems,
  approveReport,
  rejectReport,
  updateReportStatus,
  PublicItemStatus,
} from "./admin_api";

type ItemReport = {
  id: number;
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
  const [reports, setReports] = useState<ItemReport[]>([]);
  const [search, setSearch] = useState("");
  const [statusFilter, setStatusFilter] = useState("All");

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

  const handleApprove = async (id: number) => {
    try {
      await approveReport(String(id));
      await loadReportedItems();
      alert("Report approved successfully.");
    } catch (error) {
      console.error(error);
      alert("Failed to approve report.");
    }
  };

  const handleReject = async (id: number) => {
    try {
      await rejectReport(String(id));
      await loadReportedItems();
      alert("Report rejected.");
    } catch (error) {
      console.error(error);
      alert("Failed to reject report.");
    }
  };

  const handleStatusChange = async (
    id: number,
    status: PublicItemStatus
  ) => {
    try {
      await updateReportStatus(String(id), status);
      await loadReportedItems();
      alert("Item status updated successfully.");
    } catch (error) {
      console.error(error);
      alert("Failed to update item status.");
    }
  };

  const filteredReports = reports.filter((item) => {
    const matchesSearch = `${item.itemName} ${item.reportType} ${item.category} ${item.location} ${item.status}`
      .toLowerCase()
      .includes(search.toLowerCase());

    const matchesStatus =
      statusFilter === "All" ||
      item.status === statusFilter;

    return matchesSearch && matchesStatus;
  });

  return (
    <div className="lf-dashboard">
      <AdminSidebar active="reported-items" />

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

            <p>
              Review user-submitted lost and found
              reports before making them public.
            </p>
          </div>

          <div className="explore-tabs">
            <button
              className={
                statusFilter === "All" ? "active" : ""
              }
              onClick={() => setStatusFilter("All")}
            >
              All
            </button>

            <button
              className={
                statusFilter === "Under Review"
                  ? "active"
                  : ""
              }
              onClick={() =>
                setStatusFilter("Under Review")
              }
            >
              Under Review
            </button>

            <button
              className={
                statusFilter === "Unclaimed"
                  ? "active"
                  : ""
              }
              onClick={() =>
                setStatusFilter("Unclaimed")
              }
            >
              Unclaimed
            </button>

            <button
              className={
                statusFilter === "Pending Claim"
                  ? "active"
                  : ""
              }
              onClick={() =>
                setStatusFilter("Pending Claim")
              }
            >
              Pending Claim
            </button>

            <button
              className={
                statusFilter === "Claimed"
                  ? "active"
                  : ""
              }
              onClick={() =>
                setStatusFilter("Claimed")
              }
            >
              Claimed
            </button>

            <button
              className={
                statusFilter === "Rejected"
                  ? "active"
                  : ""
              }
              onClick={() =>
                setStatusFilter("Rejected")
              }
            >
              Rejected
            </button>
          </div>
        </section>

        <section className="explore-grid">
          {filteredReports.map((item) => (
            <AdminReportedItemCard
              key={item.id}
              item={item}
              onApprove={handleApprove}
              onReject={handleReject}
              onStatusChange={handleStatusChange}
            />
          ))}
        </section>

        {filteredReports.length === 0 && (
          <p className="explore-empty">
            No reported items found.
          </p>
        )}
      </main>
    </div>
  );
}

export default AdminReportedItemsPage;