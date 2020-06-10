import DataSource from "devextreme/data/data_source";
import CustomStore from "devextreme/data/custom_store";
import {getParams} from "../../utils/param-util";
import {deleteItem, getItem, handleData, handleResponse, initGet, updateItem} from "../../utils/server-connection";
import {EndTimer, StartTimer} from "../../utils/method-timer";

export const AgentDataSource = () => {
    return new DataSource({
        store: new CustomStore({
            byKey: function (key) {

            },
            load: function (loadOptions) {
                StartTimer();
                let url = process.env.REACT_APP_API_URL + 'agents' + getParams(loadOptions, 'nodeName', 'asc');
                return fetch(url, initGet())
                    .then(response => {
                        return handleResponse(response, 'Could not get list of agents');
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

export const AgentScheduleDataSource = (agent) => {
    return new DataSource({
        store: new CustomStore({
            load: function (loadOptions) {
                StartTimer();
                let url = agent._links.schedules.href + getParams(loadOptions, 'name', 'asc')
                return fetch(url, initGet())
                    .then(response => {
                        return handleResponse(response, 'Could not get list of schedules');
                    })
                    .then(data => {
                        return handleData(data, 'jobScheduleDtoes')
                    }).finally(() => {
                        EndTimer();
                    });
            },
            insert: function (jobSchedule) {
                return getItem(agent._links.addJobSchedule.href + jobSchedule.id);
            },
            remove: function (jobSchedule) {
                return getItem(agent._links.removeJobSchedule.href + jobSchedule.id);
            }
        })
    });
};
