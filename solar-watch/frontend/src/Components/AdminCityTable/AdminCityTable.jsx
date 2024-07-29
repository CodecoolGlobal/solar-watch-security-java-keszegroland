import { Link } from "react-router-dom";
import "./adminCityTable.css";

function AdminCityTable({ reports, onDelete }) {
  return <div className="city-data-table">
    <table>
      <thead>
        <tr>
          <th>City</th>
          <th>Latitude</th>
          <th>Longitude</th>
          <th>Country</th>
          <th>Sunrise</th>
          <th>Sunset</th>
          <th />
        </tr>
      </thead>
      <tbody>
        {reports && reports.map(report => (
          <tr key={report.id}>
            <td>{report.city.name}</td>
            <td>{report.city.lat}</td>
            <td>{report.city.lon}</td>
            <td>{report.city.country}</td>
            <td>{report.sunrise}</td>
            <td>{report.sunset}</td>
            <td>
              <Link to={`/admin/update/${report.id}`}>
                <button type="button">Update</button>
              </Link>
              <button type="button" onClick={() => onDelete(report.city.id)}>Delete</button>
            </td>
          </tr>
        ))}
      </tbody>
    </table>
  </div>
}

export default AdminCityTable;