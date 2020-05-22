import DataSource from "devextreme/data/data_source";
import CustomStore from "devextreme/data/custom_store";
import {getParams} from "../../util/ParamUtil";
import {deleteItem, insertItem, listItems, updateItem} from "../../components/ServerConnection";

export const jobScheduleDataSource = () => {
    return new DataSource({
        store: new CustomStore({
            load: function (loadOptions) {
                let params = getParams(loadOptions, 'name');
                return listItems('jobschedules' + params, 'jobScheduleDtoes');
            },
            insert: function (jobSchedule) {
                let url = process.env.REACT_APP_API_URL + 'jobschedules/insert';
                return insertItem(url, JSON.stringify(jobSchedule))
            },
            update: function (jobSchedule, values) {
                jobSchedule.schedule = values.schedule ? values.schedule  : jobSchedule.schedule;
                jobSchedule.name = values.name ? values.name : jobSchedule.name;
                jobSchedule.active = values.active ? values.active : jobSchedule.active;
                let url = jobSchedule._links.update.href;
                return updateItem(url, jobSchedule);
            },
            remove: function (key) {
                let url = key._links.delete.href;
                return deleteItem(url, key);
            }
        })
    });
};

export const scheduleAgentDataSource = (jobSchedule) => {
    return new DataSource({
        store: new CustomStore({
            load: function (loadOptions) {
                let params = getParams(loadOptions, 'name');
                return listItems('jobschedules/' + jobSchedule.id + '/getAgents' + params, 'agentDtoes');
            },
            remove: function (agent) {
                let url = jobSchedule._links.removeAgent.href + agent.id;
                return deleteItem(url, agent.id);
            },
            insert: function (agent) {
                let url = jobSchedule._links.addAgent.href;
                return insertItem(url, JSON.stringify(agent.nodeName))
            },
            update: function (agent) {
                let url = jobSchedule._links.updateAgent.href;
                return updateItem(url, agent);
            }
        })
    });
};
