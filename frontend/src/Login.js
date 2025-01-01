import { useState } from "react";
import makeRequest from "./makeRequest";
import RegistrationBox from "./RegistrationBox";

const Login = ({setLoggedIn, setToken}) => {
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
            setLoggedIn(true);
            setToken(response.data["token"]);
        } else {
            alert(response.response.data["message"]);
        }
    }
    
    return (
        <div>
            <label htmlFor="username">Username</label>
            <input type="text" name = "username" onChange={e => setUsername(e.target.value)}></input>
            <br/>
            <label htmlFor="password">Password</label>
            <input type="password" name = "password" onChange={e => setPassword(e.target.value)}></input>
            <br/>
            <button type="submit" onClick={submitLogin}>Login</button>
            <button onClick={() => setShowRegOverlay(true)}>Register</button>
            {showRegOverlay && <div id = "overlay">
                <RegistrationBox setShowRegOverlay={setShowRegOverlay}/>
            </div>}
        </div>
    );
}

export default Login;