import DataSource from "devextreme/data/data_source";
import CustomStore from "devextreme/data/custom_store";
import {deleteItem, getParams, handleData, handleResponse, initGet} from "../../utils/server-connection";
import {EndTimer, StartTimer} from "../../utils/method-timer";

export const StepExecutionDataSource = (jobExecutionInfo) => {
    return new DataSource({
        store: new CustomStore({
            load: function (loadOptions) {
                StartTimer();
                let url = jobExecutionInfo !== undefined ? jobExecutionInfo._links.byJobId.href : process.env.REACT_APP_API_URL + 'stepexecutions';
                url += getParams(loadOptions, 'startTime', 'desc')
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
