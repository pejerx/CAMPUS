import { useNavigate } from "react-router-dom";
import "../css/component_style.css";
import API_BASE_URL from "../api/api_config";

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

type Props = {
  item: ItemReport;
  onClose: () => void;
  canRequestClaim?: boolean;
};

function ItemDetailsModal({
  item,
  onClose,
  canRequestClaim = true,
}: Props) {
  const navigate = useNavigate();
  const rawType =
    item.reportType || item.itemType || "Unknown";

  const type = rawType.toUpperCase();

  const location = item.location || item.lastSeenLocation || "No location provided";

  const imageSource = item.imageUrl
    ? item.imageUrl
    : item.imagePath
    ? item.imagePath.startsWith("http")
      ? item.imagePath
      : `${API_BASE_URL}/${item.imagePath}`
    : "";

  const createdDate = item.createdAt
    ? new Date(item.createdAt)
    : null;

  const formattedDate = createdDate
    ? createdDate.toLocaleDateString("en-PH", {
        year: "numeric",
        month: "long",
        day: "numeric",
      })
    : "N/A";

  const formattedTime = createdDate
    ? createdDate.toLocaleTimeString("en-PH", {
        hour: "numeric",
        minute: "2-digit",
      })
    : "N/A";

  return (
    <div className="item-modal-overlay">
      <div className="item-modal">

        <button
          className="item-modal-close"
          onClick={onClose}
        >
          ✕
        </button>

        <div className="item-modal-top">

          <div className="item-modal-image-container">

            {imageSource ? (
              <img
                src={imageSource}
                alt={item.itemName}
                className="item-modal-image"
              />
            ) : (
              <div className="item-modal-placeholder">
                📦
              </div>
            )}

          </div>

          <div className="item-modal-details">

            <h1>{item.itemName}</h1>

            <span
              className={
                type === "LOST"
                  ? "lf-badge lost"
                  : "lf-badge found"
              }
            >
              {type.charAt(0) + type.slice(1).toLowerCase()}
            </span>

            <div className="item-modal-information">

              <div>
                <strong>📍 Location</strong>
                <span>{location}</span>
              </div>

              <div>
                <strong>🏷 Category</strong>
                <span>{item.category}</span>
              </div>

              <div>
                <strong>🆔 Report ID</strong>
                <span>#{item.id}</span>
              </div>

              <div>
                <strong>👤 Posted By</strong>
                <span>{item.userId}</span>
              </div>

              <div>
                <strong>📅 Date Reported</strong>
                <span>{formattedDate}</span>
              </div>

              <div>
                <strong>🕒 Time Reported</strong>
                <span>{formattedTime}</span>
              </div>

            </div>

            <div className="item-modal-buttons">
              {canRequestClaim && type === "FOUND" ? (

                <button
                  className="explore-btn"
                  onClick={() =>
                    navigate("/claim-request", {
                      state: {item,},
                    })
                  }
                >
                  Request Claim
                </button>

              ) : (

                <button
                  className="explore-btn outline"
                  onClick={onClose}
                >
                  Close
                </button>

              )}

            </div>

          </div>

        </div>

        <div className="item-modal-description">

          <h3>Description</h3>

          <p>
            {item.description ||
              "No description provided."}
          </p>

        </div>

      </div>
    </div>
  );
}

export default ItemDetailsModal;