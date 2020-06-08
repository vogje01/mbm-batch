import DataSource from "devextreme/data/data_source";
import CustomStore from "devextreme/data/custom_store";
import {getParams, mergeParams} from "../../utils/param-util";
import {deleteItem, getItem, getList, insertItem, listItems, updateItem} from "../../utils/server-connection";

export const UserDataSource = () => {
    return new DataSource({
        store: new CustomStore({
            load: function (loadOptions) {
                let params = getParams(loadOptions, 'userId', 'asc');
                return listItems('users' + params, 'userDtoes');
            },
            insert: function (user) {
                let url = process.env.REACT_APP_API_URL + 'users/insert';
                return insertItem(url, JSON.stringify(user))
            },
            update: function (user, values) {
                user.userId = values.userId ? values.userId : user.userId;
                user.firstName = values.firstName ? values.firstName : user.firstName;
                user.lastName = values.lastName ? values.lastName : user.lastName;
                user.email = values.email ? values.email : user.email;
                user.phone = values.phone ? values.phone : user.phone;
                user.theme = values.theme ? values.theme : user.theme;
                user.numberFormat = values.numberFormat ? values.numberFormat : user.numberFormat;
                user.dateTimeFormat = values.dateTimeFormat ? values.dateTimeFormat : user.dateTimeFormat;
                user.description = values.description ? values.description : user.description;
                user.active = values.active ? values.active : user.active;
                let url = user._links.update.href;
                return updateItem(url, user, 'userDto');
            },
            remove: function (user) {
                let url = user._links.delete.href;
                return deleteItem(url, user);
            }
        })
    });
};

export const UserUsergroupDataSource = (user) => {
    return new DataSource({
        store: new CustomStore({
            load: function (loadOptions) {
                if (user._links !== undefined) {
                    let url = user._links.userGroups.href;
                    url = mergeParams(loadOptions, url, 'name', 'asc');
                    return getList(url, 'userGroupDtoes');
                }
            },
            insert: function (userGroup) {
                return getItem(user._links.addUserGroup.href + userGroup.id);
            },
            remove: function (userGroup) {
                return getItem(user._links.removeUserGroup.href + userGroup.id);
            }
        })
    });
};
