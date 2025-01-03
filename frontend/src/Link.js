import makeRequest from "./makeRequest";

const Link = ({ bookmark, refreshData, setShowUpdateOverlay, setCurBookmark, setLoggedIn }) => {
    const copyToClipboard = async(textToCopy) => {
        try {
            await navigator.clipboard.writeText(textToCopy);
            console.log("Link copied");
        } catch (error) {
            console.error("Copy failed, try again");
        }
    }

    const deleteLink = async() => {
        const response = await makeRequest('DELETE', `/url/${bookmark.shortCode}`, {});
        if(response.status === 200) {
            refreshData();
        } else if (response.status === 401) {
            alert("Session Expired");
            setLoggedIn(false);
        } else {
            alert(response.response.data["message"]);
        }
    }

    const triggerUpdate = () => {
        setCurBookmark(bookmark);
        setShowUpdateOverlay(true);
    }

    const date = new Date(bookmark.expiration);
    const now = new Date();
    const boxColor = 
        (bookmark.type === 3 && !bookmark.isActive) || (bookmark.type === 2 && date - now < 0)
        ? "link-box link-box-inactive" 
        : "link-box";
    const buttonColor = 
        (bookmark.type === 3 && !bookmark.isActive) || (bookmark.type === 2 && date - now < 0) 
        ? "link-box-button link-box-button-inactive" 
        : "link-box-button";
    const shortUrl = `${process.env.REACT_APP_API_URL}/url/${bookmark.shortCode}`;
    return (
        <div className={boxColor}>
            <p>Short URL: <a href={shortUrl} target="_blank">{shortUrl}</a></p>
            <button onClick={() => copyToClipboard(shortUrl)} className={buttonColor}>Copy Short URL</button>
            <p>Full URL: <a href={bookmark.fullUrl} target="_blank">{bookmark.fullUrl}</a></p>
            <button onClick={() => copyToClipboard(bookmark.fullUrl)} className={buttonColor}>Copy Full URL</button>
            {bookmark.type !== 3 && <p>Number of Visits: {bookmark.numVisits}</p>}
            {bookmark.type === 2 && <p>Expiration Date: {bookmark.expiration}</p>}
            {bookmark.type === 3 && <p>{bookmark.isActive ? 'Active' : 'Inactive'}</p>}
            <button onClick={deleteLink} style={{marginBottom:"1em"}} className={buttonColor}>Delete</button>
            <button onClick={triggerUpdate} className={buttonColor}>Update</button>
        </div>
    );
}

export default Link;