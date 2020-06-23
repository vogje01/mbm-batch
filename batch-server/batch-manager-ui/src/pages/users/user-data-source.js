import DataSource from "devextreme/data/data_source";
import CustomStore from "devextreme/data/custom_store";
import {deleteItem, getItem, getParams, handleData, handleResponse, initGet, insertItem, updateItem} from "../../utils/server-connection";
import {EndTimer, StartTimer} from "../../utils/method-timer";

export const UserDataSource = () => {
    return new DataSource({
        store: new CustomStore({
            load: function (loadOptions) {
                let url = process.env.REACT_MANAGER_API_URL + 'users' + getParams(loadOptions, 'userId', 'asc');
                return fetch(url, initGet())
                    .then(response => {
                        return handleResponse(response, 'Could not get list of users');
                    })
                    .then(data => {
                        return handleData(data, 'userDtoes')
                    }).finally(() => {
                        EndTimer();
                    });
            },
            insert: function (user) {
                let url = process.env.REACT_MANAGER_API_URL + 'users/insert';
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
                if (user._links === undefined) {
                    return {data: [], totalCount: 0}
                }
                let url = user._links.userGroups.href + getParams(loadOptions, 'name', 'asc');
                return fetch(url, initGet())
                    .then(response => {
                        return handleResponse(response, 'Could not get list of user groups');
                    })
                    .then(data => {
                        return handleData(data, 'userGroupDtoes')
                    }).finally(() => {
                        EndTimer();
                    });
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

export const UserRestrictedDataSource = (userGroup) => {
    return new DataSource({
        store: new CustomStore({
            load: function (loadOptions) {
                StartTimer();
                let url = process.env.REACT_MANAGER_API_URL + 'users/restricted/' + userGroup.id + getParams(loadOptions, 'userId', 'asc')
                return fetch(url, initGet())
                    .then(response => {
                        return handleResponse(response)
                    })
                    .then((data) => {
                        return handleData(data, 'userDtoes')
                    })
                    .finally(() => EndTimer());
            }
        })
    });
};
