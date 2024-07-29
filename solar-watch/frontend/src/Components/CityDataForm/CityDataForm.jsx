import { useState } from "react";
import IndicateError from "../IndicateError/IndicateError";
import CityDataTable from "../CityDataTable/CityDataTable";
import "./cityDataForm.css";

async function fetchInfosForSearchedCity(cityName) {
  const token = localStorage.getItem("token");
  console.log(token);
  try {
    const response = await fetch(`/api/solarwatch?city=${cityName}`,
      {
        headers: { "Authorization": `Bearer ${token}` }
      },
    );
    return await response.json();
  } catch (error) {
    throw new Error("This city name is not valid.");
  }
}

function CityDataForm() {
  const [cityName, setCityName] = useState("");
  const [cityReport, setCityReport] = useState(null);
  const [error, setError] = useState(null);


  async function handleSubmit(e) {
    e.preventDefault();
    try {
      setError(null);
      const result = await fetchInfosForSearchedCity(cityName);
      setCityReport(result);
    } catch (error) {
      setCityReport(null);
      setError(error.message);
    }
  }


  return <div className="city-data-form-container">
    <form className="city-data-form" onSubmit={handleSubmit}>
      <input type="text" placeholder="City Name" onChange={(e) => setCityName(e.target.value)} id="search-city-input"></input>
      <button className="city-data-form-button" type="submit">Get Report!</button>
    </form>
    {error && <IndicateError errorMessage={error} />}
    {cityReport && <CityDataTable cityReport={cityReport} />}
  </div>
}

export default CityDataForm;