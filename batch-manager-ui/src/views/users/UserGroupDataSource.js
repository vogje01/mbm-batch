import DataSource from "devextreme/data/data_source";
import CustomStore from "devextreme/data/custom_store";
import {getParams} from "../../util/ParamUtil";
import {deleteItem, insertItem, listItems, updateItem} from "../../components/ServerConnection";

export const userGroupDataSource = () => {
    return new DataSource({
        store: new CustomStore({
            load: function (loadOptions) {
                let params = getParams(loadOptions, 'name');
                return listItems('usergroups' + params, 'userGroupDtoes');
            },
            insert: function (userGroup) {
                let url = process.env.REACT_APP_API_URL + 'usergroups/insert';
                return insertItem(url, JSON.stringify(userGroup))
            },
            update: function (userGroup, values) {
                userGroup.nodeName = values.nodeName ? values.nodeName : userGroup.nodeName;
                userGroup.active = values.active;
                let url = userGroup._links.update.href;
                return updateItem(url, userGroup, 'userGroupDto');
            },
            remove: function (userGroup) {
                let url = userGroup._links.delete.href;
                return deleteItem(url, userGroup);
            }
        })
    });
};
