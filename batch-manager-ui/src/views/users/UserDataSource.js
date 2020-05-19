import DataSource from "devextreme/data/data_source";
import CustomStore from "devextreme/data/custom_store";
import {getParams} from "../../util/ParamUtil";
import {deleteItem, insertItem, listItems, updateItem} from "../../components/ServerConnection";

export const userDataSource = () => {
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
                user.active = values.active;
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
