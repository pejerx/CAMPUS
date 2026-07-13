import { useEffect, useRef, useState } from "react";

import {
  IconBell,
} from "@tabler/icons-react";

import "../css/component_style.css";

import NotificationItem from "./component_notification_item";

import {
  Notification,
  getNotifications,
  getUnreadNotificationCount,
  markAllNotificationsAsRead,
} from "./notification_api";

function NotificationDropdown() {

  const [notifications, setNotifications] =
    useState<Notification[]>([]);

  const [unreadCount, setUnreadCount] =
    useState(0);

  const [open, setOpen] =
    useState(false);

  const dropdownRef =
    useRef<HTMLDivElement>(null);

  const user = JSON.parse(
    localStorage.getItem("user") || "{}"
  );

  const loadNotifications =
    async () => {

      if (!user.id) return;

      try {

        const notificationData =
          await getNotifications(user.id);

        const count =
          await getUnreadNotificationCount(
            user.id
          );

        setNotifications(
          notificationData
        );

        setUnreadCount(count);

      } catch (error) {

        console.error(error);

      }

    };

  useEffect(() => {

    loadNotifications();

  }, []);

  useEffect(() => {

    const handleOutsideClick = (
      event: MouseEvent
    ) => {

      if (
        dropdownRef.current &&
        !dropdownRef.current.contains(
          event.target as Node
        )
      ) {

        setOpen(false);

      }

    };

    document.addEventListener(
      "mousedown",
      handleOutsideClick
    );

    return () => {

      document.removeEventListener(
        "mousedown",
        handleOutsideClick
      );

    };

  }, []);

  const handleMarkAll =
    async () => {

      try {

        await markAllNotificationsAsRead(
          user.id
        );

        loadNotifications();

      } catch (error) {

        console.error(error);

      }

    };

  return (

    <div
      className="notification-dropdown-container"
      ref={dropdownRef}
    >

      <button
        className="notification-bell-btn"
        onClick={() =>
          setOpen(!open)
        }
      >

        <IconBell size={21} />

        {unreadCount > 0 && (

          <span className="notification-badge">

            {unreadCount}

          </span>

        )}

      </button>

      {open && (

        <div className="notification-dropdown">

          <div className="notification-dropdown-header">

            <h3>
              Notifications
            </h3>

            {notifications.length > 0 && (

              <button
                className="notification-read-all"
                onClick={handleMarkAll}
              >
                Mark all as read
              </button>

            )}

          </div>

          {notifications.length === 0 ? (

            <div className="notification-empty">

              No notifications.

            </div>

          ) : (

            notifications.map(
              (notification) => (

                <NotificationItem
                  key={notification.id}
                  notification={
                    notification
                  }
                  onRefresh={
                    loadNotifications
                  }
                />

              )
            )

          )}

        </div>

      )}

    </div>

  );

}

export default NotificationDropdown;