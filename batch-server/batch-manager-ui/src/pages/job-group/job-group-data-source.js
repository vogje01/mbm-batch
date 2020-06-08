import DataSource from "devextreme/data/data_source";
import CustomStore from "devextreme/data/custom_store";
import {getParams, mergeParams} from "../../utils/param-util";
import {deleteItem, getItem, getList, insertItem, listItems, updateItem} from "../../utils/server-connection";

export const JobGroupDataSource = () => {
    return new DataSource({
        store: new CustomStore({
            byKey: function (key) {
                let url = process.env.REACT_APP_API_URL + 'jobgroups/byName?name=' + key;
                return getItem(url);
            },
            load: function (loadOptions) {
                let params = getParams(loadOptions, 'name', 'asc');
                return listItems('jobgroups' + params, 'jobGroupDtoes');
            },
            insert: function (jobGroup) {
                let url = process.env.REACT_APP_API_URL + 'jobgroups/insert';
                return insertItem(url, JSON.stringify(jobGroup));
            },
            update: function (jobGroup, values) {
                jobGroup.name = values.name ? values.name : jobGroup.name;
                jobGroup.description = values.description ? values.description : jobGroup.description;
                jobGroup.active = values.active ? values.active : jobGroup.active;
                return updateItem(jobGroup._links.update.href, jobGroup, 'jobGroupDto');
            },
            remove: function (item) {
                return deleteItem(item._links.delete.href, item, item.name);
            }
        })
    });
};

export const JobGroupJobDefinitionDataSource = (jobGroup) => {
    return new DataSource({
        store: new CustomStore({
            load: function (loadOptions) {
                if (!jobGroup._links) return;
                let url = jobGroup._links.jobDefinitions.href;
                url = mergeParams(loadOptions, url, 'name', 'asc');
                return getList(url, 'jobDefinitionDtoes');
            },
            insert: function (user) {
                return getItem(jobGroup._links.addJobDefinition.href + user.id);
            },
            remove: function (user) {
                return getItem(jobGroup._links.removeJobDefinition.href + user.id);
            }
        })
    });
};
