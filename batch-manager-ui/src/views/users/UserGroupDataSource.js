import DataSource from "devextreme/data/data_source";
import CustomStore from "devextreme/data/custom_store";
import {mergeParams} from "../../util/ParamUtil";
import {deleteItem, getList, insertItem, updateItem} from "../../components/ServerConnection";

export const userGroupDataSource = (user) => {
    return new DataSource({
        store: new CustomStore({
            load: function (loadOptions) {
                let url = process.env.REACT_APP_API_URL + 'usergroups';
                if (user !== undefined) {
                    url = user._links.userGroups.href;
                }
                url = mergeParams(loadOptions, url, 'name');
                return getList(url, 'userGroupDtoes');
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
