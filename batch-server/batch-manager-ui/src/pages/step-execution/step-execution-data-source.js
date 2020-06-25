import DataSource from "devextreme/data/data_source";
import CustomStore from "devextreme/data/custom_store";
import {deleteItem, handleData, handleResponse, initGet} from "../../utils/server-connection";
import {EndTimer, StartTimer} from "../../utils/method-timer";
import {getFilterString} from "../../utils/filter-util";

// Special version of the getParams function, as the job name is somehow hidden
// inside the jobExecutionInfo.jobInstance.
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
                s.selector = 'jobExecutionInfo.jobExecutionInstance.jobName';
            }
            params += '&sort=' + s.selector + (s.desc ? ',desc' : ',asc');
        });
    } else {
        params += '&sort=' + defaultSortBy + ',' + defaultSortDir;
    }
    return params;
};

export const StepExecutionDataSource = (jobExecutionInfo, filterName) => {
    return new DataSource({
        store: new CustomStore({
            load: function (loadOptions) {
                StartTimer();
                let url = jobExecutionInfo !== undefined ? jobExecutionInfo._links.byJobId.href : process.env.REACT_APP_MANAGER_URL + 'stepexecutions';
                url += getParams(loadOptions, 'startTime', 'desc')
                if (filterName) {
                    url += getFilterString(filterName);
                }
                return fetch(url, initGet())
                    .then(response => {
                        return handleResponse(response)
                    })
                    .then(data => {
                        return handleData(data, 'stepExecutionDtoes')
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

export const StepExecutionLogDataSource = (stepExecutionInfo) => {
    return new DataSource({
        store: new CustomStore({
            load: function (loadOptions) {
                StartTimer();
                let url = stepExecutionInfo._links.logs.href + getParams(loadOptions, 'timestamp', 'desc')
                return fetch(url, initGet())
                    .then(response => {
                        return handleResponse(response, 'Could not get list of step execution logs');
                    })
                    .then(data => {
                        return handleData(data, 'jobExecutionLogDtoes')
                    }).finally(() => {
                        EndTimer();
                    });
            }
        })
    });
};
