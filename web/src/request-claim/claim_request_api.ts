import API_BASE_URL from "../api/api_config";

const CLAIM_REQUEST_API_URL =
  `${API_BASE_URL}/api/claim-requests`;
/*
 * Submit a claim request.
 */
export async function submitClaimRequest(
  formData: FormData
) {
  const response = await fetch(
    CLAIM_REQUEST_API_URL,
    {
      method: "POST",
      body: formData,
    }
  );

  if (!response.ok) {
    const message = await response.text();

    throw new Error(
      message || "Failed to submit claim request."
    );
  }

  return response.json();
}

/*
 * Admin - View all claim requests.
 */
export async function getAllClaimRequests() {
  const response = await fetch(
    CLAIM_REQUEST_API_URL
  );

  if (!response.ok) {
    const message = await response.text();

    throw new Error(
      message || "Failed to fetch claim requests."
    );
  }

  return response.json();
}

/*
 * User - View their own claim requests.
 */
export async function getUserClaimRequests(
  claimantId: string
) {
  const response = await fetch(
    `${CLAIM_REQUEST_API_URL}/user/${encodeURIComponent(
      claimantId
    )}`
  );

  if (!response.ok) {
    const message = await response.text();

    throw new Error(
      message || "Failed to fetch your claim requests."
    );
  }

  return response.json();
}

/*
 * Admin - Update claim request status.
 */
export async function updateClaimRequestStatus(
  id: number,
  status: string
) {
  const response = await fetch(
    `${CLAIM_REQUEST_API_URL}/${id}/status?status=${encodeURIComponent(
      status
    )}`,
    {
      method: "PUT",
    }
  );

  if (!response.ok) {
    const message = await response.text();

    throw new Error(
      message || "Failed to update claim request."
    );
  }

  return response.json();
}