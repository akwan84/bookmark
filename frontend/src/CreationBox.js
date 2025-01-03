import { useState } from "react";
import makeRequest from "./makeRequest";

const CreationBox = ({ setShowOverlay, refreshData }) => {
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

        const response = await makeRequest("POST", "/url", requestBody);
        if(response.status === 200) {
            refreshData();
            setShowOverlay(false);
        } else {
            alert(response.response.data["message"]);
        }
    }

    return (
        <div className = "overlay-box">
            <div id="create-spacer"></div>
            <label htmlFor="fullUrl" className="create-header">URL</label>
            <br/>
            <input name="fullUrl" onChange = {e => setUrl(e.target.value)} className="create-input"></input>
            <br/>
            <label className="create-header">Type</label>
            <br/>
            <select onChange={e => setSelectedType(e.target.value)} className="create-input">
                <option value="1">Permanent</option>
                <option value="2">Temporary</option>
                <option value="3">One-Time</option>
            </select>
            <br/>
            {selectedType === "2" && 
                <div>
                    <label htmlFor="validLength" className="create-header">Validity Length (in minutes)</label>
                    <br/>
                    <input name="validLength" type="number" min="1" onChange={e => setLength(e.target.value)} className="create-input"></input>
                    <br/>
                </div>
            }

            {selectedType === "3" && <button onClick={() => setActive(!active)} className={active ? "create-active" : "create-inactive"}>{active ? "Active" : "Inactive"}</button>}
            <button onClick={() => setShowOverlay(false)} id="cancel-button" className="creation-btn">Cancel</button>
            <button onClick={handleSubmit} id="sub-button" className="creation-btn">Submit</button>
        </div>
    );
}

export default CreationBox;