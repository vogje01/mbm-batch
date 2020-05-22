import DataSource from "devextreme/data/data_source";
import CustomStore from "devextreme/data/custom_store";
import {getParams, mergeParams} from "../../util/ParamUtil";
import {deleteItem, getList, insertItem, listItems, updateItem} from "../../components/ServerConnection";

export const UserGroupDataSource = () => {
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
                userGroup.name = values.name ? values.name : userGroup.name;
                userGroup.active = values.active ? values.active : userGroup.active;
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

export const UsergroupUserDataSource = (userGroup) => {
    return new DataSource({
        store: new CustomStore({
            load: function (loadOptions) {
                let url = userGroup._links.users.href;
                url = mergeParams(loadOptions, url, 'userId');
                return getList(url, 'userDtoes');
            },
            insert: function (user) {
                let url = userGroup._links.addGroup.href;
                url = url.replace("{userId}", user.userId)
                return insertItem(url)
            },
            remove: function (user) {
                let url = userGroup._links.removeGroup.href;
                url = url.replace("{userId}", user.userId)
                return insertItem(url);
            }
        })
    });
};
