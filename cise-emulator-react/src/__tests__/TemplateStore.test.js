import TemplateService from "../__mock__/templates/TemplateService";
import TemplateStore from "../js/templates/TemplateStore";

jest.mock('../js/templates/TemplateService');



describe("TemplateStore", () => {

    beforeEach(() => {
        API.mockClear();
        get.mockClear();
    });

    it("return a template error",()=>{

            const template  = getTemplateById("", "", "", false);
            expect(API).toHaveBeenCalledTimes(1);
        }
    )

});