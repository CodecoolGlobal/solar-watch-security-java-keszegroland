import { useEffect, useState } from "react";
import AdminCityTable from "../../Components/AdminCityTable/AdminCityTable";

async function fetchAllReports(options = {}) {
  const token = localStorage.getItem("token");
  const headers = {
    ...options.headers,
    "Authorization": `Bearer ${token}`
  };

  const response = await fetch("/api/solarwatch/getAllReports", { ...options, headers });
  return response.json();
}

async function deleteCityReport(cityReportId, options = {}) {
  const token = localStorage.getItem("token");
  const headers = {
    ...options.headers,
    "Authorization": `Bearer ${token}`
  };
  const response = await fetch(`/api/solarwatch/delete/${cityReportId}`,
    {
      method: "DELETE",
      headers
    }
  )
  return response;
}

function AdminPage() {
  const [reports, setReports] = useState(null);

  useEffect(() => {
    async function getAllReports() {
      const response = await fetchAllReports();
      setReports(response);
    }
    getAllReports();
  }, [])


  function handleDelete(cityReportId) {
    deleteCityReport(cityReportId);
    setReports((cityReports) => {
      return cityReports.filter((cityReport) => cityReport.id !== cityReportId);
    });
  }

  return <div className="admin-page">
    <h1>Admin Dashboard</h1>
    <AdminCityTable cityReports={reports} onDelete={handleDelete} />
  </div>
}

export default AdminPage;