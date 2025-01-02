import { useState } from "react";
import Link from "./Link";
import CreationBox from "./CreationBox";
import UpdateBox from "./UpdateBox";
import makeRequest from "./makeRequest";

const Home = ({ data, refreshData, type, setType, setLoggedIn }) => {

    const [showOverlay, setShowOverlay] = useState(false);
    const [showUpdateOverlay, setShowUpdateOverlay] = useState(false);
    const [curBookmark, setCurBookmark] = useState({});

    const logout = async() => {
        const response = await makeRequest("POST", "/logout", {});
        if(response.status === 200) {
            setLoggedIn(false);
        } else {
            alert(response.response.data["message"]);
        }
    }

    return (
        <div style={{position:"relative"}} className="outer">
            <div>
                <div className="header">
                    <h1 id="title">BKMRK</h1>
                    <button onClick={logout} className="header-button">Logout</button>
                    <button onClick={() => setShowOverlay(true)} className="header-button">Create</button>
                </div>
                <div id="type-select">
                    <button onClick = {() => setType(1)} className={type === 1 ? "select-opt selected" : "select-opt unselected"}>Permanent</button>
                    <button onClick = {() => setType(2)} className={type === 2 ? "select-opt selected" : "select-opt unselected"}>Temporary</button>
                    <button onClick = {() => setType(3)} className={type === 3 ? "select-opt selected" : "select-opt unselected"}>One-Time</button>
                </div>
                {type === 1 && data.filter(bookmark => bookmark.type === 1).map(bookmark => (
                    <Link 
                        bookmark={bookmark} 
                        refreshData={refreshData} 
                        setShowUpdateOverlay = {setShowUpdateOverlay} 
                        setCurBookmark = {setCurBookmark}
                    />
                ))}
                {type === 2 && data.filter(bookmark => bookmark.type === 2).map(bookmark => (
                    <Link 
                        bookmark={bookmark} 
                        refreshData={refreshData} 
                        setShowUpdateOverlay = {setShowUpdateOverlay} 
                        setCurBookmark = {setCurBookmark}
                    />
                ))}
                {type === 3 && data.filter(bookmark => bookmark.type === 3).map(bookmark => (
                    <Link 
                        bookmark={bookmark} 
                        refreshData={refreshData} 
                        setShowUpdateOverlay = {setShowUpdateOverlay} 
                        setCurBookmark = {setCurBookmark}
                    />
                ))}
            </div>
            {showOverlay && <div id = "overlay">
                <CreationBox setShowOverlay = {setShowOverlay} refreshData={refreshData}/>
            </div>}
            {showUpdateOverlay && <div id = "overlay">
                <UpdateBox setShowUpdateOverlay = {setShowUpdateOverlay} refreshData={refreshData} curBookmark={curBookmark}/>
            </div>}
        </div>
    );
}

export default Home;