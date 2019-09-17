
import React from 'react';
import ReactDOM from 'react-dom';
import MainApp from './MainApp';

describe("renders without crashing", () => {
    test("Matches the snapshot", () => {
        const div = document.create('MainApp');
        ReactDOM.render(  <MainApp />);
    });
});