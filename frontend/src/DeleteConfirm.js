import { useContext } from "react";
import makeRequest from "./makeRequest";
import DataContext from "./context/DataContext";
import UserContext from "./context/UserContext";

const DeleteConfirm = ({ setShowDeleteConfirm, shortCode }) => {

    const { refreshData } = useContext(DataContext);
    const { setLoggedIn } = useContext(UserContext);

    const deleteLink = async() => {
        const response = await makeRequest('DELETE', `/url/${shortCode}`, {});
        if(response.status === 200) {
            setShowDeleteConfirm(false);
            refreshData();
        } else if (response.status === 401) {
            alert("Session Expired");
            setShowDeleteConfirm(false);
            setLoggedIn(false);
        } else {
            alert(response.response.data["message"]);
        }
    }

    return (
        <div className = "overlay-box" style={{textAlign:"center"}}>
            <div id="delete-spacer"></div>
            <p id="delete-confirm-message">Are you sure you want to delete this bookmarked link?</p>
            <button onClick={() => setShowDeleteConfirm(false)} className="delete-confirm-btn" id="delete-cancel-button">No</button>
            <button onClick={deleteLink} className="delete-confirm-btn" id="delete-sub-button">Yes</button>
        </div>
    )
}

export default DeleteConfirm;