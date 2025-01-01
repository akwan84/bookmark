import { useState } from "react";
import makeRequest from "./makeRequest";

const RegistrationBox = ({ setShowRegOverlay }) => {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [confirm, setConfirm] = useState("");

    const registerUser = async() => {
        if(password !== confirm) {
            alert("Passwords do not match");
            return;
        }
        const requestBody = {
            "username": username,
            "password": password,
        };

        const response = await makeRequest("POST", "/register", requestBody);
        if(response.status === 200) {
            alert("User created");
            setShowRegOverlay(false);
        } else {
            alert(response.response.data["message"]);
        }
    }

    return (
        <div className = "overlay-box">
            <label htmlFor="username">Username</label>
            <input name="username" type="text" onChange={e => setUsername(e.target.value)}></input>
            <br/>
            <label htmlFor="password">Password</label>
            <input name="password" type="password" onChange={e => setPassword(e.target.value)}></input>
            <br/>
            <label htmlFor="confirm">Confirm Password</label>
            <input name="confirm" type="password"  onChange={e => setConfirm(e.target.value)}></input>
            <br/>
            <button onClick={() => setShowRegOverlay(false)}>Cancel</button>
            <button onClick={registerUser}>Submit</button>
        </div>
    )
}

export default RegistrationBox;