import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import NewTicket from './components/Pages/NewTicket';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import Home from './components/Pages/Home';
import Card from './components/ui/Card';

function App(props) {
  return (
    
    <Router>
    <div>
      <div>
        {props.children}
      </div>
      <Routes>
        <Route path='/NewTicket' element={< NewTicket />}/>
        <Route path='/' element = {< Home />}/>
      </Routes>
    </div>
    </Router>

    
    
  );
}

export default App;
