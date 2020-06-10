import DataSource from "devextreme/data/data_source";
import CustomStore from "devextreme/data/custom_store";
import {mergeParams} from "../../utils/param-util";
import {deleteItem, getList, initGet} from "../../utils/server-connection";
import {EndTimer, StartTimer} from "../../utils/method-timer";
import {errorMessage} from "../../utils/message-util";

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
                        if (response.status !== 200) {
                            errorMessage("Could not get list of step executions - error: " + response.statusText)
                        }
                        return response.json()
                    })
                    .then(data => {
                        return {
                            data: data._embedded['stepExecutionDtoes'],
                            totalCount: data.page.totalElements,
                        };
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
                let url = mergeParams(loadOptions, stepExecutionInfo._links.logs.href, 'timestamp', 'desc');
                return getList(url, 'jobExecutionLogDtoes')
            }
        })
    });
};
