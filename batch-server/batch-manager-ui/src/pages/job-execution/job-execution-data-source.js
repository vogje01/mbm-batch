import DataSource from "devextreme/data/data_source";
import CustomStore from "devextreme/data/custom_store";
import {deleteItem, getParams, handleData, handleResponse, initGet} from "../../utils/server-connection";
import {EndTimer, StartTimer} from "../../utils/method-timer";

export function JobExecutionDataSource() {
    return new DataSource({
        store: new CustomStore({
            load: function (loadOptions) {
                StartTimer();
                let url = process.env.REACT_APP_API_URL + 'jobexecutions/' + getParams(loadOptions, 'startTime', 'desc')
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
