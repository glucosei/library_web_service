import logo from './logo.svg';
import './App.css';
import axios from 'axios';

function selectData() {
  axios.post('/testData', ["가", "나", "다"], {
    headers: {
      "Content-Type": "application/json"
    }
  })
  .then(function (res) {
    console.log(res);
  })
  .catch(function (err) {
    console.error(err);
  });
}

function App() {

    return (
        <div className="App">
            <header className="App-header">
                <img src={logo} className="App-logo" alt="logo" />
                <div>
                   <button onClick={() =>selectData()}>조회</button>
                </div>
            </header>
        </div>
    );
}

export default App;