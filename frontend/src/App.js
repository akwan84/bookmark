import { useState } from "react";
import Login from "./Login";
import Home from "./Home";


function App() {
  const [loggedIn, setLoggedIn] = useState(false);
  const [token, setToken] = useState("");

  return (
    <div className="App">
      {loggedIn ? 
        <Home/> :
        <Login 
          setLoggedIn = {setLoggedIn}
          setToken = {setToken}
        />
      }
    </div>
  );
}

export default App;
