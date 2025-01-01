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
            <div id="reg-spacer"></div>
            <label htmlFor="username" className="register-header">Username</label>
            <br/>
            <input name="username" type="text" onChange={e => setUsername(e.target.value)} className="reg-input"></input>
            <br/>
            <br/>
            <label htmlFor="password" className="register-header">Password</label>
            <br/>
            <input name="password" type="password" onChange={e => setPassword(e.target.value)} className="reg-input"></input>
            <br/>
            <br/>
            <label htmlFor="confirm" className="register-header">Confirm Password</label>
            <br/>
            <input name="confirm" type="password"  onChange={e => setConfirm(e.target.value)} className="reg-input"></input>
            <br/>
            <button onClick={() => setShowRegOverlay(false)} className="reg-button">Cancel</button>
            <br/>
            <button onClick={registerUser} className="reg-button">Submit</button>
        </div>
    )
}

export default RegistrationBox;