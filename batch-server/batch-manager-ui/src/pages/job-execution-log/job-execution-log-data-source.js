import DataSource from "devextreme/data/data_source";
import CustomStore from "devextreme/data/custom_store";
import {getParams, handleData, handleResponse, initGet} from "../../utils/server-connection";
import {EndTimer, StartTimer} from "../../utils/method-timer";

export const JobExecutionLogDataSource = () => {
    return new DataSource({
        store: new CustomStore({
            load: function (loadOptions) {
                StartTimer();
                let url = process.env.REACT_APP_API_URL + 'jobexecutionlogs' + getParams(loadOptions, 'timestamp', 'desc');
                return fetch(url, initGet())
                    .then(response => {
                        return handleResponse(response, 'Could not get list of job execution logs');
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
