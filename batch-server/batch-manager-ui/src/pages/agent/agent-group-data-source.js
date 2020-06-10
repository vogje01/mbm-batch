import DataSource from "devextreme/data/data_source";
import CustomStore from "devextreme/data/custom_store";
import {getParams} from "../../utils/param-util";
import {deleteItem, getItem, handleData, handleResponse, initGet, insertItem, updateItem} from "../../utils/server-connection";
import {EndTimer, StartTimer} from "../../utils/method-timer";

export const AgentGroupDataSource = () => {
    return new DataSource({
        store: new CustomStore({
            load: function (loadOptions) {
                StartTimer();
                let url = process.env.REACT_APP_API_URL + 'agentgroups' + getParams(loadOptions, 'name', 'asc');
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
                let url = process.env.REACT_APP_API_URL + 'agentgroups/insert';
                return insertItem(url, JSON.stringify(agentGroup))
            },
            update: function (agentGroup, values) {
                agentGroup.name = values.name ? values.name : agentGroup.name;
                agentGroup.active = values.active ? values.active : agentGroup.active;
                let url = agentGroup._links.update.href;
                return updateItem(url, agentGroup, 'agentGroupDto');
            },
            remove: function (agentGroup) {
                let url = agentGroup._links.delete.href;
                return deleteItem(url, agentGroup);
            }
        })
    });
};

export const AgentAgentGroupDataSource = (agent) => {
    return new DataSource({
        store: new CustomStore({
            load: function (loadOptions) {
                StartTimer();
                let url = agent._links.agentGroups.href + getParams(loadOptions, 'name', 'asc');
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
                return getItem(agent._links.addAgentGroup.href + agentGroup.id);
            },
            remove: function (agentGroup) {
                return getItem(agent._links.removeAgentGroup.href + agentGroup.id);
            }
        })
    });
};

export const AgentGroupAgentDataSource = (agentGroup) => {
    return new DataSource({
        store: new CustomStore({
            load: function (loadOptions) {
                StartTimer();
                let url = agentGroup._links.agents.href + getParams(loadOptions, 'nodeName', 'asc');
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
                return getItem(agentGroup._links.addAgent.href + agent.id);
            },
            remove: function (agent) {
                return getItem(agentGroup._links.removeAgent.href + agent.id);
            }
        })
    });
};
