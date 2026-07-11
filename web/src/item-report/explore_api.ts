const API_URL = "http://localhost:8080/api/reports";

export async function getPublicReports() {
  const response = await fetch(`${API_URL}/public`);

  if (!response.ok) {
    throw new Error("Failed to fetch public reports");
  }

  return response.json();
}