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
import EditReportedItemModal from "../item-report/edit_reported_item";


import {
  getReportedItems,
  approveReport,
  rejectReport,
  updateReportStatus,
  PublicItemStatus,
} from "./admin_api";
import { deleteReport } from "../item-report/report_api";

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
  const [statusFilter, setStatusFilter] = useState("Pending Review");
  const [editingItem, setEditingItem] =
  useState<ItemReport | null>(null);
  const [showEditModal, setShowEditModal] =
    useState(false);

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

  const handleDelete = async (
    id: number
  ) => {
    const confirmed = window.confirm(
      "Are you sure you want to delete this report?"
    );
    if (!confirmed) {
      return;
    }
    try {
      await deleteReport(id);
      await loadReportedItems();
      alert("Report deleted.");
    } catch (error) {
      console.error(error);
      alert("Failed to delete report.");
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

  const filteredReports = reports.filter((report) => {
      const matchesSearch = `
        ${report.itemName}
        ${report.category}
        ${report.location}
      `
        .toLowerCase()
        .includes(search.toLowerCase());

      let matchesStatus = true;

      if (statusFilter === "Pending Review") {

        matchesStatus =
          report.status === "Under Review";

      } else if (statusFilter === "Published") {

        matchesStatus = [
          "Unclaimed",
          "Pending Claim",
          "Claimed",
        ].includes(report.status ?? "");

      } else if (statusFilter === "Rejected") {

        matchesStatus =
          report.status === "Rejected";

      }

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
                statusFilter === "Pending Review"
                  ? "active"
                  : ""
              }
              onClick={() =>
                setStatusFilter("Pending Review")
              }
            >
              Pending Review
            </button>

            <button
              className={
                statusFilter === "Published"
                  ? "active"
                  : ""
              }
              onClick={() =>
                setStatusFilter("Published")
              }
            >
              Published
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

            <button
              className={
                statusFilter === "All"
                  ? "active"
                  : ""
              }
              onClick={() =>
                setStatusFilter("All")
              }
            >
              All
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

               onEdit={(item) => {
                  setEditingItem(item);
                  setShowEditModal(true);
              }}
              onDelete={(item) => {
                  handleDelete(item.id);
              }}
            />
          ))}
        </section>

        {filteredReports.length === 0 && (
          <p className="explore-empty">
            No reported items found.
          </p>
        )}
        {showEditModal && editingItem && (
          <EditReportedItemModal
            item={editingItem}
            onClose={() => {
              setShowEditModal(false);
              setEditingItem(null);
              loadReportedItems();
            }}
          />
        )}
      </main>
    </div>
  );
}

export default AdminReportedItemsPage;