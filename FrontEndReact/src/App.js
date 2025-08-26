import 'bootstrap/dist/css/bootstrap.min.css';
import { BrowserRouter, Routes, Route, Navigate} from 'react-router-dom';
import HomePage from './HomePage';
import './App.css';
import Tournament from './Tournament';
import Battle from './Battle';
import Login from "./Login"; 
import AddMove from './AddMove';
import AddPokemon from './AddPokemon'; 


const user = JSON.parse(localStorage.getItem('user'));  

const PrivateRoute = ({children})=>{
  if(user){
    return children; 
  }
  else {
    return <Navigate to="/login" />
  }
}

const AdminProtectedRoute = ({children}) => {

  if(user.role === "admin"){
        return children; 
  }
  else{
    return <Navigate to="/login"/>
  }
}


function App() {

  return (
      <BrowserRouter>
        <div>
            <Routes>
              <Route path="/" element={<PrivateRoute><HomePage /></PrivateRoute>} />
              <Route path="/tournament" element={<PrivateRoute><Tournament /></PrivateRoute>} />
              <Route path="/battle" element={<PrivateRoute><Battle /></PrivateRoute>} />
              <Route path="/add_move" element={<AdminProtectedRoute><AddMove/></AdminProtectedRoute>}/>
              <Route path="/add_pokemon" element={<AdminProtectedRoute><AddPokemon/></AdminProtectedRoute>}/>
              <Route path="/login" element={<Login/>}/>
            </Routes>
        </div>
      </BrowserRouter>
  );
}

export default App;
