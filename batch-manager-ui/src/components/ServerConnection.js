import {EndTimer, StartTimer} from "../util/MethodTimer";
import {errorMessage, infoMessage} from "../util/MessageUtil";
import history from "./History";
import * as jwt from "jsonwebtoken";

const initGet = () => {
    return {
        headers: {'Authorization': 'Bearer ' + localStorage.getItem('webToken')}, method: 'GET'
    }
};
const initDelete = () => {
    return {
        headers: {'Authorization': 'Bearer ' + localStorage.getItem('webToken')}, method: 'DELETE'
    }
};
const initInsert = (body) => {
    return {
        headers: {'Authorization': 'Bearer ' + localStorage.getItem('webToken'), 'Content-type': 'application/hal+json'}, method: 'PUT', body: body
    }
};
const initUpdate = (body) => {
    return {
        headers: {'Authorization': 'Bearer ' + localStorage.getItem('webToken'), 'Content-type': 'application/hal+json'}, method: 'PUT', body: body
    }
};
const initPost = (token, body) => {
    return {
        headers: {'Content-Type': 'application/hal+json', 'Authorization': token}, method: 'POST', body: body
    }
};

const logout = () => {
    localStorage.clear();
    history.push('/');
};

const checkAuthentication = () => {
    if (localStorage.getItem('authenticated') === undefined) {
        logout();
    }
    let webToken = localStorage.getItem('webToken');
    let decoded = jwt.decode(webToken);
    decoded.exp = Math.floor(Date.now() / 1000) + 300;
    localStorage.setItem('webToken', jwt.sign(decoded, 'javainuse', {algorithm: 'HS512'}));
};

const loginRequest = (basicAuthentication, authRequest) => {
    StartTimer();
    let tokenBody = {sub: authRequest.userId, exp: Math.floor(Date.now() / 1000) + 300, iat: Math.floor(Date.now() / 1000)};
    let token = 'Bearer ' + jwt.sign(tokenBody, process.env.REACT_APP_WEBTOKEN_SECRET, {algorithm: 'HS512'});
    return fetch(process.env.REACT_APP_API_URL + 'authenticate', initPost(token, JSON.stringify(authRequest)))
        .then((response) => {
            return response.json();
        })
        .then((data) => {
            localStorage.setItem('authenticated', 'true');
            localStorage.setItem('webToken', data.token);
            history.push('/jobexecutions');
        })
        .catch((error) => {
            errorMessage('Login error: ' + error.message);
        }).finally(() => {
        EndTimer();
    });
};

const getList = (url, attributes) => {
    checkAuthentication();
    StartTimer();
    return fetch(url, initGet())
        .then(response => {
            if (response.status === 401) {
                logout();
            }
            return response.json()
        })
        .then((data) => {
            if (data === undefined) {
                return {
                    data: [],
                    totalCount: 0
                }
            }
            return {
                data: data._embedded ? data._embedded[attributes] : [],
                totalCount: data._embedded ? data._embedded[attributes][0].totalSize : 0
            };
        })
        .catch((error) => {
            errorMessage('Get list error: ' + error.message);
        }).finally(() => {
            EndTimer();
        });
};

const listItems = (entity, attributes) => {
    checkAuthentication();
    StartTimer();
    return fetch(process.env.REACT_APP_API_URL + entity, initGet())
        .then(response => {
            if (response.status === 401) {
                logout();
            }
            return response.json()
        })
        .then((data) => {
            if (data === undefined) {
                return {
                    data: [],
                    totalCount: 0
                }
            }
            return {
                data: data._embedded ? data._embedded[attributes] : [],
                totalCount: data._embedded ? data._embedded[attributes][0].totalSize : 0,
                links: data._links ? data._links : []
            };
        })
        .catch((error) => {
            errorMessage('Get list error: ' + error.message);
        }).finally(() => {
            EndTimer();
        });
};

const insertItem = (url, item) => {
    checkAuthentication();
    StartTimer();
    return fetch(url, initInsert(item))
        .then(response => {
            if (response.status === 401) {
                logout();
            }
            if (response.ok) {
                infoMessage('Item \'' + item.nodeName + '\' added');
            }
        })
        .catch((error) => {
            errorMessage('Insert item error: ' + error.message);
        }).finally(() => {
            EndTimer();
        });
};

const updateItem = (url, item, attribute) => {
    checkAuthentication();
    StartTimer();
    return fetch(url, initUpdate(JSON.stringify(item)))
        .then(response => {
            if (response.status === 401) {
                logout();
            }
            if (response.ok) {
                infoMessage('Item \'' + item.id + '\' updated');
            }
            return response.json();
        })
        .catch((error) => {
            errorMessage('Update item error: ' + error.message);
        }).finally(() => {
            EndTimer();
        });
};

const deleteItem = (url, key, label) => {
    checkAuthentication();
    StartTimer();
    return fetch(url, initDelete())
        .then(response => {
            if (response.status === 401) {
                logout();
            }
            if (response.ok) {
                infoMessage('Item \'' + label + '\' deleted');
            }
        })
        .catch((error) => {
            errorMessage('Get list error: ' + error.message);
        }).finally(() => {
            EndTimer();
        });
};

export {loginRequest, logout, getList, deleteItem, insertItem, updateItem, listItems}