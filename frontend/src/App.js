
import './App.css';
import { BrowserRouter as Router, Route, Routes  } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css' 
import Home from './components/statistics/Home'
import Game from './components/games/Game';
import Games from './components/games/Games';
import Navbar from './components/general/Navbar';
import User from './components/users/User';
import Users from './components/users/Users';
import NotFound from './components/general/NotFound';
import { useTranslation } from "react-i18next";

function App() {
  return (
    <div>

      <Router>
        <Navbar/>
        <Routes >
          <Route exact path="/" element={<Home/>} />
          <Route  path="/game/:id" element={<Game/>} />
          <Route  path="/games/:page" element={<Games/>} />
          <Route  path="/user/:username/:page" element={<User/>} />
          <Route  path="/users/:page" element={<Users/>} />
          <Route  path="/users/:page" element={<Users/>} />
          <Route  path="*" element={<NotFound/>} />
        </Routes >
      </Router>
     
    </div>
    
      
  );
}

export default App;
