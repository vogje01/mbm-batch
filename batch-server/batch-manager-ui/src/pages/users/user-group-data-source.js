import DataSource from "devextreme/data/data_source";
import CustomStore from "devextreme/data/custom_store";
import {deleteItem, getItem, getParams, handleData, handleResponse, initGet, insertItem, updateItem} from "../../utils/server-connection";
import {EndTimer} from "../../utils/method-timer";

export const UserGroupDataSource = () => {
    return new DataSource({
        store: new CustomStore({
            load: function (loadOptions) {
                let url = process.env.REACT_MANAGER_API_URL + 'usergroups' + getParams(loadOptions, 'name', 'asc');
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
                let url = process.env.REACT_MANAGER_API_URL + 'usergroups/insert';
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
                let url = userGroup._links.users.href + getParams(loadOptions, 'userId', 'asc');
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
                return getItem(userGroup._links.addUser.href + user.id);
            },
            remove: function (user) {
                return getItem(userGroup._links.removeUser.href + user.id);
            }
        })
    });
};
