import { useState } from "react";

import "../css/component_style.css";
import API_BASE_URL from "../api/api_config";
import ItemDetailsModal from "../request-claim/component_item_details_modal";
import { IconDotsVertical } from "@tabler/icons-react";

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

  onEdit?: (item: ItemReport) => void;
  onDelete?: (item: ItemReport) => void;
};

function ExploreCard({
  item,
  variant = "explore",
  onEdit,
  onDelete,
}: ExploreCardProps) {
  const [showDetails, setShowDetails] = useState(false);
  const currentUser = JSON.parse(
    localStorage.getItem("user") || "{}"
  );

  const isOwnReport = currentUser.id === item.userId;
  const rawType = item.reportType || item.itemType || "Unknown";
const [showMenu, setShowMenu] = useState(false);
  const type = rawType.toUpperCase();

  const location =
    item.location || item.lastSeenLocation || "No location provided";

  const imageSource = item.imageUrl
    ? item.imageUrl
    : item.imagePath
    ? item.imagePath.startsWith("http")
      ? item.imagePath
      : `${API_BASE_URL}/${item.imagePath}`
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
                type === "LOST"
                  ? "lf-badge lost"
                  : "lf-badge found"
              }
            >
              {type.charAt(0) + type.slice(1).toLowerCase()}
            </span>

            <span className="explore-status">
              {item.status || "Unclaimed"}
            </span>
          </div>

          {variant === "myReports" ? (

            <div className="explore-actions">

              <button
                className="explore-btn outline"
                onClick={() => setShowDetails(true)}
              >
                View
              </button>

              <div className="explore-menu">

                <button
                  className="explore-menu-btn"
                  onClick={() => setShowMenu(!showMenu)}
                >
                  <IconDotsVertical size={18} />
                </button>

                {showMenu && (

                  <div className="explore-dropdown">

                    <button
                      onClick={() => {
                        setShowMenu(false);
                        onEdit?.(item);
                      }}
                    >
                      Edit Report
                    </button>

                    <button
                      className="delete"
                      onClick={() => {
                        setShowMenu(false);
                        onDelete?.(item);
                      }}
                    >
                      Delete Report
                    </button>

                  </div>

                )}

              </div>

            </div>

          ) : (

            type === "FOUND" ? (

              isOwnReport ? (

                <button
                  className="explore-btn outline"
                  disabled
                  title="You cannot request a claim for your own report."
                >
                  Your Report
                </button>

              ) : item.status === "Claimed" ? (

                <button
                  className="explore-btn outline"
                  disabled
                  title="This item has already been claimed."
                >
                  Already Claimed
                </button>

              ) : (

                <button
                  className="explore-btn"
                  onClick={() => setShowDetails(true)}
                >
                  Request Claim
                </button>

              )

            ) : (

              <button
                className="explore-btn outline"
                onClick={() => setShowDetails(true)}
              >
                View Details
              </button>

            )

          )}
        </div>
      </div>

      {showDetails && (
        <ItemDetailsModal
          item={item}
          onClose={() => setShowDetails(false)}
          canRequestClaim={variant !== "myReports"}
        
        />
      )}
    </>
  );
}

export default ExploreCard;