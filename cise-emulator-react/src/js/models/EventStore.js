import { computed, observable, action } from "mobx"

export class Event {
  @observable value;
  @observable id;
  @observable viewed;

  constructor(value) {
    this.value = value;
    this.id = Date.now();
    this.viewed = false;
  }
}

export class EventStore {
  @observable events = [];
  @observable filter = "";

  @computed 
  get filteredEvents() {
    var matchesFilter = new RegExp(this.filter, "i");
    return this.events.filter(event => !this.filter || matchesFilter.test(event.value));
  }

  @action
  createEvent(value) {
    this.events.push(new Event(value));
  }

  @action
  clearViewed = () => {
    const unviewedEvents = this.events.filter(event => !event.viewed);
    this.events.replace(unviewedEvents);
  }
}

export default new EventStore 


