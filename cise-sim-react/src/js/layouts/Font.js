
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
