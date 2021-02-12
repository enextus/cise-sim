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

// describe ("EventList", function() {
//
//     //don't use an arrow function...preserve the value of "this"
//     beforeEach(function() {
//         this.store = {
//             filteredEvents: [
//                 {value: "event1", id: 111, viewed: false},
//                 {value: "event2", id: 222, viewed: false},
//                 {value: "event3", id: 333, viewed: false},
//             ],
//             filter: "test",
//             createEvent: (val) => {
//                 this.createEventCalled = true
//                 this.eventValue = val
//             },
//         }
//     })
//
//     it("must renders filtered events", function() {
//         const wrapper = shallow(<EventList store={this.store} />)
//         expect(wrapper.find("li span").at(0).text()).toBe("event1")
//         expect(wrapper.find("li span").at(1).text()).toBe("event2")
//         expect(wrapper.find("li span").at(2).text()).toBe("event3")
//     })
//
//     it("must calls createEvent on enter", function(){
//         const wrapper=shallow(<EventList store= {this.store}/>);
//         wrapper.find("input.new").at(0).simulate(
//             "keypress",
//             {which:13 ,
//                 target: {value: "ńewEvent"}
//             }
//         )
//         expect(this.createEventCalled).toBe(true);
//         expect(this.eventValue).toBe("ńewEvent");
//     })
//
//     it("must updates store filter",
//         function() {
//             const wrapper = shallow(<EventList store={this.store} />)
//             wrapper.find("input.filter").at(0) .simulate('change', {target: {value: 'filter'}})
//             expect(this.store.filter).toBe("filter")
//         }
//     )
// })