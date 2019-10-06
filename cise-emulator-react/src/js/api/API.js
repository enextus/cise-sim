import axios from "axios";

export const get = async (service, params) => {
    try {
        let response = await axios.get(
            getServiceURL(service),
            {
                header: {
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

export const getServiceURL = service => {
    return getHost().concat("/api/").concat(service);
};

export const getHost = () => {
    return location.protocol.concat("//").concat(window.location.hostname).concat(":47080");
};
