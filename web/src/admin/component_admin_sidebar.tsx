import {
  IconClipboardText,
  IconHome,
  IconLogout,
  IconReport,
} from "@tabler/icons-react";
import { useNavigate } from "react-router-dom";

type Props = {
  active: "dashboard" | "reported-items" | "claim-requests" | "items";
};

function AdminSidebar({ active }: Props) {
  const navigate = useNavigate();

  const handleLogout = () => {
    localStorage.removeItem("user");
    localStorage.removeItem("admin");
    navigate("/", { replace: true });
  };

  return (
    <aside className="lf-sidebar">
      <div className="lf-profile">
        <div className="lf-avatar">A</div>
        <h3>Hi, Admin!</h3>
        <small>Welcome Back</small>
      </div>

      <nav className="lf-menu">
        <button
          className={active === "dashboard" ? "active" : ""}
          onClick={() => navigate("/admin-dashboard")}
        >
          <IconHome size={17} />
          Dashboard
        </button>

        <button
          className={active === "reported-items" ? "active" : ""}
          onClick={() => navigate("/admin-reported-items")}
        >
          <IconReport size={17} />
          Reported Items
        </button>

        <button
          className={active === "claim-requests" ? "active" : ""}
          onClick={() => navigate("/admin-claim-requests")}
        >
          <IconClipboardText size={17} />
          Claim Requests
        </button>

      </nav>

      <button className="lf-logout" onClick={handleLogout}>
        <IconLogout size={17} />
        Log Out
      </button>
    </aside>
  );
}

export default AdminSidebar;