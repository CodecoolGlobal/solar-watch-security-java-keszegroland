import { useState } from "react";

function ReportHandler() {
  const [city, setCity] = useState("");
  const [latitude, setLatitude] = useState("");
  const [longitude, setLongitude] = useState("");
  const [country, setCountry] = useState("");
  const [sunrise, setSunrise] = useState("");
  const [sunset, setSunset] = useState("");

  function handleSubmit() {

  }


  return <div className="report-handler">
    <h1>Report Handler</h1>
    <form className="report-form" onSubmit={handleSubmit}>
      <div className="input-box">
        <input
          type="text"
          placeholder="City"
          required
          onChange={(e) => setCity(e.target.value)}
          id="city"
        />
      </div>
      <div className="input-box">
        <input
          type="text"
          placeholder="Latitude"
          required
          onChange={(e) => setLatitude(e.target.value)}
          id="latitude"
        />
      </div>
      <div className="input-box">
        <input
          type="text"
          placeholder="Longitude"
          required
          onChange={(e) => setLongitude(e.target.value)}
          id="Longitude"
        />
      </div>
      <div className="input-box">
        <input
          type="text"
          placeholder="country"
          required
          onChange={(e) => setCountry(e.target.value)}
          id="country"
        />
      </div>
      <div className="input-box">
        <input
          type="text"
          placeholder="sunrise"
          required
          onChange={(e) => setSunrise(e.target.value)}
          id="sunrise"
        />
      </div>
      <div className="input-box">
        <input
          type="text"
          placeholder="sunset"
          required
          onChange={(e) => setSunset(e.target.value)}
          id="sunset"
        />
      </div>
      <button className="submit-button" type="submit">update</button>
    </form>
  </div>
}

export default ReportHandler;