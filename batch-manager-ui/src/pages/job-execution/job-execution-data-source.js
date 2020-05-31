import DataSource from "devextreme/data/data_source";
import CustomStore from "devextreme/data/custom_store";
import {getParams, mergeParams} from "../../utils/param-util";
import {deleteItem, getList, listItems} from "../../utils/server-connection";

export function JobExecutionDataSource() {
    return new DataSource({
        store: new CustomStore({
            load: function (loadOptions) {
                let params = getParams(loadOptions);
                return listItems('jobexecutions' + params, 'jobExecutionDtoes');
            },
            remove: function (key) {
                let url = key._links.delete.href;
                return deleteItem(url, key);
            }
        })
    });
}

export const JobExecutionLogDataSource = (jobExecutionInfo) => {
    return new DataSource({
        store: new CustomStore({
            load: function (loadOptions) {
                let url = mergeParams(loadOptions, jobExecutionInfo._links.logs.href, 'instant.epochSecond');
                return getList(url, 'jobExecutionLogDtoes')
            }
        })
    });
};

export const JobExecutionParamDataSource = (jobExecutionInfo) => {
    return new DataSource({
        store: new CustomStore({
            load: function (loadOptions) {
                let params = getParams(loadOptions, 'keyName');
                return listItems('jobexecutionparams/byJobId/' + jobExecutionInfo.id + params, 'jobExecutionParamDtoes');
            },
            remove: function (key) {
                let url = key._links.delete.href;
                return deleteItem(url, key);
            }
        })
    });
};
