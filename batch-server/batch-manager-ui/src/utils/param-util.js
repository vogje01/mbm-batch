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
        params += '&sort=' + loadOptions.sort[0].selector + ',' + (loadOptions.sort[0].desc ? 'desc' : 'asc');
    } else {
        params += '&sort=' + defaultSortBy + ',' + defaultSortDir;
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
        newUrl.searchParams.set('sort', loadOptions.sort[0].selector + ',' + (loadOptions.sort[0].desc ? 'desc' : 'asc'));
    } else {
        newUrl.searchParams.set('sort', defaultSortBy + ',' + defaultSortDir);
    }
    return newUrl.toString();
};
