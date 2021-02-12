/*
 * BSD 3-Clause License
 *
 * Copyright (c) 2021, Joint Research Centre (JRC) All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its
 *    contributors may be used to endorse or promote products derived from
 *    this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 */

import React from 'react';
import {observer} from 'mobx-react';

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