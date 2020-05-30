import * as moment from 'moment';
import 'moment/locale/pt-br';

export const dateTimeFormat = 'DD.MM.YYYY HH:mm:ss';

export const formatDateTime = (dateTime) => {
    return moment().format(dateTimeFormat);
};

export const convertUTCToLocalDateTime = (dateTime) => {
    return moment(dateTime).format(dateTimeFormat)
};

export const getCurrentDateTime = () => {
    return moment().format(dateTimeFormat);
};

export const zeroPad = (num, places) => {
    var zero = places - num.toString().length + 1;
    return Array(+(zero > 0 && zero)).join("0") + num;
};

export const calculateRunningTimeFromUnixTime = (unixTime) => {
    const runningTime = moment(unixTime);
    return zeroPad(runningTime.hours() - 1, 2) + ":" + zeroPad(runningTime.minutes(), 2) + ":" + zeroPad(runningTime.seconds(), 2) + "." + zeroPad(runningTime.milliseconds(), 3);
};

export const getCreateTime = (rowData) => {
    return rowData && rowData.createTime ? convertUTCToLocalDateTime(rowData.createTime) : null;
};

export const getStartTime = (rowData) => {
    return rowData && rowData.startTime ? convertUTCToLocalDateTime(rowData.startTime) : null;
};

export const getEndTime = (rowData) => {
    return rowData && rowData.endTime ? convertUTCToLocalDateTime(rowData.endTime) : null;
};

export const getLastUpdatedTime = (rowData) => {
    return rowData && rowData.lastUpdated ? convertUTCToLocalDateTime(rowData.lastUpdated) : null;
};

export const getRunningTime = (rowData) => {
    return rowData && rowData.runningTime ? calculateRunningTimeFromUnixTime(rowData.runningTime) : null;
};

export const getPrevFireDateTime = (rowData) => {
    return rowData && rowData.lastExecution ? convertUTCToLocalDateTime(rowData.lastExecution) : null;
};

export const getNextFireDateTime = (rowData) => {
    return rowData && rowData.nextExecution ? convertUTCToLocalDateTime(rowData.nextExecution) : null;
};

export const getNextExecutionTime = (jobSchedule) => {
    return jobSchedule && jobSchedule.nextExecution ? convertUTCToLocalDateTime(jobSchedule.nextExecution) : null;
};

export const getLastExecutionTime = (jobSchedule) => {
    return jobSchedule && jobSchedule.lastExecution ? convertUTCToLocalDateTime(jobSchedule.lastExecution) : null;
};

export const getLastStartTime = (agent) => {
    return agent && agent.lastStart ? convertUTCToLocalDateTime(agent.lastStart) : null;
};

export const getLastPingTime = (agent) => {
    return agent && agent.lastPing ? convertUTCToLocalDateTime(agent.lastPing) : null;
};

export const getTimestamp = (rowData) => {
    return rowData && rowData.timestamp ? convertUTCToLocalDateTime(rowData.timestamp) : null;
};

export const getRangeStart = (scale) => {
    return moment().startOf(scale).unix();
};

export const getRangeEnd = (scale) => {
    return moment().endOf(scale).unix();
};

export const getLimitStart = (scale) => {
    return moment().startOf(scale).format(dateTimeFormat);
};

export const getLimitEnd = (scale) => {
    return moment().endOf(scale).format(dateTimeFormat);
};
