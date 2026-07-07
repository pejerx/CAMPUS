const API_URL = "http://localhost:8080/api/reports";

export type ReportItemFormData = {
  userId: number;
  itemName: string;
  category: string;
  description: string;
  lastSeenLocation: string;
  imageFile: File | null;
};

export async function createReportItem(data: ReportItemFormData) {
  const formData = new FormData();

  formData.append("userId", String(data.userId));
  formData.append("itemName", data.itemName);
  formData.append("category", data.category);
  formData.append("description", data.description);
  formData.append("lastSeenLocation", data.lastSeenLocation);

  if (data.imageFile) {
    formData.append("image", data.imageFile);
  }

  const response = await fetch(API_URL, {
    method: "POST",
    body: formData,
  });

  if (!response.ok) {
    throw new Error("Failed to submit report");
  }

  return response.json();
}