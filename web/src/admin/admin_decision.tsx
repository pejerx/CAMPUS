import { useState } from "react";

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

  action: "approve" | "reject";

  onClose: () => void;

  onConfirm: (
    reason: string,
    message: string,
    remarks: string
  ) => void;
};

function AdminDecisionModal({
  item,
  action,
  onClose,
  onConfirm,
}: Props) {

  const type =
    item.reportType ||
    item.itemType ||
    "Unknown";

  const location =
    item.location ||
    item.lastSeenLocation ||
    "No location provided";

  const imageSource = item.imageUrl
    ? item.imageUrl
    : item.imagePath
    ? item.imagePath.startsWith("http")
      ? item.imagePath
      : `${API_BASE_URL}${item.imagePath}`
    : "";

  const [reason, setReason] =
    useState("");

  const [remarks, setRemarks] =
    useState("");

  const rejectMessages: Record<
    string,
    string
  > = {

    "Insufficient Proof of Ownership":
      "Your claim request has been rejected because the submitted proof of ownership was insufficient to verify ownership. If you have additional evidence, you may visit the Lost and Found Office for further assistance.",

    "Item Already Claimed":
      "Your claim request has been rejected because the item has already been successfully claimed by another individual.",

    "Information Does Not Match":
      "Your claim request has been rejected because the information you provided does not sufficiently match the identifying details of the reported item.",

    "Duplicate Claim":
      "Our records indicate that you have already submitted a claim request for this item.",

    "Suspicious Claim":
      "Your claim request has been rejected because the submitted information could not be verified.",

    "Other":
      "",
  };

  const generatedMessage =
    action === "approve"
      ? "Your claim request has been approved. Please proceed to the Lost and Found Office for identity verification and item release. Kindly bring your school ID and any supporting documents that may help verify ownership."
      : rejectMessages[reason] || "";

  const createdDate =
    item.createdAt
      ? new Date(item.createdAt)
      : null;

  const formattedDate =
    createdDate
      ? createdDate.toLocaleDateString(
          "en-PH",
          {
            year: "numeric",
            month: "long",
            day: "numeric",
          }
        )
      : "N/A";

  const formattedTime =
    createdDate
      ? createdDate.toLocaleTimeString(
          "en-PH",
          {
            hour: "numeric",
            minute: "2-digit",
          }
        )
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

            <h1>

              {action === "approve"
                ? "Approve Claim Request"
                : "Reject Claim Request"}

            </h1>

            <h2>
              {item.itemName}
            </h2>

            <span
              className={
                type === "Lost"
                  ? "lf-badge lost"
                  : "lf-badge found"
              }
            >
              {type}
            </span>

            <div className="item-modal-information">

              <div>

                <strong>
                  📍 Location
                </strong>

                <span>
                  {location}
                </span>

              </div>

              <div>

                <strong>
                  🏷 Category
                </strong>

                <span>
                  {item.category}
                </span>

              </div>

              <div>

                <strong>
                  🆔 Report ID
                </strong>

                <span>
                  #{item.id}
                </span>

              </div>

              <div>

                <strong>
                  👤 Posted By
                </strong>

                <span>
                  {item.userId}
                </span>

              </div>

              <div>

                <strong>
                  📅 Date Reported
                </strong>

                <span>
                  {formattedDate}
                </span>

              </div>

              <div>

                <strong>
                  🕒 Time Reported
                </strong>

                <span>
                  {formattedTime}
                </span>

              </div>

                        </div>

          </div>

        </div>

        <div className="item-modal-description">

          <h3>
            {action === "approve"
              ? "Approval Details"
              : "Rejection Details"}
          </h3>

          {action === "reject" && (

            <>

              <label className="admin-form-label">
                Reason for Rejection
              </label>

              <select
                className="admin-form-input"
                value={reason}
                onChange={(e) =>
                  setReason(e.target.value)
                }
              >

                <option value="">
                  Select a reason...
                </option>

                <option>
                  Insufficient Proof of Ownership
                </option>

                <option>
                  Item Already Claimed
                </option>

                <option>
                  Information Does Not Match
                </option>

                <option>
                  Duplicate Claim
                </option>

                <option>
                  Suspicious Claim
                </option>

                <option>
                  Other
                </option>

              </select>

            </>

          )}

          <label
            className="admin-form-label"
            style={{ marginTop: "25px" }}
          >
            Notification Message
          </label>

          <textarea
            className="admin-form-textarea"
            rows={7}
            value={generatedMessage}
            readOnly={action === "reject"}
          />

          <label
            className="admin-form-label"
            style={{ marginTop: "25px" }}
          >
            Additional Remarks
          </label>

          <textarea
            className="admin-form-textarea"
            rows={4}
            placeholder={
              action === "approve"
                ? "Example: Bring your school ID and purchase receipt."
                : "Optional remarks for the claimant."
            }
            value={remarks}
            onChange={(e) =>
              setRemarks(e.target.value)
            }
          />

        </div>

        <div className="item-modal-buttons">

          <button
            className="explore-btn outline"
            onClick={onClose}
          >
            Cancel
          </button>

          <button
            className="explore-btn"
            onClick={() =>
              onConfirm(
                reason,
                generatedMessage,
                remarks
              )
            }
            disabled={
              action === "reject" &&
              reason === ""
            }
          >
            {action === "approve"
              ? "Approve Claim"
              : "Reject Claim"}
          </button>

        </div>

      </div>

    </div>

  );

}

export default AdminDecisionModal;