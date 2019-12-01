import {computed, observable, action, decorate} from "mobx"

export class Event {
 value;
 id;
 viewed;

  constructor(value) {
    this.value = value;
    this.id = Date.now();
    this.viewed = false;
  }
}

export class EventStore {
 events = [];
 filter = "";


  get filteredEvents() {
    var matchesFilter = new RegExp(this.filter, "i");
    return this.events.filter(event => !this.filter || matchesFilter.test(event.value));
  }


  createEvent(value) {
    this.events.push(new Event(value));
  }

  clearViewed = () => {
    const unviewedEvents = this.events.filter(event => !event.viewed);
    this.events.replace(unviewedEvents);
  }
}

decorate(Event, {
  value: observable,
  id: observable,
  viewed: observable,
})

decorate(EventStore, {
  events: observable,
  filter: observable,
  filteredEvents: computed,
  createEvent:action,
  clearViewed:action
})
