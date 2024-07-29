import { useEffect, useState } from "react";
import AdminCityTable from "../../Components/AdminCityTable/AdminCityTable";

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
      headers: { "Authorization": `Bearer ${token}` }
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