

export const buildValueLabelMap = (valueList) => {
    let labelMap = [];
    for (let val of valueList) {
        //let lab = val[0] + val.substring(1).replace(/[A-Z][a-z]*/g, str => ' ' + str.toLowerCase());
        let lab = value2label(val);
        labelMap.push({value:val, label:lab});
    }
    return labelMap
}

const value2label= (value) => {

    let label = [];
    label.push(value.charAt(0));
    for (let i = 1; i < value.length; i++) {
        const c = value.charAt(i);
        if (c === c.toUpperCase()) {
            if ((i+1) < value.length) {
                const cNext = value.charAt(i+1);
                if (cNext !== cNext.toUpperCase()) {
                    label.push(' ');
                    label.push(c.toLowerCase());
                }
                else {
                    label.push(c);
                }
            }
            else {
                label.push(c);
            }
        }
        else {
            label.push(c);
        }
    }
    return label.join("");
}

export const date2String = (timestamp) => {

    let date = new Date(timestamp);
    let aaaa = date.getFullYear();
    let gg = date.getDate();
    let mm = (date.getMonth() + 1);

    const options = {month: 'long'};
    let monthLiteral = new Intl.DateTimeFormat('en-EN', options).format(date);

    if (gg < 10)
        gg = "0" + gg;

    if (mm < 10)
        mm = "0" + mm;

    monthLiteral = 'September'
    let cur_day =  gg + "-" + monthLiteral + "-" + aaaa;

    let hours = date.getHours()
    let minutes = date.getMinutes()
    let seconds = date.getSeconds();
    let mseconds = date.getMilliseconds();

    if (hours < 10)
        hours = "0" + hours;

    if (minutes < 10)
        minutes = "0" + minutes;

    if (seconds < 10)
        seconds = "0" + seconds;

    if (mseconds < 10)
        mseconds = "00" + mseconds;
    else if (mseconds < 100)
        mseconds = "0" + mseconds;


    return cur_day + " ° " + hours + ":" + minutes + ":" + seconds + "." + mseconds;
}
