import "../css/component_style.css";
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
  createdAt?: string;

  itemReport?: {
    id: number;
    itemName?: string;
    reportType?: string;
    category?: string;
    location?: string;
    lastSeenLocation?: string;
    imagePath?: string;
    imageUrl?: string;
  };
};

type Props = {
  claim: ClaimRequest;
  onClose: () => void;
  onApprove: () => void;
  onReject: () => void;
};

function ClaimDetailsModal({
  claim,
  onClose,
  onApprove,
  onReject,
}: Props) {

  const item = claim.itemReport;

  const itemImage =
    item?.imageUrl
      ? item.imageUrl
      : item?.imagePath
      ? item.imagePath.startsWith("http")
        ? item.imagePath
        : `${API_BASE_URL}/${item.imagePath}`
      : "";

  const proofImage =
    claim.proofImagePath
      ? claim.proofImagePath.startsWith("http")
        ? claim.proofImagePath
        : `${API_BASE_URL}/${claim.proofImagePath}`
      : "";

  const location =
    item?.location ||
    item?.lastSeenLocation ||
    "No location provided";

  const formattedDate = claim.createdAt
    ? new Date(claim.createdAt).toLocaleString()
    : "N/A";

  return (
    <div className="modal-overlay">
    <div className="claim-details-modal">

        <button
        className="modal-close"
        onClick={onClose}
        >
        ✕
        </button>

        <div className="claim-details-layout">

        {/* LEFT */}

        <div className="claim-item-section">

            <h3 className="claim-section-title">
            Reported Item
            </h3>

            {itemImage ? (
            <img
                src={itemImage}
                alt={item?.itemName}
                className="claim-item-image"
            />
            ) : (
            <div className="claim-item-placeholder">
                📦
            </div>
            )}

            <h2>{item?.itemName}</h2>

            <span
            className={
                item?.reportType === "Lost"
                ? "lf-badge lost"
                : "lf-badge found"
            }
            >
            {item?.reportType}
            </span>

            <p className="claim-item-info">
            <strong>Category</strong>
            <br />
            {item?.category}
            </p>

            <p className="claim-item-info">
            <strong>Location</strong>
            <br />
            {location}
            </p>

        </div>

        {/* RIGHT */}

        <div className="claim-request-section">

            <h2 className="claim-title">
            Claim Request Details
            </h2>

            <div className="claim-info-grid">

            <strong>Claim ID</strong>
            <span>{claim.id}</span>

            <strong>Student ID</strong>
            <span>{claim.claimantId}</span>

            <strong>Claimant</strong>
            <span>{claim.claimantName}</span>

            <strong>Email</strong>
            <span>{claim.claimantEmail}</span>

            <strong>Contact No.</strong>
            <span>{claim.claimantPhone}</span>

            <strong>Date Submitted</strong>
            <span>{formattedDate}</span>

            <strong>Status</strong>
            <span>{claim.status}</span>

            </div>

            <hr />

            <h3 className="claim-section-title">
            Claimant Description
            </h3>

            <p className="claim-text">
            {claim.itemDescription}
            </p>

            <h3 className="claim-section-title">
            Additional Information
            </h3>

            <p className="claim-text">
            {claim.additionalInformation || "None"}
            </p>

            <h3 className="claim-section-title">
            Proof of Ownership
            </h3>

            {proofImage ? (
            <img
                src={proofImage}
                alt="Proof"
                className="claim-proof-image"
            />
            ) : (
            <p>No image uploaded.</p>
            )}

            {claim.status === "Pending" ? (

            <div className="claim-actions">

                <button
                className="explore-btn outline"
                onClick={onReject}
                >
                Reject
                </button>

                <button
                className="explore-btn"
                onClick={onApprove}
                >
                Approve
                </button>
            </div>

            ) : (

            <div className="claim-actions">

                <button
                className="explore-btn"
                onClick={onClose}
                >
                Close
                </button>
            </div>
            )}
        </div>
        </div>
    </div>
    </div>
  );
}

export default ClaimDetailsModal;