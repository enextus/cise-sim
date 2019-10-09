import { observable, action, decorate } from "mobx";
import React from 'react';

export default class NotificationStore {

    notifications   = [];


    enqueue(notification) {
        this.notifications.push({
            key: new Date().getTime() + Math.random(),
            ...notification,
        });
    };


    remove(notification) {
        this.notifications.remove(notification);
    };


    removeByKey(key) {
        let selectedNotification = this.notifications.find(function (element) {
            key === element.key;
        });
        let existingValue = (selectedNotification !== undefined )
        this.remove(selectedNotification);
        return existingValue;
};



}
decorate(NotificationStore, {
    notifications: observable,
    enqueue: action,
    remove: action,
    removeByKey: action
});

