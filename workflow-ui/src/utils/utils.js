import { Notify } from 'quasar';

export function fixedZero(val) {
    return val * 1 < 10 ? `0${val}` : val;
}

export function formatWan(val) {
    const v = val * 1;
    if (!v || Number.isNaN(v)) return '';

    let result = val;
    if (val > 10000) {
        result = Math.floor(val / 10000);
        result = `${result}万`;
    }
    return result;
}

export function formatDicDisplay(options, option) {
    let display = ''
    if (null === option) {
        return display;
    }
    options.forEach(op => {
        if (option.constructor === Object) {
            option = option.value
        }
        if (op.value === option) {
            display = op.label
        }
    })
    return display
}

export function positiveNotify(message) {
    let setting = {
        type: 'positive',
        position: 'top'
    };
    creteNotify(setting, message);
}
export function negativeNotify(message) {
    let setting = {
        type: 'negative',
        position: 'top'
    };
    creteNotify(setting, message);
}
export function warningNotify(message) {
    let setting = {
        type: 'warning',
        position: 'top'
    };
    creteNotify(setting, message);
}
export function infoNotify(message) {
    let setting = {
        type: 'info',
        position: 'top'
    };
    creteNotify(setting, message);
}
export function ongoingNotify(message) {
    let setting = {
        type: 'ongoing',
        position: 'top'
    };
    creteNotify(setting, message);
}
function creteNotify(setting, message) {
    if (typeof message === 'string') {
        setting.message = message;
    } else {
        Object.assign(setting, message);
    }
    Notify.create(setting);
}