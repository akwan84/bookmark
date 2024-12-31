const Link = ({ bookmark }) => {
    const copyToClipboard = async(textToCopy) => {
        try {
            await navigator.clipboard.writeText(textToCopy);
            console.log("Link copied");
        } catch (error) {
            console.error("Copy failed, try again");
        }
    }

    const shortUrl = `${process.env.REACT_APP_API_URL}/url/${bookmark.shortCode}`;
    return (
        <div style={{border: "1px solid"}}>
            <p>Short URL: {shortUrl}</p>
            <button onClick={() => copyToClipboard(shortUrl)}>Copy Short URL</button>
            <p>Full URL: {bookmark.fullUrl}</p>
            <button onClick={() => copyToClipboard(bookmark.fullUrl)}>Copy Full URL</button>
            <p>Number of Visits: {bookmark.numVisits}</p>
            {bookmark.type === 2 && <p>Expiration Date: {bookmark.expiration}</p>}
            {bookmark.type === 3 && <p>Active: {bookmark.isActive ? 'True' : 'False'}</p>}
        </div>
    );
}

export default Link;