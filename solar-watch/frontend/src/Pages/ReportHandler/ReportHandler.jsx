import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";

async function fetchSolarwatchReport(sunId, token) {
  const response = await fetch(`/api/solarwatch/${sunId}`,
    {
      headers: { "Authorization": `Bearer ${token}` }
    }
  );
  return await response.json();
}

async function updateSolarwatchReport(sunId, token, newReport) {
  const response = await fetch(`/api/solarwatch/update/${sunId}`,
    {
      method: "PATCH",
      headers: {
        "Content-Type": "application/json",
        "Authorization": `Bearer ${token}`,
      },
      body: JSON.stringify(newReport),
    }
  );
  return await response.json();
}

function ReportHandler() {
  const [cityName, setCityName] = useState("");
  const [latitude, setLatitude] = useState("");
  const [longitude, setLongitude] = useState("");
  const [country, setCountry] = useState("");
  const [sunrise, setSunrise] = useState("");
  const [sunset, setSunset] = useState("");
  const token = localStorage.getItem("token");
  const { sunId } = useParams();
  const navigate = useNavigate();

  useEffect(() => {
    async function handleFetchSolarwatchReport() {
      const response = await fetchSolarwatchReport(sunId, token);
      setCityName(response?.cityName || "");
      setLatitude(response?.latitude || "");
      setLongitude(response?.longitude || "");
      setCountry(response?.country || "");
      setSunrise(response?.sunrise || "");
      setSunset(response?.sunset || "");
    }
    handleFetchSolarwatchReport();
  }, [sunId, token]);


  async function handleSubmit(e) {
    e.preventDefault();
    const newReport = {
      cityName: cityName,
      latitude: latitude,
      longitude: longitude,
      country: country,
      sunrise: sunrise,
      sunset: sunset
    };
    await updateSolarwatchReport(sunId, token, newReport);
    navigate("/admin");
  }


  return <div className="report-handler">
    <h1>Report Handler</h1>
    <form className="report-form" onSubmit={handleSubmit}>
      <div className="input-box">
        <input
          type="text"
          placeholder="City"
          required
          value={cityName}
          onChange={(e) => setCityName(e.target.value)}
          id="city"
        />
      </div>
      <div className="input-box">
        <input
          type="text"
          placeholder="Latitude"
          required
          value={latitude}
          onChange={(e) => setLatitude(e.target.value)}
          id="latitude"
        />
      </div>
      <div className="input-box">
        <input
          type="text"
          placeholder="Longitude"
          required
          value={longitude}
          onChange={(e) => setLongitude(e.target.value)}
          id="Longitude"
        />
      </div>
      <div className="input-box">
        <input
          type="text"
          placeholder="country"
          required
          value={country}
          onChange={(e) => setCountry(e.target.value)}
          id="country"
        />
      </div>
      <div className="input-box">
        <input
          type="text"
          placeholder="sunrise"
          required
          value={sunrise}
          onChange={(e) => setSunrise(e.target.value)}
          id="sunrise"
        />
      </div>
      <div className="input-box">
        <input
          type="text"
          placeholder="sunset"
          required
          value={sunset}
          onChange={(e) => setSunset(e.target.value)}
          id="sunset"
        />
      </div>
      <button className="submit-button" type="submit">update</button>
    </form>
  </div>
}

export default ReportHandler;