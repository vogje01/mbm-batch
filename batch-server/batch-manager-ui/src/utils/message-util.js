import notify from 'devextreme/ui/notify';

export const infoMessage = (message) => {
    notify({
        type: 'success',
        width: 800,
        displayTime: 2000,
        message: message
    });
};

export const errorMessage = (message) => {
    notify({
        type: 'error',
        width: 800,
        displayTime: 2000,
        message: message
    });
};
