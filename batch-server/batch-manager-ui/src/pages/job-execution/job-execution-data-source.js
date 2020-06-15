import DataSource from "devextreme/data/data_source";
import CustomStore from "devextreme/data/custom_store";
import {deleteItem, handleData, handleResponse, initGet} from "../../utils/server-connection";
import {EndTimer, StartTimer} from "../../utils/method-timer";

// Special version of the getParams function, as the job name is somehow hidden
// inside the jobInstance.
//
const getParams = (loadOptions, defaultSortBy, defaultSortDir) => {
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
            if (s.selector === 'jobName') {
                s.selector = 'jobExecutionInstance.jobName';
            }
            params += '&sort=' + s.selector + (s.desc ? ',desc' : ',asc');
        });
    } else {
        params += '&sort=' + defaultSortBy + ',' + defaultSortDir;
    }
    return params;
};


export function JobExecutionDataSource() {
    return new DataSource({
        store: new CustomStore({
            load: function (loadOptions) {
                StartTimer();
                let url = process.env.REACT_APP_API_URL + 'jobexecutions' + getParams(loadOptions, 'startTime', 'desc')
                return fetch(url, initGet())
                    .then(response => {
                        return handleResponse(response)
                    })
                    .then(data => {
                        return handleData(data, 'jobExecutionDtoes')
                    })
                    .finally(() => EndTimer());
            },
            remove: function (key) {
                let url = key._links.delete.href;
                return deleteItem(url, key);
            }
        })
    });
}

export const JobExecutionLogDataSource = (jobExecutionInfo) => {
    return new DataSource({
        store: new CustomStore({
            load: function (loadOptions) {
                StartTimer();
                let url = jobExecutionInfo._links.logs.href + getParams(loadOptions, 'timestamp', 'desc')
                return fetch(url, initGet())
                    .then(response => {
                        return handleResponse(response)
                    })
                    .then(data => {
                        return handleData(data, 'jobExecutionLogDtoes')
                    })
                    .finally(() => EndTimer());
            }
        })
    });
};

export const JobExecutionParamDataSource = (jobExecutionInfo) => {
    return new DataSource({
        store: new CustomStore({
            load: function (loadOptions) {
                StartTimer();
                let url = jobExecutionInfo._links.params.href + getParams(loadOptions, 'keyName', 'asc')
                return fetch(url, initGet())
                    .then(response => {
                        return handleResponse(response)
                    })
                    .then(data => {
                        return handleData(data, 'jobExecutionParamDtoes')
                    })
                    .finally(() => EndTimer());
            },
            remove: function (key) {
                let url = key._links.delete.href;
                return deleteItem(url, key);
            }
        })
    });
};
