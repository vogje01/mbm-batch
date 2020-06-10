import DataSource from "devextreme/data/data_source";
import CustomStore from "devextreme/data/custom_store";
import {getParams} from "../../utils/param-util";
import {handleData, handleResponse, initGet} from "../../utils/server-connection";
import {EndTimer} from "../../utils/method-timer";

export const JobExecutionLogDataSource = () => {
    return new DataSource({
        store: new CustomStore({
            load: function (loadOptions) {
                let url = process.env.REACT_APP_API_URL + 'jobexecutionlogs';
                let params = getParams(loadOptions, 'timestamp', 'desc');
                return fetch(url + params, initGet())
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
