import { useState } from "react";
import makeRequest from "./makeRequest";

const UpdateBox = ({ setShowUpdateOverlay, token, refreshData, curBookmark }) => {
    const getDiff = () => {
        const curDate = new Date();
        const expDate = new Date(curBookmark.expiration);

        const diff = expDate - curDate;
        if(diff < 0) return 0;

        return Math.floor(diff / (1000 * 60));
    }

    const [url, setUrl] = useState(curBookmark.fullUrl);
    const [active, setActive] = useState(curBookmark.isActive);
    const [expTime, setExpTime] = useState(getDiff());

    const handleSubmit = async() => {
        const requestBody = {
            "fullUrl": url,
            "type": parseInt(curBookmark.type),
            "length": parseInt(expTime),
            "active": active
        };

        const response = await makeRequest("PUT", `/url/${curBookmark.shortCode}`, requestBody, token);
        if(response.status === 200) {
            refreshData();
            setShowUpdateOverlay(false);
        } else {
            alert(response.response.data["message"]);
        }
    }

    return (
        <div className = "overlay-box">
            <label htmlFor="fullUrl">URL</label>
            <input name="fullUrl" onChange = {e => setUrl(e.target.value)} value={url}></input>

            {curBookmark.type === 2 && 
                <div>
                    <label htmlFor="expTime">Time Until Expiration</label>
                    <input name="expTime" onChange={e => setExpTime(e.target.value)} value={expTime} type="number" min="1"></input>
                </div>
            }
            {curBookmark.type === 3 && <button onClick={() => setActive(!active)}>{active ? "Active" : "Inactive"}</button>}
            
            <button onClick={() => setShowUpdateOverlay(false)}>Cancel</button>
            <button onClick={handleSubmit}>Submit</button>
        </div>
    );
}

export default UpdateBox;