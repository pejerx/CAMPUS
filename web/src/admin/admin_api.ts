const ADMIN_API_URL = "http://localhost:8080/api/admin";

/*
 * These are the only statuses that can exist
 * after a report has already been approved.
 */
export type PublicItemStatus =
  | "Unclaimed"
  | "Pending Claim"
  | "Claimed";

/*
 * Get all reports.
 */
export async function getAllReports() {
  const response = await fetch(`${ADMIN_API_URL}/reports`);

  if (!response.ok) {
    const message = await response.text();

    throw new Error(
      message || "Failed to fetch reports."
    );
  }

  return response.json();
}

/*
 * Get all reported items.
 */
export async function getReportedItems() {
  const response = await fetch(
    `${ADMIN_API_URL}/reported-items`
  );

  if (!response.ok) {
    const message = await response.text();

    throw new Error(
      message || "Failed to fetch reported items."
    );
  }

  return response.json();
}

/*
 * ==========================================================
 * APPROVE REPORT
 *
 * Under Review
 *        ↓
 *   Unclaimed
 * ==========================================================
 */
export async function approveReport(id: string) {
  const response = await fetch(
    `${ADMIN_API_URL}/reports/${encodeURIComponent(id)}/approve`,
    {
      method: "PUT",
    }
  );

  if (!response.ok) {
    const message = await response.text();

    throw new Error(
      message || "Failed to approve report."
    );
  }

  return response.json();
}

/*
 * ==========================================================
 * REJECT REPORT
 *
 * Under Review
 *        ↓
 *   Rejected
 * ==========================================================
 */
export async function rejectReport(id: string) {
  const response = await fetch(
    `${ADMIN_API_URL}/reports/${encodeURIComponent(id)}/reject`,
    {
      method: "PUT",
    }
  );

  if (!response.ok) {
    const message = await response.text();

    throw new Error(
      message || "Failed to reject report."
    );
  }

  return response.json();
}

/*
 * ==========================================================
 * UPDATE STATUS
 *
 * Only used AFTER a report has already been
 * approved.
 *
 * Allowed values:
 *
 * Unclaimed
 * Pending Claim
 * Claimed
 * ==========================================================
 */
export async function updateReportStatus(
  id: string,
  status: PublicItemStatus
) {
  const response = await fetch(
    `${ADMIN_API_URL}/reports/${encodeURIComponent(id)}/status`,
    {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        status,
      }),
    }
  );

  if (!response.ok) {
    const message = await response.text();

    throw new Error(
      message || "Failed to update report status."
    );
  }

  return response.json();
}