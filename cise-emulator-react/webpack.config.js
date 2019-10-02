var path = require('path'); 
var webpack = require('webpack');
var debug = process.env.NODE_ENV !== 'production';

module.exports = {
  context: path.join(__dirname, 'src'),
  devtool: debug ? 'inline-sourcemap' : null,
  entry: './js/index.js',
  devServer: {
    proxy: {
      '/webapi': {
       bypass: (req, res) => {
         if (req.url.indexOf('/webapi/messages')!== -1){
            console.log('.')
            res.send({
            'status':200,
            'body':'<?xml version="1.0"?><!--{"id":"34840-34534-943443", "requireAck":false, "correlationId":"23434-3443-434"}--> <xsl:stylesheet xmlns:xsl="http://www.w3.org/TR/WD-xsl"><ExpansionPanelDetails><span className={classes.message}><Highlight></Highlight></span></xsl:stylesheet>',
            'acknowledge':'<?xml version="1.0"?><!--{"id":"34840-34534-943443", "requireAck":false, "correlationId":"23434-3443-434"}--> <xsl:stylesheet xmlns:xsl="http://www.w3.org/TR/WD-xsl"><ExpansionPanelDetails><span className={classes.acknowledgement}><Highlight></Highlight></span></xsl:stylesheet>'});
         }''
         if (req.url.indexOf('/webapi/templates')!== -1){
                     console.log('bypass.0TemplateList.')
                     res.send({
                     'status':200,
                     'body':
                     '<?xml version="1.0"?> ',
                     'acknowledge':
                     '<?xml version="1.0"?> '});
                  }
       } 
      }
    }
  },
  output: {
      path: path.join(__dirname, 'dist'),
      filename: 'bundle.js',
      publicPath: '/static/'
  },
  plugins: [
  ],
  resolve: {
    extensions: ['.js', '.jsx']
  },
  module: {
    rules: [{
      test: /\.js?/,
      exclude: /(node_modules|bower_components)/,
      use: [{
        loader: 'babel-loader',
        options:{
          plugins: [
            ["@babel/plugin-proposal-decorators", { "legacy": true }],
            ["@babel/plugin-proposal-class-properties", { "loose": true }]
          ],
          presets:['@babel/preset-env','@babel/preset-react'],
        }
    }],
      include: path.join(__dirname, 'src')
    }]
  },
};
