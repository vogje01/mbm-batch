import {EndTimer, StartTimer} from "./method-timer";
import {errorMessage, infoMessage} from "./message-util";

export const initGet = () => {
    return {
        headers: {'Authorization': 'Bearer ' + localStorage.getItem('webToken')}, method: 'GET'
    }
};
export const initPut = (body) => {
    return {
        headers: {'Authorization': 'Bearer ' + localStorage.getItem('webToken'), 'Content-type': 'application/hal+json'}, method: 'PUT', body: body
    }
};
export const initDelete = () => {
    return {
        headers: {'Authorization': 'Bearer ' + localStorage.getItem('webToken'), 'Content-type': 'application/hal+json'}, method: 'DELETE'
    }
};
export const initInsert = (body) => {
    return {
        headers: {'Authorization': 'Bearer ' + localStorage.getItem('webToken'), 'Content-type': 'application/hal+json'}, method: 'PUT', body: body
    }
};
export const initUpdate = (body) => {
    return {
        headers: {'Authorization': 'Bearer ' + localStorage.getItem('webToken'), 'Content-type': 'application/hal+json'}, method: 'PUT', body: body
    }
};

export const handleResponse = (response, errMsg) => {
    if (response.status !== 200) {
        errorMessage(errMsg + " status: " + response.status + " message: " + response.statusText)
        return undefined;
    }
    return response.json()
}

export const handleData = (data, attributes) => {
    if (data.page.totalElements > 0) {
        return {
            data: data._embedded[attributes],
            totalCount: data.page.totalElements
        };
    }
    return {
        data: [],
        totalCount: 0
    }
}

export const getParams = (loadOptions, defaultSortBy, defaultSortDir) => {
    let params = '?';

    if (loadOptions.skip !== undefined && loadOptions.take !== undefined) {
        params += 'page=' + loadOptions.skip / loadOptions.take;
        params += '&size=' + loadOptions.take;
    } else {
        params += 'page=0';
        params += '&size=-1';
    }

    if (loadOptions.sort) {
        loadOptions.sort.forEach((s) => {
            params += '&sort=' + s.selector + (s.desc ? ',desc' : ',asc');
        });
    } else {
        params += '&sort=' + defaultSortBy + ',' + defaultSortDir;
    }
    return params;
};

const getItem = (url) => {
    StartTimer();
    return fetch(url, initGet())
        .then(response => {
            if (response.status === 401) {
                errorMessage("Could not get item.")
            }
            return response.json();
        })
        .finally(() => {
            EndTimer();
        });
};

const insertItem = (url, item) => {
    StartTimer();
    return fetch(url, initInsert(item))
        .then(response => {
            if (response.ok) {
                infoMessage('Insert item successful');
            }
        })
        .catch((error) => {
            errorMessage('Insert item error: ' + error.message);
        }).finally(() => {
            EndTimer();
        });
};

const updateItem = (url, item, attribute) => {
    StartTimer();
    return fetch(url, initUpdate(JSON.stringify(item)))
        .then(response => {
            if (response.ok) {
                infoMessage('Update item updated successfull');
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
    StartTimer();
    return fetch(url, initDelete())
        .then(response => {
            if (response.ok) {
                infoMessage('Item \'' + label + '\' deleted');
            }
        })
        .catch((error) => {
            errorMessage('Delete error: ' + error.message);
        }).finally(() => {
            EndTimer();
        });
};

export {deleteItem, insertItem, updateItem, getItem}