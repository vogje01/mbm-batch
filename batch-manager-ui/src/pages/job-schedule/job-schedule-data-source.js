import DataSource from "devextreme/data/data_source";
import CustomStore from "devextreme/data/custom_store";
import {getParams} from "../../utils/param-util";
import {deleteItem, insertItem, listItems, updateItem} from "../../utils/server-connection";

export const JobScheduleDataSource = () => {
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
                jobSchedule.schedule = values.schedule ? values.schedule : jobSchedule.schedule;
                jobSchedule.jobDefinitionName = values.jobDefinitionName ? values.jobDefinitionName : jobSchedule.jobDefinitionName;
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

export const JobScheduleAgentDataSource = (jobSchedule) => {
    return new DataSource({
        store: new CustomStore({
            load: function (loadOptions) {
                let params = getParams(loadOptions, 'name');
                return listItems('jobschedules/' + jobSchedule.id + '/getAgents' + params, 'agentDtoes');
            },
            insert: function (agent) {
                let url = jobSchedule._links.addAgent.href;
                url = url.replace("{nodeName}", agent.nodeName)
                return insertItem(url)
            },
            remove: function (agent) {
                let url = jobSchedule._links.removeAgent.href;
                url = url.replace("{agentId}", agent.id)
                return insertItem(url)
            }
        })
    });
};
