import DataSource from "devextreme/data/data_source";
import CustomStore from "devextreme/data/custom_store";
import {getParams, mergeParams} from "../../utils/param-util";
import {deleteItem, getItem, getList, insertItem, listItems, updateItem} from "../../utils/server-connection";

export const AgentGroupDataSource = () => {
    return new DataSource({
        store: new CustomStore({
            load: function (loadOptions) {
                let params = getParams(loadOptions, 'name', 'asc');
                return listItems('agentgroups' + params, 'agentGroupDtoes');
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
                if (agent._links !== undefined) {
                    let url = agent._links.agentGroups.href;
                    url = mergeParams(loadOptions, url, 'name', 'asc');
                    return getList(url, 'agentGroupDtoes');
                }
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
                if (!agentGroup._links) return;
                let url = agentGroup._links.agents.href;
                url = mergeParams(loadOptions, url, 'nodeName', 'asc');
                return getList(url, 'agentDtoes');
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
