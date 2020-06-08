import DataSource from "devextreme/data/data_source";
import CustomStore from "devextreme/data/custom_store";
import {getParams, mergeParams} from "../../utils/param-util";
import {deleteItem, getItem, getList, insertItem, listItems, updateItem} from "../../utils/server-connection";

export const UserGroupDataSource = () => {
    return new DataSource({
        store: new CustomStore({
            load: function (loadOptions) {
                let params = getParams(loadOptions, 'name', 'asc');
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
                if (!userGroup._links) return;
                let url = userGroup._links.users.href;
                url = mergeParams(loadOptions, url, 'userId', 'asc');
                return getList(url, 'userDtoes');
            },
            insert: function (user) {
                return getItem(userGroup._links.addUser.href + user.id);
            },
            remove: function (user) {
                return getItem(userGroup._links.removeUser.href + user.id);
            }
        })
    });
};
