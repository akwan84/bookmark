import { createContext, useContext, useState } from "react";
import makeRequest from "../makeRequest";
import UserContext from "./UserContext";

const DataContext = createContext({});

export const DataProvider = ({ children }) => {
    const [data, setData] = useState([]);
    const { setLoggedIn } = useContext(UserContext);

    const refreshData = async() => {
        const response = await makeRequest("GET", "/url", {});
		if(response.status === 200) {
			setData(response.data);
		} else if (response.status === 401) {
			alert("Session Expired");
			setLoggedIn(false);
		} else {
			alert(response.response.data["message"]);
		}
    }

    return (
        <DataContext.Provider value={{
            refreshData,
            data,
            setData
        }}>
            {children}
        </DataContext.Provider>
    )
}

export default DataContext;