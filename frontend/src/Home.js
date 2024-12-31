import { useState } from "react";
import Link from "./Link";
import CreationBox from "./CreationBox";
import UpdateBox from "./UpdateBox";

const Home = ({ data, refreshData, type, setType, token }) => {

    const [showOverlay, setShowOverlay] = useState(false);
    const [showUpdateOverlay, setShowUpdateOverlay] = useState(false);
    const [curBookmark, setCurBookmark] = useState({});

    return (
        <div style={{position:"relative"}}>
            <div>
                <div>
                    <button onClick={refreshData}>Refresh</button>
                    <button onClick={() => setShowOverlay(true)}>Create</button>
                </div>
                <div>
                    <button onClick = {() => setType(1)}>Permanent</button>
                    <button onClick = {() => setType(2)}>Temporary</button>
                    <button onClick = {() => setType(3)}>One-Time</button>
                </div>
                {type === 1 && data.filter(bookmark => bookmark.type === 1).map(bookmark => (
                    <Link 
                        bookmark={bookmark} 
                        token={token} 
                        refreshData={refreshData} 
                        setShowUpdateOverlay = {setShowUpdateOverlay} 
                        setCurBookmark = {setCurBookmark}
                    />
                ))}
                {type === 2 && data.filter(bookmark => bookmark.type === 2).map(bookmark => (
                    <Link 
                        bookmark={bookmark} 
                        token={token} 
                        refreshData={refreshData} 
                        setShowUpdateOverlay = {setShowUpdateOverlay} 
                        setCurBookmark = {setCurBookmark}
                    />
                ))}
                {type === 3 && data.filter(bookmark => bookmark.type === 3).map(bookmark => (
                    <Link 
                        bookmark={bookmark} 
                        token={token} 
                        refreshData={refreshData} 
                        setShowUpdateOverlay = {setShowUpdateOverlay} 
                        setCurBookmark = {setCurBookmark}
                    />
                ))}
            </div>
            {showOverlay && <div id = "overlay">
                <CreationBox setShowOverlay = {setShowOverlay} token={token} refreshData={refreshData}/>
            </div>}
            {showUpdateOverlay && <div id = "overlay">
                <UpdateBox setShowUpdateOverlay = {setShowUpdateOverlay} token={token} refreshData={refreshData} curBookmark={curBookmark}/>
            </div>}
        </div>
    );
}

export default Home;