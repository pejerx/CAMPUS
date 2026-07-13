import { useState } from "react";

import "../css/component_style.css";

import ItemDetailsModal from "../request-claim/component_item_details_modal";

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

type ExploreCardProps = {
  item: ItemReport;
  variant?: "explore" | "myReports";
};

function ExploreCard({
  item,
  variant = "explore",
}: ExploreCardProps) {
  const [showDetails, setShowDetails] = useState(false);

  const type = item.reportType || item.itemType || "Unknown";

  const location =
    item.location || item.lastSeenLocation || "No location provided";

  const imageSource = item.imageUrl
    ? item.imageUrl
    : item.imagePath
    ? item.imagePath.startsWith("http")
      ? item.imagePath
      : `http://localhost:8080/${item.imagePath}`
    : "";

  return (
    <>
      <div className="explore-card">
        <div className="explore-image">
          {imageSource ? (
            <img
              src={imageSource}
              alt={item.itemName || "Item"}
            />
          ) : (
            <span>📦</span>
          )}
        </div>

        <div className="explore-info">
          <div className="explore-meta">
            <span>
              {item.category || "Uncategorized"}
            </span>

            <span>{location}</span>
          </div>

          <h3>{item.itemName || "Unnamed Item"}</h3>

          <p className="explore-description">
            {item.description ||
              "No description provided."}
          </p>

          <div className="explore-footer">
            <span
              className={
                type === "Lost"
                  ? "lf-badge lost"
                  : "lf-badge found"
              }
            >
              {type}
            </span>

            <span className="explore-status">
              {item.status || "Unclaimed"}
            </span>
          </div>

          {type === "Found" ? (
            <button
              className="explore-btn"
              onClick={() => setShowDetails(true)}
            >
              Request Claim
            </button>
          ) : (
            <button
              className="explore-btn outline"
              onClick={() => setShowDetails(true)}
            >
              View Details
            </button>
          )}
        </div>
      </div>

      {showDetails && (
        <ItemDetailsModal
          item={item}
          onClose={() => setShowDetails(false)}
        />
      )}
    </>
  );
}

export default ExploreCard;