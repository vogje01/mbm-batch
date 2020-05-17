import DataSource from "devextreme/data/data_source";
import CustomStore from "devextreme/data/custom_store";
import {getParams, mergeParams} from "../../util/ParamUtil";
import {deleteItem, getList, listItems} from "../../components/ServerConnection";

export const jobExecutionDataSource = () => {
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
};

export const jobExecutionLogDataSource = (jobExecutionInfo) => {
    return new DataSource({
        store: new CustomStore({
            load: function (loadOptions) {
                let url = mergeParams(loadOptions, jobExecutionInfo._links.logs.href, 'instant.epochSecond');
                return getList(url, 'jobExecutionLogDtoes')
            }
        })
    });
};

export const jobExecutionParamDataSource = (jobExecutionInfo) => {
    return new DataSource({
        store: new CustomStore({
            load: function (loadOptions) {
                let params = getParams(loadOptions);
                return listItems('jobexecutionparams/byJobId/' + jobExecutionInfo.id + params, 'jobExecutionParamDtoes');
            },
            remove: function (key) {
                let url = key._links.delete.href;
                return deleteItem(url, key);
            }
        })
    });
};
