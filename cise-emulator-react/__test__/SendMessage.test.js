import {render} from "react-testing-library";
import "jest-dom/extend-expect";
import "../components/Panels/SendMessage/XmlFileRef";


describe("SendMessage", () => {
        it("renders", () => {
            const messageRefTemplateService = "";
            const option = {'hash': "test", 'path': 'test', 'name': 'test'};
            const {asFragment} = render(<XmlFileRef parent={messageRefTemplateService} option={option}/>);
            expect(asFragment()).toMatchSnapshot();
        });


        it("renders", () => {
            const messageRefTemplateService = "";
            const option = {'hash': "test", 'path': 'test', 'name': 'test'};
            const {asFragment} = render(<XmlFileRef parent={messageRefTemplateService} option={option}/>);
            expect(asFragment()).toMatchSnapshot();
        });
    }
)
