import axios from "axios";

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
