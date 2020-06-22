import DataSource from "devextreme/data/data_source";
import CustomStore from "devextreme/data/custom_store";
import {deleteItem, getParams, handleData, handleResponse, initGet, updateItem} from "../../utils/server-connection";
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

export const HostDataSource = () => {
    return new DataSource({
        store: new CustomStore({
            byKey: function (key) {

            },
            load: function (loadOptions) {
                StartTimer();
                let url = process.env.REACT_APP_API_URL + 'hosts' + getParams(loadOptions, 'hostName', 'asc');
                return fetch(url, initGet())
                    .then(response => {
                        return handleResponse(response, 'Could not get list of hosts');
                    })
                    .then(data => {
                        return handleData(data, 'agentDtoes')
                    }).finally(() => {
                        EndTimer();
                    });
            },
            update: function (agent, values) {
                agent.nodeName = values.nodeName !== undefined ? values.nodeName : agent.nodeName;
                agent.active = values.active !== undefined ? values.active : agent.active;
                let url = agent._links.update.href;
                return updateItem(url, agent, 'agentDto');
            },
            remove: function (agent) {
                let url = agent._links.delete.href;
                url = url.replace("{agentId}", agent.id)
                return deleteItem(url, agent);
            }
        })
    });
};