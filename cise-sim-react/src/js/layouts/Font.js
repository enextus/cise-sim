
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

console.log("WINDOW Height:" +window.screen.availHeight);
console.log("WINDOW Width:" + window.screen.availWidth);

/*
xx-large 	Sets the font-size to an xx-large size
x-large 	Sets the font-size to an extra large size
large 	    Sets the font-size to a large size
medium 	    Sets the font-size to a medium size. This is default
small 	    Sets the font-size to a small size
x-small 	Sets the font-size to an extra small size
xx-small 	Sets the font-size to an xx-small size

smaller 	Sets the font-size to a smaller size than the parent element
larger 	    Sets the font-size to a larger size than the parent element
length 	    Sets the font-size to a fixed size in px, cm, etc. Read about length units
% 	        Sets the font-size to a percent of  the parent element's font size
initial 	Sets this property to its default value. Read about initial
inherit 	Inherits this property from its parent element. Read about inherit
 */

    export const fontSizeExtraLarge=window.screen.availWidth < 1200 ? "large"    : screen.availWidth > 1600 ? "xx-large" : "x-large";
    export const fontSizeLarge=window.screen.availWidth < 1200 ?      "medium"   : screen.availWidth > 1600 ? "x-large"  : "large";
    export const fontSizeNormal=window.screen.availWidth < 1200 ?     "small"    : screen.availWidth > 1600 ? "large"    : "medium";
    export const fontSizeSmall=window.screen.availWidth < 1200 ?      "x-small"  : screen.availWidth > 1600 ? "medium"   : "small";
    export const fontSizeExtraSmall=window.screen.availWidth < 1200 ? "xx-small" : screen.availWidth > 1600 ? "small"    : "x-small";
    export const fontSizeUltraSmall=window.screen.availWidth < 1200 ? "xx-small" : screen.availWidth > 1600 ? "xx-small"    : "xx-small";

    export const buttonSizeSmall=window.screen.availWidth < 1200 ?    "inherit"  : screen.availWidth > 1600 ? "inherit"  : "inherit"; //20px

    export const boxSizeHeight=window.screen.availWidth < 1200 ?    "600px"  : screen.availWidth > 1600 ? "800px"  : "600px";
    export const boxSizeWidth=window.screen.availWidth < 1200 ?    "600px"  : screen.availWidth > 1600 ? "1400px"  : "800px";
    export const xmlContentHeightSize=window.screen.availWidth < 1200 ?    "small"  : screen.availWidth > 1600 ? "normal"  : "small";
