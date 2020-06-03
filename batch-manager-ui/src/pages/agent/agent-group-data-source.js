import DataSource from "devextreme/data/data_source";
import CustomStore from "devextreme/data/custom_store";
import {getParams, mergeParams} from "../../utils/param-util";
import {deleteItem, getList, insertItem, listItems, updateItem} from "../../utils/server-connection";

export const AgentGroupDataSource = () => {
    return new DataSource({
        store: new CustomStore({
            load: function (loadOptions) {
                let params = getParams(loadOptions, 'name');
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

export const AgentgroupAgentDataSource = (agentGroup) => {
    return new DataSource({
        store: new CustomStore({
            load: function (loadOptions) {
                if (!agentGroup._links) return;
                let url = agentGroup._links.agents.href;
                url = mergeParams(loadOptions, url, 'agentId');
                return getList(url, 'agentDtoes');
            },
            insert: function (agent) {
                let url = agentGroup._links.addGroup.href;
                url = url.replace("{agentId}", agent.agentId)
                return insertItem(url)
            },
            remove: function (agent) {
                let url = agentGroup._links.removeGroup.href;
                url = url.replace("{agentId}", agent.agentId)
                return insertItem(url);
            }
        })
    });
};
