import DataSource from "devextreme/data/data_source";
import CustomStore from "devextreme/data/custom_store";
import {getParams} from "../../util/ParamUtil";
import {deleteItem, insertItem, listItems, updateItem} from "../../components/ServerConnection";

export const jobGroupDataSource = () => {
    return new DataSource({
        store: new CustomStore({
            load: function (loadOptions) {
                let params = getParams(loadOptions, 'name');
                return listItems('jobgroups' + params, 'jobGroupDtoes');
            },
            insert: function (jobGroup) {
                let url = process.env.REACT_APP_API_URL + 'jobgroups/insert';
                return insertItem(url, JSON.stringify(jobGroup));
            }, update: function (jobGroup) {
                let url = process.env.REACT_APP_API_URL + 'jobgroups/insert';
                return updateItem(url, JSON.stringify(jobGroup));
            },
            remove: function (item) {
                return deleteItem(item._links.delete.href, item, item.name);
            }
        })
    });
};
