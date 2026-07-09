const ADMIN_API_URL = "http://localhost:8080/api/admin";

export async function getAllReports() {
  const response = await fetch(`${ADMIN_API_URL}/reports`);

  if (!response.ok) {
    throw new Error("Failed to fetch reports");
  }

  return response.json();
}

export async function getReportedItems() {
  const response = await fetch(`${ADMIN_API_URL}/reported-items`);

  if (!response.ok) {
    throw new Error("Failed to fetch reported items");
  }

  return response.json();
}

export async function updateReportStatus(id: number, status: string) {
  const response = await fetch(`${ADMIN_API_URL}/reports/${id}/status`, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ status }),
  });

  if (!response.ok) {
    throw new Error("Failed to update report status");
  }

  return response.json();
}