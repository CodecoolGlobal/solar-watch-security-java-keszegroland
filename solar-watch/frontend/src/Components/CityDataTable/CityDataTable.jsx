import "./cityDataTable.css";

function CityDataTable({ cityReport }) {

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
        </tr>
      </thead>
      <tbody>
        <tr>
          <td>{cityReport.city.name}</td>
          <td>{cityReport.city.lat}</td>
          <td>{cityReport.city.lon}</td>
          <td>{cityReport.city.country}</td>
          <td>{cityReport.sunrise}</td>
          <td>{cityReport.sunset}</td>
        </tr>
      </tbody>
    </table>
  </div>
}

export default CityDataTable;