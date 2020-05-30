import DataSource from "devextreme/data/data_source";
import CustomStore from "devextreme/data/custom_store";
import {getParams, mergeParams} from "../../utils/param-util";
import {deleteItem, getList, insertItem, listItems, updateItem} from "../../utils/server-connection";

export const UserDataSource = () => {
    return new DataSource({
        store: new CustomStore({
            load: function (loadOptions) {
                let params = getParams(loadOptions, 'userId');
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
                    url = mergeParams(loadOptions, url, 'name');
                    return getList(url, 'userGroupDtoes');
                }
            },
            insert: function (userGroup) {
                let url = user._links.addUserGroup.href;
                url = url.replace("{name}", userGroup.name)
                return insertItem(url)
            },
            remove: function (userGroup) {
                let url = user._links.removeUserGroup.href;
                url = url.replace("{userGroupId}", userGroup.id)
                return insertItem(url);
            }
        })
    });
};
