// jest.config.js
const {defaults} = require('jest-config');
module.exports = {
  "verbose": true,
  "bail": 1,
  // ...
  moduleFileExtensions: [...defaults.moduleFileExtensions, 'ts', 'tsx'],
  // ...
};