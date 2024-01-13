import './App.css';
import React, { useState } from 'react';
import {useEffect} from 'react';
import axios from 'axios';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import { Line } from 'react-chartjs-2';
//import Chart from "chart.js";
import Chart from 'chart.js/auto';
import {CategoryScale} from 'chart.js';
Chart.register(CategoryScale);
//
//import { CategoryScale } from "chart.js";
//Chart.register(CategoryScale);

//import Chart from './Chart';
const App = () => {
  useEffect(() => {
    fetchFirstData();
  }, []);
  const [startDate, setStartDate] = useState(null);
  const [endDate, setEndDate] = useState(null);
  const [responseData, setResponseData] = useState(null);
  const [startData, setStartData] = useState(null);

  const fetchFirstData = () => {
    const dataToSend = {
      cityId: 1595,
      startDate: "2023-12-10",
      endDate: "2024-01-08",
    };
    axios.post('http://localhost:8080/api/punkt-id-data', dataToSend)
        .then(response => {
          console.log(response.data);
          setStartData(response.data);
        })
        .catch(error => {
          console.error('Błąd podczas wysyłania żądania POST:', error);
        });
  };
  const handleStartDateChange = (date) => {
    setStartDate(date);
  };

  const handleEndDateChange = (date) => {
    setEndDate(date);
  };

  const handleSendData = () => {
    const dataToSend = {
      startDate: startDate.toISOString(),
      endDate: endDate.toISOString(),
    };
    fetch('http://localhost:8080/api/greet', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(dataToSend),
    })
        .then(response => response.json())
        .then(data => {
          console.log('Odpowiedź od serwera:', data);
        })
        .catch(error => {
          console.error('Błąd podczas wysyłania danych:', error);
        });
  };
  const handleGetRequest = () => {
    fetch('http://localhost:8080/api/pomiary')
        .then(response => response.json())
        .then(data => {
          console.log('Odpowiedź od serwera (GET):', data);
          setResponseData(data);
        })
        .catch(error => {
          console.error('Błąd podczas wysyłania żądania GET:', error);
        });
  };
  const getLastDataPoint = () => {
    if (startData && startData.length > 0) {
      return startData[startData.length - 1];
    }
    return null;
  };

  const lastDataPoint = getLastDataPoint();

  const chartData = {
    labels: startData ? startData.pomiary.map(item => item.date) : [],
    datasets: [
      {
        label: 'PM2.5',
        fill: false,
        lineTension: 0.1,
        backgroundColor: 'rgba(75,192,192,0.4)',
        borderColor: 'rgba(75,192,192,1)',
        borderCapStyle: 'butt',
        borderDash: [],
        borderDashOffset: 0.0,
        borderJoinStyle: 'miter',
        pointBorderColor: 'rgba(75,192,192,1)',
        pointBackgroundColor: '#fff',
        pointBorderWidth: 1,
        pointHoverRadius: 5,
        pointHoverBackgroundColor: 'rgba(75,192,192,1)',
        pointHoverBorderColor: 'rgba(220,220,220,1)',
        pointHoverBorderWidth: 2,
        pointRadius: 1,
        pointHitRadius: 10,
        data: startData ? startData.pomiary.map(item => item.pm25) : [],
      },
    ],
  };
  const chartOptions = {
    scales: {
      xAxes: [{
        type: 'category',
        labels: startData ? startData.pomiary.map(item => item.date) : [],
      }],
    },
  };
  return (
      <div className="App">
        <header className="App-header">
          <p>
            Witaj na stronie prezentującej dane pomiarowe Edukacyjnej Sieci Antysmogowej (ESA)
          </p>
          <a
              className="App-link"
              href="https://dane.gov.pl/pl/dataset/2913,dane-pomiarowe-esa-edukacyjna-siec-antysmogowa"
              target="_blank"
              rel="noopener noreferrer"
          >
            Przejdź do API ESA
          </a>
        </header>
        <div>
          <h2>Ostatnie dane dla lokalizacji: </h2>
          {startData ? (
              <pre>  Miasto pomiaru {JSON.stringify(startData.pomiary[startData.pomiary.length - 1].punkt_pomiarowy.city, null, 2) + "\n"}
                Ulica {JSON.stringify(startData.pomiary[startData.pomiary.length - 1].punkt_pomiarowy.street, null, 2) + "\n"}
                Nazwa szkoły {JSON.stringify(startData.pomiary[startData.pomiary.length - 1].punkt_pomiarowy.schoolName, null, 2) + "\n"}
                Kod pocztowy {JSON.stringify(startData.pomiary[startData.pomiary.length - 1].punkt_pomiarowy.zipcode, null, 2) + "\n"}
                Szerokość geograficzna {JSON.stringify(startData.pomiary[startData.pomiary.length - 1].punkt_pomiarowy.latitude, null, 2) + "\n"}
                Długość geograficzna {JSON.stringify(startData.pomiary[startData.pomiary.length - 1].punkt_pomiarowy.longitude, null, 2) + "\n"}
                  </pre>
          ) : (
              <p>Nie można odczytać lokalizacji</p>
          )}
          <h3>Ostatnie pomiary: </h3>
          {startData ? (

              <pre>  Data pomiaru {JSON.stringify(startData.pomiary[startData.pomiary.length - 1].date, null, 2) + "\n"}
                Czas pomiaru {JSON.stringify(startData.pomiary[startData.pomiary.length - 1].time, null, 2) + "\n"}
                PM2.5 {JSON.stringify(startData.pomiary[startData.pomiary.length - 1].pm25, null, 2) + "\n"}
                PM10 {JSON.stringify(startData.pomiary[startData.pomiary.length - 1].pm10, null, 2) + "\n"}
                Temperatura {JSON.stringify(startData.pomiary[startData.pomiary.length - 1].temperature, null, 2) + "\n"}
                Wilgotność {JSON.stringify(startData.pomiary[startData.pomiary.length - 1].humidity, null, 2) + "\n"}
                Ciśnienie {JSON.stringify(startData.pomiary[startData.pomiary.length - 1].pressure, null, 2) + "\n"}
                  </pre>
          ) : (
              <p>Brak danych do wyświetlenia</p>
          )}
        </div>
        <div className="App">

          {startData ? (
              <div>
                <h1>Wykresy</h1>
                <h3>Stężenie pyłu PM2.5</h3>

                <Line data={chartData}/>
              </div>
          ) : (
              <p>Brak danych do wyświetlenia</p>
          )}
        </div>
        <div>
          <h3>Wybierz datę początkową:</h3>
          <DatePicker
              selected={startDate}
              onChange={handleStartDateChange}
              dateFormat="dd/MM/yyyy"
              placeholderText="Wybierz datę początkową"
          />
        </div>
        <div>
          <h3>Wybierz datę końcową:</h3>
          <DatePicker
              selected={endDate}
              onChange={handleEndDateChange}
              dateFormat="dd/MM/yyyy"
              placeholderText="Wybierz datę końcową"
          />
        </div>
        <button onClick={handleSendData}>Wyślij dane</button>
        <button onClick={handleGetRequest}>Wszystkie pomiary</button>
      </div>

  )
      ;
}

export default App;