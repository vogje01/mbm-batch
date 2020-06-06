export const truncate = (value, fractionalDigits) => {
    return Number.parseFloat(value).toFixed(fractionalDigits);
};

export const getPctCounter = (totalCounter, counter) => {
    if (totalCounter && counter) {
        let pct = truncate(counter / totalCounter * 100, 2);
        return counter + ' (' + pct + '%)';
    }
    return counter ? counter : '0 (0.00%)';
};

export const getFormattedNumber = (rowData, attribute) => {
    return rowData && rowData[attribute] ? truncate(rowData[attribute], 2) : null;
}

export const numericCellTemplate = (container, options) => {
    container.textContent = getFormattedNumber(options.data, options.column.dataField);
}

