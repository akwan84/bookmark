import { useContext, useState } from "react";
import makeRequest from "./makeRequest";
import RegistrationBox from "./RegistrationBox";
import UserContext from "./context/UserContext";
import DataContext from "./context/DataContext";

const Login = () => {
    const { refreshData } = useContext(DataContext);
    const { setLoggedIn } = useContext(UserContext);

    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [showRegOverlay, setShowRegOverlay] = useState(false);

    const submitLogin = async () => {
        const requestBody = {
            "username": username,
            "password": password
        };

        const response = await makeRequest("POST", "/login", requestBody);
        if(response.status === 200) {
            refreshData();
            setLoggedIn(true);
        } else {
            alert(response.response.data["message"]);
        }
    }
    
    return (
        <div className="outer">
            <div id="login-spacer"></div>
            <label htmlFor="username" className="login-header">Username</label>
            <br/>
            <input type="text" name = "username" onChange={e => setUsername(e.target.value)} className="login-input"></input>
            <br/>
            <br/>
            <label htmlFor="password" className="login-header">Password</label>
            <br/>
            <input type="password" name = "password" onChange={e => setPassword(e.target.value)} className="login-input"></input>
            <br/>
            <button type="submit" onClick={submitLogin} className="login-button">Login</button>
            <br/>
            <button onClick={() => setShowRegOverlay(true)} className="login-button">Register</button>
            {showRegOverlay && <div className = "overlay">
                <RegistrationBox setShowRegOverlay={setShowRegOverlay}/>
            </div>}
        </div>
    );
}

export default Login;