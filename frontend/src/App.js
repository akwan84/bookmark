import { useContext, useEffect, useState } from "react";
import Login from "./Login";
import Home from "./Home";
import makeRequest from "./makeRequest";
import UserContext from "./context/UserContext";
import DataContext from "./context/DataContext";


function App() {
	const { loggedIn, setLoggedIn } = useContext(UserContext);
	const { setData } = useContext(DataContext);

	useEffect(() => {
		const fetchData = async() => {
			const response = await makeRequest("GET", "/url", {});
			if(response.status === 200) {
				setData(response.data);
				setLoggedIn(true);
			}
		}
		fetchData();
	}, []);

	return (
		<div className="App">
			{loggedIn ? 
				<Home/> :
				<Login/>
			}
		</div>
	);
}

export default App;
