import axios from 'axios';

const makeRequest = async (method, url, body, token) => {
    try {
        const headers = token != null ? { headers : {Authorization : `Bearer ${token}`}} : {};

        let response;

        if(method === "POST") {
            response = await axios.post(
                `${process.env.REACT_APP_API_URL}${url}`,
                body,
                headers
            );
        } else if (method === "GET") {
            response = await axios.get(
                `${process.env.REACT_APP_API_URL}${url}`,
                headers
            );
        } else if (method === "DELETE") {
            response = await axios.delete(
                `${process.env.REACT_APP_API_URL}${url}`,
                headers
            )
        }

        return response;
    } catch (error) {
        //TODO: More verbose error handling
        return error;
    }
}

export default makeRequest;