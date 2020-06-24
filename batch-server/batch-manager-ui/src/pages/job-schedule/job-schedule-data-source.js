import DataSource from "devextreme/data/data_source";
import CustomStore from "devextreme/data/custom_store";
import {deleteItem, getItem, getParams, handleData, handleResponse, initGet, insertItem, updateItem} from "../../utils/server-connection";
import {EndTimer, StartTimer} from "../../utils/method-timer";

export const JobScheduleDataSource = () => {
    return new DataSource({
        store: new CustomStore({
            load: function (loadOptions) {
                let url = process.env.REACT_APP_MANAGER_URL + 'jobschedules' + getParams(loadOptions, 'name', 'asc');
                return fetch(url, initGet())
                    .then(response => {
                        return handleResponse(response, 'Could not get list of job execution logs');
                    })
                    .then(data => {
                        return handleData(data, 'jobScheduleDtoes')
                    }).finally(() => {
                        EndTimer();
                    });
            },
            insert: function (jobSchedule) {
                let url = process.env.REACT_APP_MANAGER_URL + 'jobschedules/insert';
                return insertItem(url, JSON.stringify(jobSchedule))
            },
            update: function (jobSchedule, values) {
                jobSchedule.schedule = values.schedule !== undefined ? values.schedule : jobSchedule.schedule;
                jobSchedule.jobDefinitionName = values.jobDefinitionName !== undefined ? values.jobDefinitionName : null;
                jobSchedule.name = values.name !== undefined ? values.name : jobSchedule.name;
                jobSchedule.type = values.type !== undefined ? values.type : jobSchedule.type;
                jobSchedule.mode = values.mode !== undefined ? values.mode : jobSchedule.mode;
                jobSchedule.active = values.active !== undefined ? values.active : jobSchedule.active;
                let url = jobSchedule._links.update.href;
                return updateItem(url, jobSchedule);
            },
            remove: function (jobSchedule) {
                let url = jobSchedule._links.delete.href;
                return deleteItem(url, jobSchedule);
            }
        })
    });
};

export const JobScheduleAgentDataSource = (jobSchedule) => {
    return new DataSource({
        store: new CustomStore({
            load: function (loadOptions) {
                let url = jobSchedule._links.agents.href + getParams(loadOptions, 'nodeName', 'asc')
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
            insert: function (agent) {
                return getItem(jobSchedule._links.addAgent.href + agent.id);
            },
            remove: function (agent) {
                return getItem(jobSchedule._links.removeAgent.href + agent.id);
            }
        })
    });
};

export const JobScheduleAgentGroupDataSource = (jobSchedule) => {
    return new DataSource({
        store: new CustomStore({
            load: function (loadOptions) {
                let url = jobSchedule._links.agentGroups.href + getParams(loadOptions, 'name', 'asc')
                return fetch(url, initGet())
                    .then(response => {
                        return handleResponse(response, 'Could not get list of agent groups');
                    })
                    .then(data => {
                        return handleData(data, 'agentGroupDtoes')
                    }).finally(() => {
                        EndTimer();
                    });
            },
            insert: function (agentGroup) {
                return getItem(jobSchedule._links.addAgentGroup.href + agentGroup.id);
            },
            remove: function (agentGroup) {
                return getItem(jobSchedule._links.removeAgentGroup.href + agentGroup.id);
            }
        })
    });
};

export const JobScheduleStart = (jobSchedule) => {
    StartTimer();
    let url = jobSchedule._links.startJobSchedule.href;
    return fetch(url, initGet())
        .then(response => {
            EndTimer();
            return handleResponse(response);
        });
}
