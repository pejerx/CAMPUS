const ADMIN_API_URL = "http://localhost:8080/api/admin";

/**
 * Get all reports for the admin dashboard.
 */
export async function getAllReports() {
  const response = await fetch(`${ADMIN_API_URL}/reports`);

  if (!response.ok) {
    throw new Error("Failed to fetch reports.");
  }

  return response.json();
}

/**
 * Get all reported items.
 */
export async function getReportedItems() {
  const response = await fetch(`${ADMIN_API_URL}/reported-items`);

  if (!response.ok) {
    throw new Error("Failed to fetch reported items.");
  }

  return response.json();
}

/**
 * Approve a report.
 * Under Review -> Unclaimed
 */
export async function approveReport(id: string) {
  const response = await fetch(`${ADMIN_API_URL}/reports/${id}/approve`, {
    method: "PUT",
  });

  if (!response.ok) {
    throw new Error("Failed to approve report.");
  }

  return response.json();
}

/**
 * Reject a report.
 */
export async function rejectReport(id: string) {
  const response = await fetch(`${ADMIN_API_URL}/reports/${id}/reject`, {
    method: "PUT",
  });

  if (!response.ok) {
    throw new Error("Failed to reject report.");
  }

  return response.json();
}

/**
 * Update the status of an already-approved item.
 * Used when changing:
 *
 * Unclaimed
 * -> Pending Claim
 * -> Claimed
 */
export async function updateReportStatus(id: string, status: string) {
  const response = await fetch(`${ADMIN_API_URL}/reports/${id}/status`, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      status,
    }),
  });

  if (!response.ok) {
    throw new Error("Failed to update report status.");
  }

  return response.json();
}