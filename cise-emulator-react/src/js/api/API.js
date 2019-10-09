import axios from "axios";
import Notification from "../notifications/Notification.js"


export const get = async (service, params) => {
    try {
        let response = await axios.get(
            getServiceURL(service),
            {
                headers: {
                    'Content-Type': 'application/json'
                },
                params: params
            });

        return response.data;
    } catch (error) {
        console.error("request GET " + getServiceURL(service) + " failed.");
        console.error(error);
        return new Notification(error.response.status, error.response.data.error);
    }
};

export const post = async (service, data) => {
    try {
        let response = await axios.post(
            getServiceURL(service),
            data,
            {
                headers: {
                    'Content-Type': 'application/json'
                }
            });

        return response.data;

    } catch (error) {
        console.error("request GET " + getServiceURL(service) + " failed.");
        console.error(error);
    }
};

const getServiceURL = service => {
    return getHost().concat("/api/").concat(service);
};

const getHost = () => {
    if (window.location.hostname.includes("localhost"))
        return window.location.protocol.concat("//").concat(window.location.hostname).concat(":47080");
    else
        return window.location.protocol.concat("//").concat(window.location.host);
};


/* if (error.response) {
    /*
     * The request was made and the server responded with a
     * status code that falls out of the range of 2xx
     * /
    console.log(error.response.data);
    console.log(error.response.status);
    console.log(error.response.headers);
} else if (error.request) {
    /*
     * The request was made but no response was received, `error.request`
     * is an instance of XMLHttpRequest in the browser and an instance
     * of http.ClientRequest in Node.js
     * /
    console.log(error.request);
} else {
    // Something happened in setting up the request and triggered an Error
    console.log('Error', error.message);
} */