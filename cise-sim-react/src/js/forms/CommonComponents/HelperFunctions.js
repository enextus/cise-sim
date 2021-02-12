/*
 * BSD 3-Clause License
 *
 * Copyright (c) 2021, Joint Research Centre (JRC) All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its
 *    contributors may be used to endorse or promote products derived from
 *    this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 */

import React from "react";


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

    let cur_day =  gg + "-" + monthLiteral.substr(0,3) + "-" + aaaa;

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

    // GMT
    let mytime = date.toString();
    const y = mytime.indexOf('GMT');
    const z = mytime.indexOf(' ', y);
    const gmt = mytime.substr(y+3, z-y-3);

    return cur_day + " " + hours + ":" + minutes + ":" + seconds + "." + mseconds + " "+ gmt;
}
