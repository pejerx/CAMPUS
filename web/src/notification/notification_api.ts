const API_URL = "http://localhost:8080/api/notifications";

export type Notification = {
  id: number;
  userId: string;
  title: string;
  message: string;
  isRead: boolean;
  createdAt: string;
};

/*
 * Get all notifications of the logged-in user.
 */
export async function getNotifications(
  userId: string
): Promise<Notification[]> {

  const response = await fetch(
    `${API_URL}/user/${userId}`
  );

  if (!response.ok) {
    throw new Error(
      "Failed to load notifications."
    );
  }

  return response.json();
}

/*
 * Get unread notification count.
 */
export async function getUnreadNotificationCount(
  userId: string
): Promise<number> {

  const response = await fetch(
    `${API_URL}/user/${userId}/count`
  );

  if (!response.ok) {
    throw new Error(
      "Failed to load notification count."
    );
  }

  return response.json();
}

/*
 * Mark a single notification as read.
 */
export async function markNotificationAsRead(
  notificationId: number
) {

  const response = await fetch(
    `${API_URL}/${notificationId}/read`,
    {
      method: "PUT",
    }
  );

  if (!response.ok) {
    throw new Error(
      "Failed to mark notification as read."
    );
  }

  return response.json();
}

/*
 * Mark every notification as read.
 */
export async function markAllNotificationsAsRead(
  userId: string
) {

  const response = await fetch(
    `${API_URL}/user/${userId}/read-all`,
    {
      method: "PUT",
    }
  );

  if (!response.ok) {
    throw new Error(
      "Failed to mark notifications as read."
    );
  }

  return true;
}