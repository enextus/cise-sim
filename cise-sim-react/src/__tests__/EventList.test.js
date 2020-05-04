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