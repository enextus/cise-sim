import  NotificationStore  from "../js/notifications/NotificationStore"


describe("NotificationStore", () => {
    it("creates new Notification", () => {
        const mystore= new  NotificationStore();
        mystore.enqueue("snack1")
        mystore.enqueue("snack2")
        expect(mystore.notifications.length).toBe(2)
    })

    it("remove notification by key", () => {
        const mystore= new  NotificationStore();
        mystore.enqueue("snack1")
        expect(mystore.notifications.length).toBe(1)
    })

});