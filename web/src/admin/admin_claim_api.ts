const CLAIM_API_URL =
  "http://localhost:8080/api/claim-requests";

export async function getClaimRequests() {
  const response = await fetch(CLAIM_API_URL);

  if (!response.ok) {
    const message = await response.text();

    throw new Error(
      message || "Failed to fetch claim requests."
    );
  }

  return response.json();
}

export async function approveClaimRequest(
  id: string
) {
  const response = await fetch(
    `${CLAIM_API_URL}/${encodeURIComponent(id)}/approve`,
    {
      method: "PUT",
    }
  );

  if (!response.ok) {
    const message = await response.text();

    throw new Error(
      message || "Failed to approve claim request."
    );
  }

  return response.json();
}

export async function rejectClaimRequest(
  id: string
) {
  const response = await fetch(
    `${CLAIM_API_URL}/${encodeURIComponent(id)}/reject`,
    {
      method: "PUT",
    }
  );

  if (!response.ok) {
    const message = await response.text();

    throw new Error(
      message || "Failed to reject claim request."
    );
  }

  return response.json();
}