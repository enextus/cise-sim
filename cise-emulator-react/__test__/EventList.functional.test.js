import { shallow } from 'enzyme'
import React from "react"

import EventList from "../src/js/EventList";
import { EventStore } from "../src/js/EventStore"

describe("EventList.functional", () => {

  it("must filters events", () => {
    const store = new EventStore()

    store.createEvent("event1")
    store.createEvent("event2")
    store.createEvent("event3")
    store.filter = "2"

    const wrapper = shallow(<EventList store={store} />)

    expect(wrapper.find("li").length).toBe(1)
    expect(wrapper.find("li span").at(0).text()).toBe("event2")
  })

  it("clears completed events when 'clear completed' is clicked", () => {
    const store = new EventStore()

    store.createEvent("event1")
    store.createEvent("event2")
    store.createEvent("event3")
    store.events[0].viewed = true
    store.events[1].viewed = true

    const wrapper = shallow(<EventList store={store} />)

    wrapper.find("a").simulate("click")

    expect(wrapper.find("li").length).toBe(1)
    expect(wrapper.find("li span").at(0).text()).toBe("event3")
    expect(store.events.length).toBe(1)
  })
})
