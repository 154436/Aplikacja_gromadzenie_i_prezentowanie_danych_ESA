import './App.css';
import React, { useState } from 'react';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import Chart from './Chart';
const App = () => {
    const [startDate, setStartDate] = useState(null);
    const [endDate, setEndDate] = useState(null);
    const [responseData, setResponseData] = useState(null);


    const handleStartDateChange = (date) => {
        setStartDate(date);
    };

    const handleEndDateChange = (date) => {
        setEndDate(date);
    };

    const handleSendData = () => {
        // Przygotuj dane do wysłania
        const dataToSend = {
            startDate: startDate.toISOString(),
            endDate: endDate.toISOString(),
        };

        // Wysyłka danych do API
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
        // Wysyłka żądania GET do API
        fetch('http://localhost:8080/api/pomiary')
            .then(response => response.json())
            .then(data => {
                console.log('Odpowiedź od serwera (GET):', data);
                // Zapisz otrzymane dane, jeśli to potrzebne
                setResponseData(data);
            })
            .catch(error => {
                console.error('Błąd podczas wysyłania żądania GET:', error);
            });
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
          <div className="App">
              <h1>Moja Aplikacja React z Wykresem</h1>
              <Chart/>
              {/* Dodaj resztę kodu Twojej aplikacji */}
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
