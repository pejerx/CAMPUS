import {
  IconBell,
  IconChevronDown,
  IconSearch,
} from "@tabler/icons-react";

import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

import "../css/style.css";
import "../css/component_style.css";

import EditReportedItemModal from "../item-report/edit_reported_item";
import ReportItemModal from "../item-report/report_item_form";
import ExploreCard from "../explore/explore_card";
import UserSidebar from "./component_user_sidebar";

import {
  getMyReports,
  deleteReport,
} from "../item-report/report_api";

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

  const [editingItem, setEditingItem] =
    useState<ItemReport | null>(null);

  const [showEditModal, setShowEditModal] =
    useState(false);

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

  const handleDelete = async (
  id: number
) => {

  const confirmed =
    window.confirm(
      "Are you sure you want to delete this report?"
    );

  if (!confirmed) {

    return;

  }

  try {

    await deleteReport(id);

    setReports((previous) =>
      previous.filter(
        (report) => report.id !== id
      )
    );

    alert(
      "Report deleted successfully."
    );

  } catch (error) {
    console.error(error);
    alert(
      "Failed to delete report."
    );
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
      <UserSidebar active="home" onReportClick={() => setShowReportModal(true)} />
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

                  onEdit={(item) => {
                      setEditingItem(item);
                      setShowEditModal(true);
                  }}

                  onDelete={(item) => {
                    handleDelete(item.id);
                }}
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

        {showEditModal && editingItem && (
            <EditReportedItemModal
                item={editingItem}
                onClose={() => {
                    setShowEditModal(false);
                    setEditingItem(null);
                    loadReports();
                }}
            />
        )}

      </main>

    </div>
  );
  
      

}
export default MyReportsPage;