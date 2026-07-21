import "./css/App.css";
import { Routes, Route } from "react-router-dom";
import '@shoelace-style/shoelace/dist/themes/light.css';
import { setBasePath } from '@shoelace-style/shoelace/dist/utilities/base-path.js';
import DashboardPage from "./dashboard/dashboard_page";
import UserDashboardPage from "./user/user_dashboard";
import AdminDashboardPage from "./admin/admin_dashboard";
import ExplorePage from "./explore/explore_page";
import AdminReportedItemsPage from "./admin/admin_reported_items";
import ClaimRequestPage from "./request-claim/claim_item_page";
import ClaimSuccessPage from "./request-claim/form_claim_success_page";
import AdminClaimRequestsPage from "./admin/admin_claim_request_page";
import MyReportsPage from "./user/my_reports_page";
import MyClaimsPage from "./user/claim_request_page";

setBasePath('https://cdn.jsdelivr.net/npm/@shoelace-style/shoelace@2.20.1/cdn/');

function App() {
  return (
    
    <Routes>
      <Route path="/" element={<DashboardPage />} />
      <Route path="/user-dashboard" element={<UserDashboardPage />} />
      <Route path="/explore" element={<ExplorePage />} />
      <Route path="/claim-request" element={<ClaimRequestPage />} />
      <Route path="/claim-success" element={<ClaimSuccessPage />} />
      <Route path="/my-reports" element={<MyReportsPage />} />
      <Route path="/my-claims" element={<MyClaimsPage />} />

      <Route path="/admin-dashboard" element={<AdminDashboardPage />} />
      <Route path="/admin-reported-items" element={<AdminReportedItemsPage />} />
      <Route path="/admin-claim-requests" element={<AdminClaimRequestsPage />} />
      
    </Routes>
  );
}

export default App;