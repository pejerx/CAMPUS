import { IconCircleCheck } from "@tabler/icons-react";
import { useNavigate } from "react-router-dom";

import "../css/style.css";
import "../css/component_style.css";

function ClaimSuccessPage() {
  const navigate = useNavigate();

  return (
    <div className="claim-success-page">
      <div className="claim-success-card">

        <div className="claim-success-icon">
          <IconCircleCheck size={90} stroke={1.5} />
        </div>

        <h1>Claim Request Submitted!</h1>

        <p>
          Thank you for submitting your claim request.
        </p>

        <p>
          Our Lost and Found Office will review the
          information you provided.
        </p>

        <p>
          You will be notified once your request has
          been approved or rejected.
        </p>

        <button
          className="explore-btn"
          onClick={() => navigate("/explore")}
        >
          Go to Homepage
        </button>

      </div>
    </div>
  );
}

export default ClaimSuccessPage;