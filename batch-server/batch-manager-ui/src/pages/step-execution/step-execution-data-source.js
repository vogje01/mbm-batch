import DataSource from "devextreme/data/data_source";
import CustomStore from "devextreme/data/custom_store";
import {mergeParams} from "../../utils/param-util";
import {deleteItem, handleData, handleResponse, initGet} from "../../utils/server-connection";
import {EndTimer, StartTimer} from "../../utils/method-timer";

export const StepExecutionDataSource = (jobExecutionInfo) => {
    return new DataSource({
        store: new CustomStore({
            load: function (loadOptions) {
                StartTimer();
                let url = process.env.REACT_APP_API_URL + 'stepexecutions';
                if (jobExecutionInfo !== undefined) {
                    url = jobExecutionInfo._links.byJobId.href;
                }
                url = mergeParams(loadOptions, url, 'startTime', 'desc');
                return fetch(url, initGet())
                    .then(response => {
                        return handleResponse(response, 'Could not get list of steps');
                    })
                    .then(data => {
                        return handleData(data, 'stepExecutionDtoes')
                    }).finally(() => {
                        EndTimer();
                    });
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
                let url = mergeParams(loadOptions, stepExecutionInfo._links.logs.href, 'timestamp', 'desc');
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
