

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
