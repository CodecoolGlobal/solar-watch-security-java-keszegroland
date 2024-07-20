function IndicateError({ errorMessage }) {

  return <div className="indicate-error">
    <h3>{errorMessage}</h3>
    <p>Try another city name.</p>
  </div>
}

export default IndicateError;