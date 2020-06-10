import {EndTimer, StartTimer} from "./method-timer";
import {errorMessage, infoMessage} from "./message-util";

export const initGet = () => {
    return {
        headers: {'Authorization': 'Bearer ' + localStorage.getItem('webToken')}, method: 'GET'
    }
};
const initDelete = () => {
    return {
        headers: {'Authorization': 'Bearer ' + localStorage.getItem('webToken'), 'Content-type': 'application/hal+json'}, method: 'DELETE'
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

export const handleResponse = (response, errMsg) => {
    if (response.status !== 200) {
        errorMessage(errMsg + " status: " + response.status + " message: " + response.statusText)
        return null;
    }
    return response.json()
}

export const handleData = (data, attributes) => {
    if (data !== undefined) {
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

const getList = (url, attributes) => {
    StartTimer();
    return fetch(url, initGet())
        .then(response => {
            if (response.status !== 200) {
                throw new Error(response.statusText)
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
        }).finally(() => {
            EndTimer();
        });
};

export const getList1 = (url, attributes) => {
    StartTimer();
    return fetch(url, initGet())
        .then(response => {
            if (response.status !== 200) {
                throw new Error(response.statusText)
            }
            return response.json()
        })
        .then((data) => {
            return {
                data: data._embedded[attributes],
                totalCount: data.page.totalElements
            };
        }).finally(() => {
            EndTimer();
        });
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

const listItems = (entity, attributes) => {
    StartTimer();
    return fetch(process.env.REACT_APP_API_URL + entity, initGet())
        .then(response => {
            if (response.status !== 200) {
                errorMessage("Could not get list of items.")
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
        }).finally(() => {
            EndTimer();
        });
};

const listItems1 = (entity, attributes) => {
    StartTimer();
    return fetch(process.env.REACT_APP_API_URL + entity, initGet())
        .then(response => {
            if (response.status !== 200) {
                errorMessage("Could not get list of items.")
            }
            return response.json()
        })
        .then((data) => {
            return {
                data: data._embedded[attributes],
                totalCount: data.page.totalElements,
            };
        }).finally(() => {
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

export {getList, deleteItem, insertItem, updateItem, listItems, listItems1, getItem}