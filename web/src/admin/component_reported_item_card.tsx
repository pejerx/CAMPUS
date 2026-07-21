import { useState } from "react";
import { IconDotsVertical } from "@tabler/icons-react";
import { PublicItemStatus } from "./admin_api";

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

type Props = {
  item: ItemReport;

  onApprove: (id: number) => void;
  onReject: (id: number) => void;
  onStatusChange: (
    id: number,
    status: PublicItemStatus
  ) => void;
  onEdit?: (item: ItemReport) => void;
  onDelete?: (item: ItemReport) => void;
};

function AdminReportedItemCard({
  item,
  onApprove,
  onReject,
  onStatusChange,
  onEdit,
  onDelete,
}: Props) {
  const getImageSource = () => {
    if (!item.imagePath) return "";

    if (item.imagePath.startsWith("http")) {
      return item.imagePath;
    }

    return `http://localhost:8080/api/reports/${item.imagePath}`;
  };

  const imageSource = getImageSource();

  const status = item.status || "Under Review";
  const [showMenu, setShowMenu] = useState(false);

  return (
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
            {status}
          </span>
        </div>

        {status === "Under Review" && (
          <div
            style={{
              display: "flex",
              gap: "10px",
              marginTop: "12px",
            }}
          >
            <button
              className="explore-btn"
              onClick={() => onApprove(item.id)}
            >
              Approve
            </button>

            <button
              className="explore-btn outline"
              onClick={() => onReject(item.id)}
            >
              Reject
            </button>
          </div>
        )}

        {(status === "Unclaimed" ||
          status === "Pending Claim" ||
          status === "Claimed") && (
          <select
            className="admin-status-select"
            value={status}
            onChange={(e) =>
              onStatusChange(
                item.id,
                e.target.value as PublicItemStatus
              )
            }
          >
            <option value="Unclaimed">
              Unclaimed
            </option>

            <option value="Pending Claim">
              Pending Claim
            </option>

            <option value="Claimed">
              Claimed
            </option>
          </select>
        )}

        {status === "Rejected" && (
          <button
            className="explore-btn outline"
            disabled
            style={{
              cursor: "not-allowed",
              opacity: 0.6,
              marginTop: "12px",
            }}
          >
            Rejected
          </button>
        )}
      </div>

      <div
        style={{
          display: "flex",
          justifyContent: "flex-end",
          marginTop: "12px",
          position: "relative",
        }}
      >

        <button
          className="explore-menu-btn"
          onClick={() =>
            setShowMenu(!showMenu)
          }
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
  );
}

export default AdminReportedItemCard;