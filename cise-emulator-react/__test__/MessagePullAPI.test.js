import "jest-dom/extend-expect";
import "./XmlFileRef";
import mockAxios from 'axios';


describe("MessagePullAPI", () => {

        test('the pull calls the webAPI', () => {
            const callResponse = {"content": "<xml/>"};
            const pullMessage = MessagePullAPI.pull();

            expect(mockAxios.request).toHaveBeenCalledWith({
                method: 'get',
                url: '/webapi/messages'
            });
        });

        test('the pull returns a message from the API', () => {
            const callResponse = {"content": "<xml/>"};
            const pullMessage = MessagePullAPI.pull();

            expect(pullMessage.isEmpty).toBe(false);

        });

    }
)