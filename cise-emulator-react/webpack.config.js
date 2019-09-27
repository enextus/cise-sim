var path = require('path');
var webpack = require("webpack");
var debug = process.env.NODE_ENV !== 'production';

module.exports = {
    context: path.join(__dirname, 'src'),
    devtool: debug ? 'inline-sourcemap' : null,
    mode: 'development',
    entry: [
        './js/index.js'
    ],
    output: {
        path: path.join(__dirname, 'dist'),
        filename: 'bundle.js',
        publicPath: '/static/'
    },
    devServer: {
        proxy: {
            '/webapi': {
                bypass: (req, res) => {
                  console.log("called {}",req.url);
                  if (req.url.indexOf('messages')!==-1){
                    res.send({'status':204, 'body':'<xml>body</xml>','acknowledge':'<xml>acknowledge</xml>'})
                  }
                  if (req.url.indexOf('templates')!==-1){
                        res.send([{"path":"/opt/jboss/EuciseData/sim-lsa/messageXmlFiles/Operations/PushTemplate.xml","name":"PushTemplate.xml","hash":350191154},
                      {"path":"/opt/jboss/EuciseData/sim-lsa/messageXmlFiles/Operations/FeedbackTemplate.xml","name":"FeedbackTemplate.xml","hash":-955324643},
                      {"path":"/opt/jboss/EuciseData/sim-lsa/messageXmlFiles/Operations/AcknowledgementTemplate.xml","name":"AcknowledgementTemplate.xml","hash":524802278}])
                    }
                }
            }
        }
    },
    plugins: [],
    resolve:
        {
            extensions: ['.js', '.jsx']
        }
    ,
    module: {
        rules: [{
            test: /\.jsx?$/,
            use: ['babel-loader'],
            include: path.join(__dirname, 'src')
        }]
    }
}
;
