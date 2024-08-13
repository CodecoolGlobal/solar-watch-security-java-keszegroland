import { useEffect, useState } from "react";
import AdminCityTable from "../../Components/AdminCityTable/AdminCityTable";
import "./adminPage.css";

async function fetchAllReports() {
  const token = localStorage.getItem("token");
  const response = await fetch("/api/solarwatch/getAllReports",
    {
      headers: { "Authorization": `Bearer ${token}` }
    }
  );
  return response.json();
}

async function deleteCityReport(cityReportId) {
  const token = localStorage.getItem("token");
  const response = await fetch(`/api/solarwatch/delete/${cityReportId}`,
    {
      method: "DELETE",
      headers: {
        "Content-Type": "application/json",
        "Authorization": `Bearer ${token}`
      }
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
    setReports((reports) => {
      return reports.filter((report) => report.cityId !== cityReportId);
    });
  }

  return <div className="admin-page">
    <h1 className="header">Admin Dashboard</h1>
    <AdminCityTable reports={reports} onDelete={handleDelete} />
  </div>
}

export default AdminPage;