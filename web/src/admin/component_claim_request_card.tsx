import { useState } from "react";

import ClaimDetailsModal from "./component_claim_details_modal";
import API_BASE_URL from "../api/api_config";

type ClaimRequest = {
  id: number;
  claimantId?: string;
  claimantName?: string;
  claimantEmail?: string;
  claimantPhone?: string;

  itemDescription?: string;
  additionalInformation?: string;
  proofImagePath?: string;

  status?: string;

  itemReport?: {
    id: number;
    itemName?: string;
    reportType?: string;
    category?: string;
    location?: string;
    imagePath?: string;
  };
};

type Props = {
  claim: ClaimRequest;
  onApprove: (id: number) => void;
  onReject: (id: number) => void;
};

function AdminClaimRequestCard({
  claim,
  onApprove,
  onReject,
}: Props) {
  const [showDetails, setShowDetails] =
    useState(false);

  const item = claim.itemReport;

  const imageSource = item?.imagePath
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
              alt={item?.itemName || "Item"}
            />
          ) : (
            <span>📦</span>
          )}
        </div>

        <div className="explore-info">

          <div className="explore-meta">
            <span>
              {item?.category ||
                "Uncategorized"}
            </span>

            <span>
              {claim.claimantName}
            </span>
          </div>

          <h3>
            {item?.itemName ||
              "Unnamed Item"}
          </h3>

          <p className="explore-description">
            Claim Request by{" "}
            <strong>
              {claim.claimantName}
            </strong>
          </p>

          <div className="explore-footer">

            <span
              className={
                item?.reportType === "Lost"
                  ? "lf-badge lost"
                  : "lf-badge found"
              }
            >
              {item?.reportType}
            </span>

            <span className="explore-status">
              {claim.status}
            </span>

          </div>

          {claim.status === "Pending" ? (
            <button
              className="explore-btn"
              onClick={() =>
                setShowDetails(true)
              }
            >
              Review Request
            </button>
          ) : (
            <button
              className="explore-btn outline"
              onClick={() =>
                setShowDetails(true)
              }
            >
              View Details
            </button>
          )}

        </div>
      </div>

      {showDetails && (
        <ClaimDetailsModal
          claim={claim}
          onClose={() =>
            setShowDetails(false)
          }
          onApprove={() => {
            onApprove(claim.id);
            setShowDetails(false);
          }}
          onReject={() => {
            onReject(claim.id);
            setShowDetails(false);
          }}
        />
      )}
    </>
  );
}

export default AdminClaimRequestCard;