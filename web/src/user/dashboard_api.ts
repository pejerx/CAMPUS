import API_BASE_URL from "../api/api_config";

const REPORT_API_URL = `${API_BASE_URL}/api/reports`;

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
  const response = await fetch(
    `${REPORT_API_URL}/public`
  );

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
export async function getMyReports(
  userId: string
) {
  const response = await fetch(
    `${REPORT_API_URL}/user/${userId}`
  );

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
export async function getDashboardData(
  userId: string
) {
  const [publicReports, userReports] =
    await Promise.all([
      getPublicReports(),
      getMyReports(userId),
    ]);

  return {
    publicReports,
    userReports,
  };
}