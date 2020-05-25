import DataSource from "devextreme/data/data_source";
import CustomStore from "devextreme/data/custom_store";
import {getParams, mergeParams} from "../../utils/param-util";
import {deleteItem, getItem, getList, insertItem, listItems, updateItem} from "../../utils/server-connection";

const getAttributeValue = (attribute, value) => {
    if (value.keyName) {
        attribute.keyName = value.keyName;
    }
    if (value.type) {
        attribute.type = value.type;
    }
    if (value.value) {
        attribute.stringValue = null;
        attribute.longValue = null;
        attribute.doubleValue = null;
        attribute.booleanValue = null;
        attribute.dateValue = null;
        attribute.value = null;
        switch (attribute.type) {
            case 'STRING':
                attribute.stringValue = '' + value.value;
                attribute.value = '' + value.value;
                break;
            case 'LONG':
                attribute.longValue = value.value;
                attribute.value = '' + value.value;
                break;
            case 'DOUBLE':
                attribute.doubleValue = value.value;
                attribute.value = '' + value.value;
                break;
            case 'BOOLEAN':
                attribute.booleanValue = value.value;
                attribute.value = '' + value.value;
                break;
            default:
                break;
        }
    }
    return attribute;
};

export const JobDefinitionDataSource = () => {
    return new DataSource({
        store: new CustomStore({
            byKey: function (key) {
                let url = process.env.REACT_APP_API_URL + 'jobdefinitions/byName?name=' + key;
                return getItem(url);
            },
            load: function (loadOptions) {
                let params = getParams(loadOptions, 'name');
                return listItems('jobdefinitions' + params, 'jobDefinitionDtoes');
            },
            insert: function (jobDefinition) {
                let url = process.env.REACT_APP_API_URL + 'jobdefinitions/insert';
                return insertItem(url, JSON.stringify(jobDefinition));
            },
            update: function (jobDefinition, values) {
                jobDefinition.label = values.label !== undefined ? values.label : jobDefinition.label;
                jobDefinition.name = values.name !== undefined ? values.name : jobDefinition.name;
                jobDefinition.version = values.version !== undefined ? values.version : jobDefinition.version;
                jobDefinition.type = values.type !== undefined ? values.type : jobDefinition.type;
                jobDefinition.fileName = values.fileName !== undefined ? values.fileName : jobDefinition.fileName;
                jobDefinition.command = values.command !== undefined ? values.command : jobDefinition.command;
                jobDefinition.active = values.active !== undefined ? values.active : jobDefinition.active;
                jobDefinition.jobGroupName = values.jobGroupName !== undefined ? values.jobGroupName : jobDefinition.jobGroupName;
                jobDefinition.workingDirectory = values.workingDirectory !== undefined ? values.workingDirectory : jobDefinition.workingDirectory;
                let url = jobDefinition._links.update.href;
                return updateItem(url, jobDefinition, 'jobDefinitionDto');
            },
            remove: function (item) {
                return deleteItem(item._links.delete.href, item, item.name);
            }
        })
    });
};

export const JobDefinitionParamDataSource = (jobDefinition) => {
    return new DataSource({
        store: new CustomStore({
            load: function (loadOptions) {
                if (!jobDefinition || !jobDefinition._links) {
                    return;
                }
                let url = mergeParams(loadOptions, jobDefinition._links.params.href, "name");
                return getList(url, 'jobDefinitionParamDtoes');
            },
            insert: function (values) {
                let param = getAttributeValue({}, values);
                let url = jobDefinition._links.addParam.href;
                return insertItem(url, JSON.stringify(param));
            },
            update: function (key, values) {
                let param = getAttributeValue(key, values);
                return updateItem(key._links.update.href, JSON.stringify(param))
            },
            remove: function (item) {
                let url = item._links.delete.href;
                return deleteItem(url, item, item.keyName)
            }
        })
    });
};