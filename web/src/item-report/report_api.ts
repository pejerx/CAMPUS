import API_BASE_URL from "../api/api_config";

const REPORT_API_URL = `${API_BASE_URL}/api/reports`;

type CreateReportItemData = {
  userId: string;
  reportType: "LOST" | "FOUND";
  itemName: string;
  category: string;
  description: string;
  location: string;
  imageFile?: File | null;
  imageUrl?: string;
};

/*
 * ==========================================================
 * CREATE REPORT
 * ==========================================================
 */
export async function createReportItem(
  data: CreateReportItemData
) {
  const formData = new FormData();

  formData.append("userId", data.userId);
  formData.append("reportType", data.reportType);
  formData.append("itemName", data.itemName);
  formData.append("category", data.category);
  formData.append("description", data.description);
  formData.append("location", data.location);

  if (data.imageFile) {
    formData.append("image", data.imageFile);
  }

  if (data.imageUrl) {
    formData.append("imageUrl", data.imageUrl);
  }

  const response = await fetch(REPORT_API_URL, {
    method: "POST",
    body: formData,
  });

  if (!response.ok) {
    throw new Error("Failed to create report.");
  }

  return response.json();
}

/*
 * ==========================================================
 * GET PUBLIC REPORTS
 *
 * Used by:
 * - Dashboard
 * - Explore Page
 * ==========================================================
 */
export async function getPublicReports() {
  const response = await fetch(
    `${REPORT_API_URL}/public`
  );

  if (!response.ok) {
    throw new Error(
      "Failed to fetch public reports."
    );
  }

  return response.json();
}

/*
 * ==========================================================
 * GET USER REPORTS
 * ==========================================================
 */
export async function getMyReports(
  userId: string
) {
  const response = await fetch(
    `${REPORT_API_URL}/user/${userId}`
  );

  if (!response.ok) {
    throw new Error(
      "Failed to fetch your reports."
    );
  }

  return response.json();
}

/*
 * ==========================================================
 * DASHBOARD DATA
 *
 * Loads everything needed by the dashboard.
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

/*
 * ==========================================================
 * UPDATE REPORT
 * ==========================================================
 */
export async function updateReport(
  id: number,
  data: {
    itemName: string;
    category: string;
    description: string;
    location: string;
  }
) {
  const response = await fetch(
    `${REPORT_API_URL}/${id}`,
    {
      method: "PUT",
      headers: {
        "Content-Type":
          "application/json",
      },
      body: JSON.stringify(data),
    }
  );

  if (!response.ok) {
    throw new Error(
      "Failed to update report."
    );
  }

  return response.json();
}

/*
 * ==========================================================
 * DELETE REPORT
 * ==========================================================
 */
export async function deleteReport(
  id: number
) {
  const response = await fetch(
    `${REPORT_API_URL}/${id}`,
    {
      method: "DELETE",
    }
  );

  if (!response.ok) {
    throw new Error(
      "Failed to delete report."
    );
  }
}