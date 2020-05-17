import DataSource from "devextreme/data/data_source";
import CustomStore from "devextreme/data/custom_store";
import {mergeParams} from "../../util/ParamUtil";
import {deleteItem, getList} from "../../components/ServerConnection";

export const stepExecutionDataSource = (jobExecutionInfo) => {
    return new DataSource({
        store: new CustomStore({
            load: function (loadOptions) {
                let url = process.env.REACT_APP_API_URL + 'stepexecutions';
                if (jobExecutionInfo !== undefined) {
                    url = jobExecutionInfo._links.byJobId.href;
                }
                url = mergeParams(loadOptions, url);
                return getList(url, 'stepExecutionDtoes');
            },
            remove: function (key) {
                let url = key._links.delete.href;
                return deleteItem(url, key);
            }
        })
    });
};

export const stepExecutionLogDataSource = (stepExecutionInfo) => {
    return new DataSource({
        store: new CustomStore({
            load: function (loadOptions) {
                let url = mergeParams(loadOptions, stepExecutionInfo._links.logs.href, 'instant.epochSecond');
                return getList(url, 'jobExecutionLogDtoes')
            }
        })
    });
};
