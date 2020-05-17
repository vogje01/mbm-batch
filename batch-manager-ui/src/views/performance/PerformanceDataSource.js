import DataSource from "devextreme/data/data_source";
import CustomStore from "devextreme/data/custom_store";
import {getParams} from "../../util/ParamUtil";
import {listItems} from "../../components/ServerConnection";

export const performanceDataSource = (nodeName, type, scale, startTime, endTime) => {
    return new DataSource({
        store: new CustomStore({
            load: function (loadOptions) {
                if (!nodeName) {
                    return;
                }
                let params = getParams(loadOptions) + '&type=' + type + '&scale=' + scale + '&startTime=' + startTime + '&endTime=' + endTime;
                return listItems('agentperformance/byNodeName/' + nodeName + params, 'agentPerformanceDtoes');
            },
            byKey: function (key) {

            }
        })
    });
};

export const nodesDataSource = () => {
    return new DataSource({
        store: new CustomStore({
            load: function (loadOptions) {
                return listItems('agents', 'agentDtoes')
            }
        })
    });
};
