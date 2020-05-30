import DataSource from "devextreme/data/data_source";
import CustomStore from "devextreme/data/custom_store";
import {getParams} from "../../utils/param-util";
import {deleteItem, insertItem, listItems, updateItem} from "../../utils/server-connection";

export const AgentDataSource = () => {
    return new DataSource({
        store: new CustomStore({
            load: function (loadOptions) {
                let params = getParams(loadOptions, 'nodeName');
                return listItems('agents' + params, 'agentDtoes');
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
                let params = getParams(loadOptions, 'name');
                return listItems('agents/' + agent.id + '/getSchedules' + params, 'jobScheduleDtoes');
            },
            insert: function (jobSchedule) {
                let url = agent._links.addJobSchedule.href;
                url = url.replace("{name}", jobSchedule.name)
                return insertItem(url)
            },
            remove: function (jobSchedule) {
                let url = agent._links.removeJobSchedule.href;
                url = url.replace("{jobScheduleId}", jobSchedule.id)
                return insertItem(url);
            }
        })
    });
};
