import { Link } from "react-router-dom";
import "./adminCityTable.css";

function AdminCityTable({ cityReports, onDelete }) {
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
        {cityReports && cityReports.map(cityReport => (
          <tr key={cityReport.id}>
            <td>{cityReport.city.name}</td>
            <td>{cityReport.city.lat}</td>
            <td>{cityReport.city.lon}</td>
            <td>{cityReport.city.country}</td>
            <td>{cityReport.sunrise}</td>
            <td>{cityReport.sunset}</td>
            <td>
              <Link to={`/admin/update/${cityReport.id}`}>
                <button type="button">Update</button>
              </Link>
              <button type="button" onClick={() => onDelete(cityReport.id)}>Delete</button>
            </td>
          </tr>
        ))}
      </tbody>
    </table>
  </div>
}

export default AdminCityTable;