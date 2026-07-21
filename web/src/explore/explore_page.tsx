import { useEffect, useState } from "react";
import "../css/style.css";
import "../css/component_style.css";

import UserHeader from "../user/component_user_header";
import ReportItemModal from "../item-report/report_item_form";
import UserSidebar from "../user/component_user_sidebar";
import ExploreCard from "./explore_card";
import { getPublicReports } from "./explore_api";

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

function ExplorePage() {
  const [reports, setReports] = useState<ItemReport[]>([]);
  const [search] = useState("");
  const [filter, setFilter] = useState("All");
  const [showReportModal, setShowReportModal] = useState(false);

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
    return (
      item.location ||
      item.lastSeenLocation ||
      "No location provided"
    );
  };

  const filteredReports = reports.filter((item) => {
    const type = getItemType(item);

    const matchesSearch = `${item.itemName} ${item.category} ${getLocation(
      item
    )} ${item.status}`
      .toLowerCase()
      .includes(search.toLowerCase());

    const matchesFilter =
      filter === "All" || type === filter;

    return matchesSearch && matchesFilter;
  });

  return (
    <div className="lf-dashboard">
      <UserSidebar active="explore" onReportClick={() => setShowReportModal(true)} />

      <main className="lf-main">
        <UserHeader />

        <section className="explore-header">
          <div>
            <h1>
              Explore Lost and Found Items
            </h1>

            <p>
              Browse approved public reports
              from the campus lost and found.
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
          {filteredReports.map((item) => (
            <ExploreCard
              key={item.id}
              item={item}
            />
          ))}
        </section>

        {filteredReports.length === 0 && (
          <p className="explore-empty">
            No public items found.
          </p>
        )}

        {showReportModal && (
          <ReportItemModal
            onClose={() =>
              setShowReportModal(false)
            }
          />
        )}
      </main>
    </div>
  );
}

export default ExplorePage;