import { useState } from "react";
import Link from "./Link";
import CreationBox from "./CreationBox";

const Home = ({ data, refreshData, type, setType, token }) => {

    const [showOverlay, setShowOverlay] = useState(false);

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
                    <Link bookmark={bookmark}/>
                ))}
                {type === 2 && data.filter(bookmark => bookmark.type === 2).map(bookmark => (
                    <Link bookmark={bookmark}/>
                ))}
                {type === 3 && data.filter(bookmark => bookmark.type === 3).map(bookmark => (
                    <Link bookmark={bookmark}/>
                ))}
            </div>
            {showOverlay && <div id = "overlay">
                <CreationBox setShowOverlay = {setShowOverlay} token={token} refreshData={refreshData}/>
            </div>}
        </div>
    );
}

export default Home;