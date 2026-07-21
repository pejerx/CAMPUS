import API_BASE_URL from "../api/api_config";

const REPORT_API_URL = `${API_BASE_URL}/api/reports`;
export async function getPublicReports() {
  const response = await fetch(`${REPORT_API_URL}/public`);

  if (!response.ok) {
    throw new Error("Failed to fetch public reports");
  }

  return response.json();
}