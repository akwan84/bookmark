import { useState } from "react";
import DeleteConfirm from "./DeleteConfirm";

const Link = ({ bookmark, setShowUpdateOverlay, setCurBookmark }) => {

    const [showDeleteConfirm, setShowDeleteConfirm] = useState(false);

    const copyToClipboard = async(textToCopy) => {
        try {
            await navigator.clipboard.writeText(textToCopy);
            console.log("Link copied");
        } catch (error) {
            console.error("Copy failed, try again");
        }
    }

    const triggerUpdate = () => {
        setCurBookmark(bookmark);
        setShowUpdateOverlay(true);
    }

    const convertUTCDateToLocalDate = (date) => {
        return new Date(date.getTime() - date.getTimezoneOffset()*60*1000);   
    }

    const date = convertUTCDateToLocalDate(new Date(bookmark.expiration));
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
            {bookmark.type === 2 && <p>Expiration Date: {date.toLocaleString()}</p>}
            {bookmark.type === 3 && <p>{bookmark.isActive ? 'Active' : 'Inactive'}</p>}
            <button onClick={() => setShowDeleteConfirm(true)} style={{marginBottom:"1em"}} className={buttonColor}>Delete</button>
            <button onClick={triggerUpdate} className={buttonColor}>Update</button>
            {showDeleteConfirm && <div className = "overlay">
                <DeleteConfirm setShowDeleteConfirm={setShowDeleteConfirm} shortCode={bookmark.shortCode} />
            </div>}
        </div>
    );
}

export default Link;