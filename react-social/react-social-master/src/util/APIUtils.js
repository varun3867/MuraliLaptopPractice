import { API_BASE_URL, ACCESS_TOKEN } from '../constants';

const request = (options) => {
    console.log("[request] Options are ",options);
    const headers = new Headers({
        'Content-Type': 'application/json',
    })
    
    if(localStorage.getItem(ACCESS_TOKEN)) {
        console.log("appending authorization bearer from localstorage.....");
        headers.append('Authorization', 'Bearer ' + localStorage.getItem(ACCESS_TOKEN))
    }

    const defaults = {headers: headers};
    options = Object.assign({}, defaults, options);
    console.log("[request] in Object.assign().....",options);
    return fetch(options.url, options)
    .then(response => 
        response.json().then(json => {
            if(!response.ok) {
                return Promise.reject(json);
            }
            console.log("returning json --> ",json)
            return json;
        })
    );
};

export function getCurrentUser() {
    console.log("entered into getCurrentUser()....")
    if(!localStorage.getItem(ACCESS_TOKEN)) {
        return Promise.reject("No access token set.");
    }

    return request({
        url: API_BASE_URL + "/user/me",
        method: 'GET'
    });
}

export function login(loginRequest) {
    console.log("[login] loginRequest is --> ",loginRequest);
    return request({
        url: API_BASE_URL + "/auth/login",
        method: 'POST',
        body: JSON.stringify(loginRequest)
    });
}

export function signup(signupRequest) {
    console.log("[signup] ssignupRequest is --> ",signupRequest);
    return request({
        url: API_BASE_URL + "/auth/signup",
        method: 'POST',
        body: JSON.stringify(signupRequest)
    });
}