import DataSource from "devextreme/data/data_source";
import CustomStore from "devextreme/data/custom_store";
import {deleteItem, getItem, getParams, handleData, handleResponse, initGet, insertItem, updateItem} from "../../utils/server-connection";
import {EndTimer, StartTimer} from "../../utils/method-timer";

const getAttributeValue = (jobDefinitionParam, values) => {
    jobDefinitionParam.keyName = values.keyName !== undefined ? values.keyName : jobDefinitionParam.keyName;
    jobDefinitionParam.type = values.type !== undefined ? values.type : jobDefinitionParam.type;
    jobDefinitionParam.longValue = values.longValue !== undefined ? values.longValue : jobDefinitionParam.longValue;
    jobDefinitionParam.stringValue = values.stringValue !== undefined ? values.stringValue : jobDefinitionParam.stringValue;
    jobDefinitionParam.booleanValue = values.booleanValue !== undefined ? values.booleanValue : jobDefinitionParam.booleanValue;
    jobDefinitionParam.doubleValue = values.doubleValue !== undefined ? values.doubleValue : jobDefinitionParam.doubleValue;
    jobDefinitionParam.dateValue = values.dateValue !== undefined ? values.dateValue : jobDefinitionParam.dateValue;
    return jobDefinitionParam;
};

export const JobDefinitionDataSource = () => {
    return new DataSource({
        store: new CustomStore({
            byKey: function (key) {
                let url = process.env.REACT_APP_MANAGER_URL + 'jobdefinitions/byName?name=' + key;
                return getItem(url);
            },
            load: function (loadOptions) {
                StartTimer();
                let url = process.env.REACT_APP_MANAGER_URL + 'jobdefinitions' + getParams(loadOptions, 'name', 'asc')
                return fetch(url, initGet())
                    .then(response => {
                        return handleResponse(response)
                    })
                    .then((data) => {
                        return handleData(data, 'jobDefinitionDtoes')
                    })
                    .finally(() => EndTimer());
            },
            insert: function (jobDefinition) {
                let url = process.env.REACT_APP_MANAGER_URL + 'jobdefinitions/insert';
                return insertItem(url, JSON.stringify(jobDefinition));
            },
            update: function (jobDefinition, values) {
                jobDefinition.label = values.label !== undefined ? values.label : jobDefinition.label;
                jobDefinition.name = values.name !== undefined ? values.name : jobDefinition.name;
                jobDefinition.jobVersion = values.jobVersion !== undefined ? values.jobVersion : jobDefinition.jobVersion;
                jobDefinition.type = values.type !== undefined ? values.type : jobDefinition.type;
                jobDefinition.fileName = values.fileName !== undefined ? values.fileName : jobDefinition.fileName;
                jobDefinition.command = values.command !== undefined ? values.command : jobDefinition.command;
                jobDefinition.active = values.active !== undefined ? values.active : jobDefinition.active;
                jobDefinition.jobGroupName = values.jobGroupName !== undefined ? values.jobGroupName : jobDefinition.jobGroupName;
                jobDefinition.workingDirectory = values.workingDirectory !== undefined ? values.workingDirectory : jobDefinition.workingDirectory;
                jobDefinition.loggingDirectory = values.loggingDirectory !== undefined ? values.loggingDirectory : jobDefinition.loggingDirectory;
                jobDefinition.description = values.description !== undefined ? values.description : jobDefinition.description;
                jobDefinition.failedExitCode = values.failedExitCode !== undefined ? values.failedExitCode : jobDefinition.failedExitCode;
                jobDefinition.failedExitMessage = values.failedExitMessage !== undefined ? values.failedExitMessage : jobDefinition.failedExitMessage;
                jobDefinition.completedExitCode = values.completedExitCode !== undefined ? values.completedExitCode : jobDefinition.completedExitCode;
                jobDefinition.completedExitMessage = values.completedExitMessage !== undefined ? values.completedExitMessage : jobDefinition.completedExitMessage;
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
                StartTimer();
                let url = jobDefinition._links.params.href + getParams(loadOptions, 'keyName', 'asc')
                return fetch(url, initGet())
                    .then(response => {
                        return handleResponse(response);
                    })
                    .then((data) => {
                        return handleData(data, 'jobDefinitionParamDtoes');
                    })
                    .finally(() => {
                        EndTimer();
                    });
            },
            insert: function (values) {
                let jobDefinitionParam = getAttributeValue(values, values);
                return insertItem(jobDefinition._links.addParam.href, JSON.stringify(jobDefinitionParam));
            },
            update: function (jobDefinitionParam, values) {
                jobDefinitionParam = getAttributeValue(jobDefinitionParam, values);
                return updateItem(jobDefinitionParam._links.update.href, jobDefinitionParam)
            },
            remove: function (item) {
                let url = item._links.delete.href;
                return deleteItem(url, item, item.keyName)
            }
        })
    });
};

export const JobDefinitionJobGroupDataSource = (jobDefinition) => {
    return new DataSource({
        store: new CustomStore({
            load: function (loadOptions) {
                StartTimer();
                let url = jobDefinition._links.jobGroups.href + getParams(loadOptions, 'name', 'asc')
                return fetch(url, initGet())
                    .then(response => {
                        return handleResponse(response);
                    })
                    .then((data) => {
                        return handleData(data, 'jobGroupDtoes');
                    })
                    .finally(() => {
                        EndTimer();
                    });
            },
            insert: function (user) {
                return getItem(jobDefinition._links.addJobGroup.href + user.id);
            },
            remove: function (user) {
                return getItem(jobDefinition._links.removeJobGroup.href + user.id);
            }
        })
    });
};

export const JobStart = (jobDefinition, agent) => {
    StartTimer();
    let url = jobDefinition._links.startJob.href + agent.id;
    return fetch(url, initGet())
        .then(response => {
            EndTimer();
            return handleResponse(response);
        });
}
