import React from "react";
import {observer} from "mobx-react";

@observer
export default class EventList extends React.Component {

    createNew(e) {
        if (e.which === 13) {
            this.props.store.createEvent(e.target.value);
            e.target.value = "";
        }
    }

    filter(e) {
        this.props.store.filter = e.target.value;
    }

    toggleComplete(event) {
        event.viewed = !event.viewed;
    }

    render() {
        const { clearViewed, filter, filteredEvents } = this.props.store;
        const eventLis = filteredEvents.map(event => (<li key={event.id}>
            <input type="checkbox" onChange={this.toggleComplete.bind(this, event)} value={event.viewed} checked={event.viewed} />
            <span>{event.value}</span>
        </li>));
        return <div>
            <h1>Events</h1>
            <input className="new" onKeyPress={this.createNew.bind(this)} />
            <input className="filter" value={filter} onChange={this.filter.bind(this)} />
            <ul>{eventLis}</ul>
            <a href="#" onClick={clearViewed}>Clear Complete</a>
        </div>;
    }
}