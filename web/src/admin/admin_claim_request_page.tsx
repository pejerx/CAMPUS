import {
  IconBell,
  IconChevronDown,
  IconSearch,
} from "@tabler/icons-react";

import { useEffect, useState } from "react";

import "../css/style.css";
import "../css/component_style.css";

import AdminSidebar from "./component_admin_sidebar";
import AdminClaimRequestCard from "./component_claim_request_card";

import {
  getClaimRequests,
  approveClaimRequest,
  rejectClaimRequest,
} from "./admin_claim_api";

type ClaimRequest = {
  id: number;
  claimantId?: string;
  claimantName?: string;
  claimantEmail?: string;
  claimantPhone?: string;

  itemDescription?: string;
  additionalInformation?: string;
  proofImagePath?: string;

  status?: string;

  itemReport?: {
    id: number;
    itemName?: string;
    reportType?: string;
    category?: string;
    location?: string;
    imagePath?: string;
  };
};

function AdminClaimRequestsPage() {
  const [claims, setClaims] = useState<ClaimRequest[]>([]);
  const [search, setSearch] = useState("");
  const [statusFilter, setStatusFilter] =
    useState("All");

  const loadClaimRequests = async () => {
    try {
      const data = await getClaimRequests();
      setClaims(data);
    } catch (error) {
      console.error(error);
      alert("Failed to load claim requests.");
    }
  };

  useEffect(() => {
    loadClaimRequests();
  }, []);

  const handleApprove = async (id: number) => {
    try {
      await approveClaimRequest(String(id));
      await loadClaimRequests();

      alert("Claim request approved.");
    } catch (error) {
      console.error(error);
      alert("Failed to approve claim request.");
    }
  };

  const handleReject = async (id: number) => {
    try {
      await rejectClaimRequest(String(id));
      await loadClaimRequests();

      alert("Claim request rejected.");
    } catch (error) {
      console.error(error);
      alert("Failed to reject claim request.");
    }
  };

  const filteredClaims = claims.filter((claim) => {
    const matchesSearch = `
      ${claim.claimantName}
      ${claim.claimantEmail}
      ${claim.itemReport?.itemName}
      ${claim.itemReport?.category}
      ${claim.status}
    `
      .toLowerCase()
      .includes(search.toLowerCase());

    const matchesStatus =
      statusFilter === "All" ||
      claim.status === statusFilter;

    return matchesSearch && matchesStatus;
  });

  return (
    <div className="lf-dashboard">
      <AdminSidebar active="claim-requests" />

      <main className="lf-main">

        <header className="lf-header">

          <div className="lf-search">

            <input
              type="text"
              placeholder="Search claim requests"
              value={search}
              onChange={(e) =>
                setSearch(e.target.value)
              }
            />

            <IconSearch size={18} />

          </div>

          <div className="lf-header-right">

            <IconBell size={21} />

            <div className="lf-user-chip">

              <div className="lf-small-avatar">
                A
              </div>

              <span>Admin</span>

              <IconChevronDown size={16} />

            </div>

          </div>

        </header>

        <section className="explore-header">

          <div>

            <h1>
              Claim Requests
            </h1>

            <p>
              Review ownership requests
              submitted by users before
              inviting them to visit the
              Lost and Found Office.
            </p>

          </div>

          <div className="explore-tabs">

            <button
              className={
                statusFilter === "All"
                  ? "active"
                  : ""
              }
              onClick={() =>
                setStatusFilter("All")
              }
            >
              All
            </button>

            <button
              className={
                statusFilter === "Pending"
                  ? "active"
                  : ""
              }
              onClick={() =>
                setStatusFilter("Pending")
              }
            >
              Pending
            </button>

            <button
              className={
                statusFilter === "Approved"
                  ? "active"
                  : ""
              }
              onClick={() =>
                setStatusFilter("Approved")
              }
            >
              Approved
            </button>

            <button
              className={
                statusFilter === "Rejected"
                  ? "active"
                  : ""
              }
              onClick={() =>
                setStatusFilter("Rejected")
              }
            >
              Rejected
            </button>

          </div>

        </section>

        <section className="explore-grid">

          {filteredClaims.map((claim) => (

            <AdminClaimRequestCard
              key={claim.id}
              claim={claim}
              onApprove={handleApprove}
              onReject={handleReject}
            />

          ))}

        </section>

        {filteredClaims.length === 0 && (

          <p className="explore-empty">
            No claim requests found.
          </p>

        )}

      </main>
    </div>
  );
}

export default AdminClaimRequestsPage;