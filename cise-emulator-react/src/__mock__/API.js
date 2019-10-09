import Error from "../js/errors/error";

export const getResult = jest.fn().mockImplementation(() => {
    let failResult = new Error(500, "this is the error message");
    return (failResult);
});

const API = jest.fn().mockImplementation(() => {
    return {get: getResult};
})

export default API;