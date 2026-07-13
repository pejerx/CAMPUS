import "../css/component_style.css";

import {
  Notification,
  markNotificationAsRead,
} from "./notification_api";

type Props = {
  notification: Notification;
  onRefresh: () => void;
};

function NotificationItem({
  notification,
  onRefresh,
}: Props) {

  const handleClick = async () => {

    try {

      if (!notification.isRead) {

        await markNotificationAsRead(
          notification.id
        );

        onRefresh();

      }

    } catch (error) {

      console.error(error);

    }

  };

  const formattedDate = new Date(
    notification.createdAt
  ).toLocaleString("en-PH", {
    dateStyle: "medium",
    timeStyle: "short",
  });

  return (

    <div
      className={`notification-item ${
        notification.isRead
          ? ""
          : "unread"
      }`}
      onClick={handleClick}
    >

      <div className="notification-content">

        <div className="notification-header">

          <h4>
            {notification.title}
          </h4>

          {!notification.isRead && (
            <span className="notification-dot"></span>
          )}

        </div>

        <p>
          {notification.message}
        </p>

        <small>
          {formattedDate}
        </small>

      </div>

    </div>

  );

}

export default NotificationItem;