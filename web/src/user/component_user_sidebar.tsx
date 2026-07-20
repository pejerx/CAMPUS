import {
  IconClipboardText,
  IconHome,
  IconLogout,
  IconPackage,
  IconReport,
  IconFileCheck,
} from "@tabler/icons-react";
import { useNavigate } from "react-router-dom";

type Props = {
  active: "home" | "explore" | "report" | "my-reports" | "my-claims";
  onReportClick: () => void;
};

function UserSidebar({ active, onReportClick }: Props) {
  const navigate = useNavigate();
  const user = JSON.parse(localStorage.getItem("user") || "{}");

  const formatRole = (role: string) => {
    return role
      .replace(/_/g, " ")
      .replace(/\b\w/g, (char) => char.toUpperCase());
  };

  const handleLogout = () => {
    localStorage.removeItem("user");
    localStorage.removeItem("admin");
    navigate("/", { replace: true });
  };

  return (
    <aside className="lf-sidebar">
      <div className="lf-profile">
      <div className="lf-avatar">
        {user.firstName?.charAt(0).toUpperCase() || "U"}
      </div>

      <h3>
        Hi, {user.firstName || "User"}
      </h3>

      <small>
        {user.role ? formatRole(user.role) : "User"}
      </small>

    </div>

      <nav className="lf-menu">
        <button
          className={active === "home" ? "active" : ""}
          onClick={() => navigate("/user-dashboard")}
        >
          <IconHome size={17} />
          Home
        </button>

        <button
          className={active === "explore" ? "active" : ""}
          onClick={() => navigate("/explore")}
        >
          <IconPackage size={17} />
          Explore
        </button>

        <button
          className={active === "report" ? "active" : ""}
          onClick={onReportClick}
        >
          <IconReport size={17} />
          Report Item
        </button>

        <button
          className={active === "my-reports" ? "active" : ""}
          onClick={() => navigate("/my-reports")}
        >
          <IconClipboardText size={17} />
          My Reports
        </button>

        <button
          className={active === "my-claims" ? "active" : ""}
          onClick={() => navigate("/my-claims")}
        >
          <IconFileCheck size={17} />
          My Claims
        </button>
      </nav>

      <button className="lf-logout" onClick={handleLogout}>
        <IconLogout size={17} />
        Log Out
      </button>
    </aside>
  );
}

export default UserSidebar;