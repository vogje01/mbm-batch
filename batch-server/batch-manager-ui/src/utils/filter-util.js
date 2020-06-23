export const getFilterString = (filterName) => {
    let filter = JSON.parse(localStorage.getItem(filterName));
    if (filter == null) {
        return '';
    }
    let filterStr = '';
    filter.forEach(function (entry) {
        filterStr += '&' + entry.key + '=' + entry.value;
    });
    return filterStr;
};

export const addFilter = (filterName, search, searchValue) => {
    let filter = JSON.parse(localStorage.getItem(filterName));
    if (filter !== null) {
        let index = filter.findIndex((x => x.key === search))
        if (index === -1) {
            filter.push({key: search, value: searchValue});
        } else {
            filter[index] = {key: search, value: searchValue}
        }
    } else {
        filter = [{key: search, value: searchValue}];
    }
    localStorage.setItem(filterName, JSON.stringify(filter));
};

export const dropFilter = (filterName, search) => {
    let filter = JSON.parse(localStorage.getItem(filterName));
    if (filter !== null) {
        let index = filter.findIndex((x => x.key === search))
        if (index !== -1) {
            filter.splice(index, 1);
        }
    }
    localStorage.setItem(filterName, JSON.stringify(filter));
};

export const clearFilter = (filterName) => {
    localStorage.removeItem(filterName);
};
