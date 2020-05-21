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
