import { useEffect, useState } from "react";
import Login from "./Login";
import Home from "./Home";
import makeRequest from "./makeRequest";


function App() {
	const [loggedIn, setLoggedIn] = useState(false);
	const [data, setData] = useState([]);
	const [type, setType] = useState(1);

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

	const refreshData = async() => {
        const response = await makeRequest("GET", "/url", {});
		if(response.status === 200) {
			setData(response.data);
		} else {
			alert(response.response.data["message"]);
		}
    }

	return (
		<div className="App">
			{loggedIn ? 
				<Home
					data = {data}
					refreshData={refreshData}
					type = {type}
					setType={setType}
					setLoggedIn={setLoggedIn}
				/> :
				<Login 
					setLoggedIn = {setLoggedIn}
					refreshData = {refreshData}
				/>
			}
		</div>
	);
}

export default App;
