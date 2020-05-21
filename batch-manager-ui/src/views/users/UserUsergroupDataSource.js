import DataSource from "devextreme/data/data_source";
import CustomStore from "devextreme/data/custom_store";
import {mergeParams} from "../../util/ParamUtil";
import {getList, insertItem} from "../../components/ServerConnection";

export const UserUsergroupDataSource = (user) => {
    return new DataSource({
        store: new CustomStore({
            load: function (loadOptions) {
                let url = user._links.userGroups.href;
                url = mergeParams(loadOptions, url, 'name');
                return getList(url, 'userGroupDtoes');
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
