import DataSource from "devextreme/data/data_source";
import CustomStore from "devextreme/data/custom_store";
import {mergeParams} from "../../utils/param-util";
import {deleteItem, getList} from "../../utils/server-connection";

export const StepExecutionDataSource = (jobExecutionInfo) => {
    return new DataSource({
        store: new CustomStore({
            load: function (loadOptions) {
                let url = process.env.REACT_APP_API_URL + 'stepexecutions';
                if (jobExecutionInfo !== undefined) {
                    url = jobExecutionInfo._links.byJobId.href;
                }
                url = mergeParams(loadOptions, url, 'startTime', 'desc');
                return getList(url, 'stepExecutionDtoes');
            },
            remove: function (key) {
                let url = key._links.delete.href;
                return deleteItem(url, key);
            }
        })
    });
};

export const StepExecutionLogDataSource = (stepExecutionInfo) => {
    return new DataSource({
        store: new CustomStore({
            load: function (loadOptions) {
                let url = mergeParams(loadOptions, stepExecutionInfo._links.logs.href, 'timestamp', 'desc');
                return getList(url, 'jobExecutionLogDtoes')
            }
        })
    });
};
