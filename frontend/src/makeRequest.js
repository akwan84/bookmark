import axios from 'axios';

const makeRequest = async (method, url, body) => {
    console.log("here");
    try {
        const response = await axios.post(
            `${process.env.REACT_APP_API_URL}${url}`,
            body
        );

        return response;
    } catch (error) {
        return error;
    }
}

export default makeRequest;