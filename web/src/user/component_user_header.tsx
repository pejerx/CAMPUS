import {
  IconSearch,
} from "@tabler/icons-react";

import { useNavigate } from "react-router-dom";

import NotificationDropdown from "../notification/component_notification_dropdown"; 

function UserHeader() {

  const navigate = useNavigate();

  return (

    <header className="lf-header">

      <div className="lf-search">

        <input
          type="text"
          placeholder="Search public items..."
          onFocus={() =>
            navigate("/explore")
          }
          readOnly
        />
        <IconSearch size={18} />
      </div>
      <NotificationDropdown />

    </header>

  );

}

export default UserHeader;