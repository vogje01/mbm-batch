import DataSource from "devextreme/data/data_source";
import CustomStore from "devextreme/data/custom_store";
import {getParams} from "../../util/ParamUtil";
import {deleteItem, insertItem, listItems, updateItem} from "../../components/ServerConnection";

export const agentDataSource = () => {
    return new DataSource({
        store: new CustomStore({
            load: function (loadOptions) {
                let params = getParams(loadOptions, 'name');
                return listItems('agents' + params, 'agentDtoes');
            },
            update: function (agent, values) {
                agent.nodeName = values.nodeName ? values.nodeName : agent.nodeName;
                agent.active = values.active ? values.active : agent.active;
                let url = agent._links.update.href;
                return updateItem(url, agent, 'agentDto');
            },
            remove: function (agent) {
                let url = agent._links.delete.href;
                return deleteItem(url, agent);
            }
        })
    });
};

export const AgentScheduleDataSource = (agent) => {
    return new DataSource({
        store: new CustomStore({
            load: function (loadOptions) {
                let params = getParams(loadOptions, 'name');
                return listItems('agents/' + agent.id + '/getSchedules' + params, 'jobScheduleDtoes');
            },
            remove: function (agent) {
                let url = agent._links.removeSchedule.href + agent.id;
                return deleteItem(url, agent.id);
            },
            insert: function (agent) {
                let url = agent._links.addSchedule.href;
                return insertItem(url, JSON.stringify(agent.nodeName))
            },
            update: function (agent) {
                let url = agent._links.updateSchedule.href;
                return updateItem(url, agent);
            }
        })
    });
};
