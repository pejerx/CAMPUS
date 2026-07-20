import {
  IconBell,
  IconChevronDown,
  IconSearch,
} from "@tabler/icons-react";

import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

import "../css/style.css";
import "../css/component_style.css";

import UserSidebar from "../user/component_user_sidebar";
import ReportItemModal from "../item-report/report_item_form";

import { getUserClaimRequests } from "./claim_request_api";

type ItemReport = {
  id: number;
  itemName?: string;
  reportType?: string;
  category?: string;
  location?: string;
  lastSeenLocation?: string;
  description?: string;
  imagePath?: string;
  imageUrl?: string;
  status?: string;
};

type ClaimRequest = {
  id: number;
  claimantId: string;
  claimantName?: string;
  claimantEmail?: string;
  claimantPhone?: string;
  itemDescription?: string;
  additionalInformation?: string;
  proofImagePath?: string;
  status?: string;
  createdAt?: string;

  itemReport: ItemReport;
};

function ClaimRequestPage() {

  const navigate = useNavigate();

  const user = JSON.parse(
    localStorage.getItem("user") || "{}"
  );

  const [claimRequests, setClaimRequests] =
    useState<ClaimRequest[]>([]);

  const [search, setSearch] =
    useState("");

  const [filter, setFilter] =
    useState("All");

  const [loading, setLoading] =
    useState(true);

  const [showReportModal, setShowReportModal] =
    useState(false);

  const handleLogout = () => {

    localStorage.removeItem("user");

    navigate("/", {
      replace: true,
    });

  };

  const loadClaimRequests =
    async () => {

      try {

        const data =
          await getUserClaimRequests(
            user.id
          );

        setClaimRequests(data);

      } catch (error) {

        console.error(error);

        alert(
          "Failed to load your claim requests."
        );

      } finally {

        setLoading(false);

      }

    };

  useEffect(() => {

    if (user?.id) {

      loadClaimRequests();

    }

  }, []);

  const filteredClaims =
    claimRequests.filter((claim) => {

      const matchesSearch = `
        ${claim.itemReport?.itemName}
        ${claim.itemReport?.category}
        ${claim.status}
      `
        .toLowerCase()
        .includes(
          search.toLowerCase()
        );

      const matchesFilter =
        filter === "All" ||
        claim.status === filter;

      return (
        matchesSearch &&
        matchesFilter
      );

    });

      return (
    <div className="lf-dashboard">

      <UserSidebar
        active="my-claims"
        onReportClick={() =>
          setShowReportModal(true)
        }
      />

      <main className="lf-main">

        <header className="lf-header">

          <div className="lf-search">

            <input
              type="text"
              placeholder="Search my claim requests..."
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
                {user.firstName?.charAt(0).toUpperCase() || "U"}
              </div>

              <span>
                {user.firstName || "User"}
              </span>

              <IconChevronDown size={16} />

            </div>

          </div>

        </header>

        <section className="explore-header">

          <div>

            <h1>
              My Claim Requests
            </h1>

            <p>
              View and track the status of all claim requests
              you have submitted.
            </p>

          </div>

          <div className="explore-tabs">

            <button
              className={
                filter === "All"
                  ? "active"
                  : ""
              }
              onClick={() =>
                setFilter("All")
              }
            >
              All
            </button>

            <button
              className={
                filter === "Pending"
                  ? "active"
                  : ""
              }
              onClick={() =>
                setFilter("Pending")
              }
            >
              Pending
            </button>

            <button
              className={
                filter === "Approved"
                  ? "active"
                  : ""
              }
              onClick={() =>
                setFilter("Approved")
              }
            >
              Approved
            </button>

            <button
              className={
                filter === "Rejected"
                  ? "active"
                  : ""
              }
              onClick={() =>
                setFilter("Rejected")
              }
            >
              Rejected
            </button>

          </div>

        </section>

        <section className="explore-grid">

          {filteredClaims.map((claim) => {

            const item =
              claim.itemReport;

            const imageSource =
              item?.imageUrl
                ? item.imageUrl
                : item?.imagePath
                ? item.imagePath.startsWith("http")
                  ? item.imagePath
                  : `http://localhost:8080/${item.imagePath}`
                : "";

            return (

              <div
                className="explore-card"
                key={claim.id}
              >

                <div className="explore-image">

                  {imageSource ? (

                    <img
                      src={imageSource}
                      alt={item?.itemName}
                    />

                  ) : (

                    <span>📦</span>

                  )}

                </div>

                <div className="explore-info">

                  <div className="explore-meta">

                    <span>
                      {item?.category}
                    </span>

                    <span>
                      {item?.location ||
                        item?.lastSeenLocation}
                    </span>

                  </div>

                  <h3>
                    {item?.itemName}
                  </h3>

                  <p className="explore-description">

                    {claim.itemDescription}

                  </p>

                  <div className="explore-footer">

                    <span
                      className={
                        item?.reportType === "Lost"
                          ? "lf-badge lost"
                          : "lf-badge found"
                      }
                    >
                      {item?.reportType}
                    </span>

                    <span className="explore-status">

                      {claim.status}

                    </span>

                  </div>

                  <small
                    style={{
                      color: "#777",
                      display: "block",
                      marginTop: "10px",
                    }}
                  >
                    Submitted on{" "}
                    {claim.createdAt
                      ? new Date(
                          claim.createdAt
                        ).toLocaleDateString()
                      : "-"}
                  </small>

                  <button
                    className="explore-btn outline"
                  >
                    View Details
                  </button>

                </div>

              </div>

            );

          })}

        </section>

                {loading && (
          <div className="explore-empty">
            Loading your claim requests...
          </div>
        )}

        {!loading &&
          filteredClaims.length === 0 && (
            <div className="explore-empty">
              <h3>No Claim Requests Found</h3>

              <p>
                You haven't submitted any claim requests yet.
              </p>

              <button
                className="explore-btn"
                onClick={() => navigate("/explore")}
              >
                Browse Found Items
              </button>
            </div>
          )}

        {showReportModal && (
          <ReportItemModal
            onClose={() =>
              setShowReportModal(false)
            }
          />
        )}

      </main>

    </div>

  );

}

export default ClaimRequestPage;