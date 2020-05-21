import DataSource from "devextreme/data/data_source";
import CustomStore from "devextreme/data/custom_store";
import {mergeParams} from "../../util/ParamUtil";
import {getList, insertItem} from "../../components/ServerConnection";

export const UsergroupUserDataSource = (userGroup) => {
    return new DataSource({
        store: new CustomStore({
            load: function (loadOptions) {
                let url = userGroup._links.users.href;
                url = mergeParams(loadOptions, url, 'userId');
                return getList(url, 'userDtoes');
            },
            insert: function (userGroup) {
                let url = userGroup._links.addUser.href;
                url = url.replace("{name}", userGroup.name)
                return insertItem(url)
            },
            remove: function (userGroup) {
                let url = userGroup._links.removeUser.href;
                url = url.replace("{userGroupId}", userGroup.id)
                return insertItem(url);
            }
        })
    });
};
