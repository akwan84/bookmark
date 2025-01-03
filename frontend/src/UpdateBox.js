import { useState } from "react";
import makeRequest from "./makeRequest";

const UpdateBox = ({ setShowUpdateOverlay, refreshData, curBookmark }) => {
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

        const response = await makeRequest("PUT", `/url/${curBookmark.shortCode}`, requestBody);
        if(response.status === 200) {
            refreshData();
            setShowUpdateOverlay(false);
        } else {
            alert(response.response.data["message"]);
        }
    }

    return (
        <div className = "overlay-box">
            <div id="update-spacer"></div>
            <label htmlFor="fullUrl" className="update-header">URL</label>
            <br/>
            <input name="fullUrl" onChange = {e => setUrl(e.target.value)} value={url} className="update-input"></input>
            <br/>
            {curBookmark.type === 2 && 
                <div>
                    <label htmlFor="expTime" className="update-header">Time Until Expiration</label>
                    <br/>
                    <input name="expTime" onChange={e => setExpTime(e.target.value)} value={expTime} type="number" min="1" className="update-input"></input>
                </div>
            }
            {curBookmark.type === 3 && <button onClick={() => setActive(!active)} className={active ? "create-active" : "create-inactive"}>{active ? "Active" : "Inactive"}</button>}
            
            <button onClick={() => setShowUpdateOverlay(false)} id="update-cancel-button" className="update-btn">Cancel</button>
            <br/>
            <button onClick={handleSubmit} id="update-sub-button" className="update-btn">Submit</button>
        </div>
    );
}

export default UpdateBox;