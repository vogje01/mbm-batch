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
        params += '&sortBy=' + loadOptions.sort[0].selector;
        if (loadOptions.sort[0].desc) {
            params += '&sortDir=desc';
        } else {
            params += '&sortDir=asc';
        }
    } else {
        params += '&sortBy=' + defaultSortBy + '&sortDir=' + defaultSortDir;
    }
    return params;
};

export const mergeParams = (loadOptions, url, defaultSortBy, defaultSortDir) => {
    let newUrl = new URL(url);

    if (loadOptions.skip !== undefined && loadOptions.take !== undefined) {
        newUrl.searchParams.set('page', loadOptions.skip / loadOptions.take);
        newUrl.searchParams.set('size', loadOptions.take);
    }

    if (loadOptions.sort) {
        newUrl.searchParams.set('sortBy', loadOptions.sort[0].selector);
        if (loadOptions.sort[0].desc) {
            newUrl.searchParams.set('sortDir', 'desc');
        } else {
            newUrl.searchParams.set('sortDir', 'asc');
        }
    } else {
        newUrl.searchParams.set('sortBy', defaultSortBy);
        newUrl.searchParams.set('sortDir', defaultSortDir);
    }
    return newUrl.toString();
};
