import {action, observable} from 'mobx';

export default class NotificationStore {

    @observable notifications   = [];

    @action
    enqueue(notification) {
        this.notifications.push({
            key: new Date().getTime() + Math.random(),
            ...notification,
        });
    };

    @action
    remove(notification) {
        this.notifications.remove(notification);
    };

    @action
    removeByKey(key) {
        let selectedNotification = this.notifications.find(function (element) {
            key === element.key;
        });
        this.remove(selectedNotification);
    };

}
