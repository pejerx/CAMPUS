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

export async function createReportItem(data: CreateReportItemData) {
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

  const response = await fetch("http://localhost:8080/api/reports", {
    method: "POST",
    body: formData,
  });

  if (!response.ok) {
    throw new Error("Failed to create report");
  }

  return response.json();
}

export async function getMyReports(userId: string) {
  const response = await fetch(
    `http://localhost:8080/api/reports/user/${userId}`
  );

  if (!response.ok) {
    throw new Error("Failed to fetch your reports.");
  }

  return response.json();
}

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
    `http://localhost:8080/api/reports/${id}`,
    {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(data),
    }
  );

  if (!response.ok) {
    throw new Error("Failed to update report.");
  }

  return response.json();
}

export async function deleteReport(
  id: number
) {
  const response = await fetch(
    `http://localhost:8080/api/reports/${id}`,
    {
      method: "DELETE",
    }
  );

  if (!response.ok) {
    throw new Error("Failed to delete report.");
  }
}