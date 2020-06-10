import DataSource from "devextreme/data/data_source";
import CustomStore from "devextreme/data/custom_store";
import {getParams} from "../../utils/param-util";
import {listItems1} from "../../utils/server-connection";

export const JobExecutionLogDataSource = () => {
    return new DataSource({
        store: new CustomStore({
            load: function (loadOptions) {
                let params = getParams(loadOptions, 'timestamp', 'desc');
                return listItems1('jobexecutionlogs' + params, 'jobExecutionLogDtoes');
            }
        })
    });
};
