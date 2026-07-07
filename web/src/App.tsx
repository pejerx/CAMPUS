import "./css/App.css";
import { Routes, Route } from "react-router-dom";
import '@shoelace-style/shoelace/dist/themes/light.css';
import { setBasePath } from '@shoelace-style/shoelace/dist/utilities/base-path.js';
import DashboardPage from "./pages/dashboard_page";
import UserDashboardPage from "./pages/user_dashboard";

setBasePath('https://cdn.jsdelivr.net/npm/@shoelace-style/shoelace@2.20.1/cdn/');

function App() {
  return (
    
    <Routes>
      <Route path="/" element={<DashboardPage />} />
      <Route path="/user-dashboard" element={<UserDashboardPage />} />
    </Routes>
  );
}

export default App;