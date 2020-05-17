import DataSource from "devextreme/data/data_source";
import CustomStore from "devextreme/data/custom_store";
import {getParams} from "../../util/ParamUtil";
import {deleteItem, listItems, updateItem} from "../../components/ServerConnection";

export const agentDataSource = () => {
    return new DataSource({
        store: new CustomStore({
            load: function (loadOptions) {
                let params = getParams(loadOptions);
                return listItems('agents' + params, 'agentDtoes');
            },
            update: function (agent) {
                let url = agent._links.update.href;
                return updateItem(url, agent, 'agentDto');
            },
            remove: function (agent) {
                let url = agent._links.delete.href;
                return deleteItem(url, agent);
            }
        })
    });
};
