import DataSource from "devextreme/data/data_source";
import CustomStore from "devextreme/data/custom_store";
import {getParams} from "../../utils/param-util";
import {listItems} from "../../utils/server-connection";

export const PerformanceDataSource = (nodeName, type, scale, startTime, endTime) => {
    return new DataSource({
        store: new CustomStore({
            load: function (loadOptions) {
                let params = getParams(loadOptions) + '&type=' + type + '&scale=' + scale + '&startTime=' + startTime + '&endTime=' + endTime;
                return listItems('agentperformance/byNodeName/' + nodeName + params, 'agentPerformanceDtoes');
            },
            byKey: function (key) {

            }
        })
    });
};
