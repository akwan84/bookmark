import { useEffect, useRef, useState } from "react";
import Login from "./Login";
import Home from "./Home";
import makeRequest from "./makeRequest";


function App() {
	const [loggedIn, setLoggedIn] = useState(false);
	const [token, setToken] = useState("");
	const [data, setData] = useState([]);
	const [type, setType] = useState(1);

	const initialRender = useRef(true);

	useEffect(() => {
		if(!loggedIn) return;
		const fetchData = async() => {
			const response = await makeRequest("GET", "/url", {}, token);
			setData(response.data);
		}
		fetchData();
	}, [loggedIn])

	return (
		<div className="App">
			{loggedIn ? 
				<Home
					data = {data}
					type = {type}
					setType={setType}
				/> :
				<Login 
					setLoggedIn = {setLoggedIn}
					setToken = {setToken}
				/>
			}
		</div>
	);
}

export default App;
