import { useContext, useState } from "react";
import makeRequest from "./makeRequest";
import DataContext from "./context/DataContext";
import UserContext from "./context/UserContext";

const UpdateBox = ({ setShowUpdateOverlay, curBookmark }) => {
    const getDiff = () => {
        const curDate = new Date();
        const utcExpDate = new Date(curBookmark.expiration);
        const expDate = new Date(utcExpDate.getTime() - utcExpDate.getTimezoneOffset()*60*1000); 

        const diff = expDate - curDate;
        if(diff < 0) return 0;

        return Math.floor(diff / (1000 * 60));
    }

    const { refreshData } = useContext(DataContext);
    const { setLoggedIn } = useContext(UserContext);

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
        } else if (response.status === 401) {
            alert("Session Expired");
            setShowUpdateOverlay(false);
            setLoggedIn(false);
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