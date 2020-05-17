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
