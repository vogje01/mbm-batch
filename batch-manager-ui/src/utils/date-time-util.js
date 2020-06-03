import * as moment from 'moment';
import 'moment/locale/en-gb';
import 'moment/locale/de';

export const dateTimeFormat = {
    DE: 'DD.MM.YYYY HH:mm:ss',
    ENGB: 'DD/MM/YYYY HH:mm:ss',
    ENUS: 'MM/DD/YYYY hh:mm:ss A'
};
export const localDateTimeFormat = dateTimeFormat[localStorage.getItem('dateTimeFormat')];

export const dateTimeFormatMillis = {
    DE: 'DD.MM.YYYY HH:mm:ss.SSS',
    ENGB: 'DD/MM/YYYY HH:mm:ss.SSS',
    ENUS: 'MM/DD/YYYY hh:mm:ss.SSS A'
};
export const localDateTimeMillisFormat = dateTimeFormatMillis[localStorage.getItem('dateTimeFormat')];

export const convertUTCToLocalDateTime = (dateTime) => {
    return moment(dateTime).format(localDateTimeFormat);
};

export const convertUTCToLocalDateTimeMillis = (dateTime) => {
    return moment(dateTime).format(localDateTimeMillisFormat)
};

export const getCurrentDateTime = () => {
    return moment().format(localDateTimeFormat);
};

export const zeroPad = (num, places) => {
    let zero = places - num.toString().length + 1;
    return Array(+(zero > 0 && zero)).join("0") + num;
};

export const calculateRunningTimeFromUnixTime = (unixTime) => {
    const runningTime = moment(unixTime);
    return zeroPad(runningTime.hours() - 1, 2) + ":" + zeroPad(runningTime.minutes(), 2) + ":" + zeroPad(runningTime.seconds(), 2) + "." + zeroPad(runningTime.milliseconds(), 3);
};

export const getRunningTime = (rowData) => {
    return rowData && rowData.runningTime ? calculateRunningTimeFromUnixTime(rowData.runningTime) : null;
};

export const getTimestamp = (rowData) => {
    return rowData && rowData.timestamp ? convertUTCToLocalDateTimeMillis(rowData.timestamp) : null;
};

export const getFormattedTime = (rowData, attribute) => {
    return rowData && rowData[attribute] ? convertUTCToLocalDateTime(rowData[attribute]) : null;
}

export const dateTimeCellTemplate = (container, options) => {
    container.textContent = getFormattedTime(options.data, options.column.dataField);
}
