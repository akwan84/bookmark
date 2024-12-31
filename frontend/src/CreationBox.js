import { useState } from "react";
import makeRequest from "./makeRequest";

const CreationBox = ({ setShowOverlay, token, refreshData }) => {
    const [selectedType, setSelectedType] = useState("1");
    const [url, setUrl] = useState("");
    const [length, setLength] = useState(1);
    const [active, setActive] = useState(true);

    const handleSubmit = async() => {
        const requestBody = {
            "fullUrl": url,
            "type": parseInt(selectedType),
            "length": parseInt(length),
            "active": active
        };

        const response = await makeRequest("POST", "/url", requestBody, token);
        if(response.status === 200) {
            refreshData();
            setShowOverlay(false);
        } else {
            alert(response.response.data["message"]);
        }
    }

    return (
        <div className = "overlay-box">
            <label htmlFor="fullUrl">URL</label>
            <input name="fullUrl" onChange = {e => setUrl(e.target.value)}></input>
            <select onChange={e => setSelectedType(e.target.value)}>
                <option value="1">Permanent</option>
                <option value="2">Temporary</option>
                <option value="3">One-Time</option>
            </select>

            <button onClick={() => setShowOverlay(false)}>Cancel</button>
            {selectedType === "2" && 
                <div>
                    <label htmlFor="validLength">Validity Length (in minutes)</label>
                    <input name="validLength" type="number" min="1" onChange={e => setLength(e.target.value)}></input>
                </div>
            }
            {selectedType === "3" && <button onClick={() => setActive(!active)}>{active ? "Active" : "Inactive"}</button>}
            <button onClick={handleSubmit}>Submit</button>
        </div>
    );
}

export default CreationBox;