import {EventStore} from "../src/js/EventStore"

describe("EventStore", () => {

        it("must creates new events", () => {
                const store = new EventStore();
                store.createEvent("event1");
                store.createEvent("event2");
                expect(store.events.length).toBe(2);
                expect(store.events[0].value).toBe("event1");
                expect(store.events[1].value).toBe("event2");
            }
        )

        it("must clears viewed events", () => {
                const store = new EventStore();
                store.createEvent("event1");
                store.createEvent("event2");
                store.events[1].viewed = true;
                store.clearViewed();
                expect(store.events.length).toBe(1);
                expect(store.events[0].value).toBe("event1");
            }
        )


    }
)

