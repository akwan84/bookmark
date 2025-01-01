import axios from 'axios';

const makeRequest = async (method, url, body) => {
    try {
        let response;

        if(method === "POST") {
            response = await axios.post(
                `${process.env.REACT_APP_API_URL}${url}`,
                body,
                {
                    withCredentials: true
                }
            );
        } else if (method === "GET") {
            response = await axios.get(
                `${process.env.REACT_APP_API_URL}${url}`,
                {
                    withCredentials: true
                }
            );
        } else if (method === "DELETE") {
            response = await axios.delete(
                `${process.env.REACT_APP_API_URL}${url}`,
                {
                    withCredentials: true
                }
            )
        } else if (method === "PUT") {
            response = await axios.put(
                `${process.env.REACT_APP_API_URL}${url}`,
                body,
                {
                    withCredentials: true
                }
            );
        }

        return response;
    } catch (error) {
        //TODO: More verbose error handling
        return error;
    }
}

export default makeRequest;