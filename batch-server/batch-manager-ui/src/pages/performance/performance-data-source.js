import DataSource from "devextreme/data/data_source";
import CustomStore from "devextreme/data/custom_store";
import {getParams} from "../../utils/param-util";
import {getItem} from "../../utils/server-connection";

export const PerformanceDataSource = (nodeName, type, metric, scale, startTime, endTime) => {
    return new DataSource({
        store: new CustomStore({
            load: function (loadOptions) {
                if (!nodeName) {
                    return;
                }
                let params = getParams(loadOptions) + '&type=' + type + '&metric=' + metric + '&scale=' + scale + '&startTime=' + startTime + '&endTime=' + endTime;
                return getItem(process.env.REACT_APP_API_URL + 'batchperformance/byNodeName/' + nodeName + params);
            },
            byKey: function (key) {

            }
        })
    });
};