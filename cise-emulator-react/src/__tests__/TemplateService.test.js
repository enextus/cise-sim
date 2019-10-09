import API, {get} from '../__mock__/API.js';
import {getTemplateById} from "../js/templates/TemplateService";
//jest.mock('../js/api/API');





describe("TemplateService", () => {

    beforeEach(() => {
        // Clear all instances and calls to constructor and all methods:
        API.mockClear();
        get.mockClear();
    });

     it("return a template error",()=>{

            const template  = getTemplateById("", "", "", false);
            expect(API).toHaveBeenCalledTimes(1);
        }
     )


});