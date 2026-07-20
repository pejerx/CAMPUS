const API_URL = "http://localhost:8080/api/reports";

/*
 * ==========================================================
 * PUBLIC REPORTS
 *
 * Used for:
 * - Dashboard Popular Items
 * - Dashboard Statistics
 * ==========================================================
 */

export async function getPublicReports() {
  const response = await fetch(`${API_URL}/public`);

  if (!response.ok) {
    throw new Error("Failed to fetch public reports.");
  }

  return response.json();
}

/*
 * ==========================================================
 * USER REPORTS
 *
 * Used for:
 * - Report History
 * ==========================================================
 */

export async function getUserReports(userId: string) {
  const response = await fetch(`${API_URL}/user/${userId}`);

  if (!response.ok) {
    throw new Error("Failed to fetch user reports.");
  }

  return response.json();
}

/*
 * ==========================================================
 * DASHBOARD DATA
 *
 * Loads both requests simultaneously.
 * ==========================================================
 */

export async function getDashboardData(userId: string) {
  const [publicReports, userReports] = await Promise.all([
    getPublicReports(),
    getUserReports(userId),
  ]);

  return {
    publicReports,
    userReports,
  };
}