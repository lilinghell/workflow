const path = require('path');
const apiMocker = require('mocker-api');

module.exports = {
  pluginOptions: {
    quasar: {
      theme: 'mat',
      importAll: true
    }
  },
  devServer: {
    overlay: {
      errors: true
    },
    // before(app) {
    //   // unnecessary fill in all mock files
    //   apiMocker(app, path.resolve('./mock/index.js'));
    // }
    proxy: "http://127.0.0.1:8085/",
  }
};
