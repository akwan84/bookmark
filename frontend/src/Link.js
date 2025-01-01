import makeRequest from "./makeRequest";

const Link = ({ bookmark, refreshData, setShowUpdateOverlay, setCurBookmark }) => {
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
        } else {
            alert(response.response.data["message"]);
        }
    }

    const triggerUpdate = () => {
        setCurBookmark(bookmark);
        setShowUpdateOverlay(true);
    }

    const shortUrl = `${process.env.REACT_APP_API_URL}/url/${bookmark.shortCode}`;
    return (
        <div style={{border: "1px solid"}}>
            <p>Short URL: <a href={shortUrl} target="_blank">{shortUrl}</a></p>
            <button onClick={() => copyToClipboard(shortUrl)}>Copy Short URL</button>
            <p>Full URL: <a href={bookmark.fullUrl} target="_blank">{bookmark.fullUrl}</a></p>
            <button onClick={() => copyToClipboard(bookmark.fullUrl)}>Copy Full URL</button>
            {bookmark.type !== 3 && <p>Number of Visits: {bookmark.numVisits}</p>}
            {bookmark.type === 2 && <p>Expiration Date: {bookmark.expiration}</p>}
            {bookmark.type === 3 && <p>Active: {bookmark.isActive ? 'True' : 'False'}</p>}
            <button onClick={deleteLink}>Delete</button>
            <button onClick={triggerUpdate}>Update</button>
        </div>
    );
}

export default Link;