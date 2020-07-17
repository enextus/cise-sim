const path = require('path');
const webpack = require('webpack');
const debug = process.env.NODE_ENV !== 'production';

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
            },
            {
            // config for images
            test: /\.(png|svg|jpg|jpeg|gif)$/,
            use: [
                {
                    loader: 'file-loader',
                    options: {
                        outputPath: 'images',
                    }
                }
            ],
            },
        ]
    },

    externals: {
        'Config': JSON.stringify(process.env.NODE_ENV === 'production' ? require('./config.prod.json') : require('./config.dev.json'))
    }
};
