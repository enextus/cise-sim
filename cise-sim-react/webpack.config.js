var path = require('path');
var webpack = require('webpack');
var debug = process.env.NODE_ENV !== 'production';

module.exports = {
    context: path.join(__dirname, 'src'),
    devtool: debug ? 'inline-sourcemap' : null,
    entry: './index.js',
    output: {
        path: path.join(__dirname, '../cise-sim-api/src/main/resources/assets/static'),
        filename: 'bundle.js',
        publicPath: '/static/'
    },
    plugins: [],
    resolve: {
        extensions: ['.js', '.jsx']
    },
    module: {
        rules: [{
            test: /\.js?/,
            exclude: /(node_modules|bower_components)/,
            use: [{
                loader: 'babel-loader',
                options: {
                    plugins: [
                        ["@babel/plugin-proposal-decorators", {"legacy": true}],
                        ["@babel/plugin-proposal-class-properties", {"loose": true}]
                    ],
                    presets: ['@babel/preset-env', '@babel/preset-react'],
                }
            }],
            include: path.join(__dirname, 'src')
        }]
    },
};
