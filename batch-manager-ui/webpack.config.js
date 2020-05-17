const HtmlWebpackPlugin = require('html-webpack-plugin');
const path = require('path');

module.exports = {
    entry: './src/App.js',
    devtool: 'sourcemaps',
    cache: true,
    mode: 'development',
    devServer: {
        contentBase: "./src",
        hot: true,
        port: 3000,
        historyApiFallback: true
    },
    output: {
        path: __dirname,
        filename: './src/built/bundle.js'
    },
    module: {
        rules: [
            {
                test: path.join(__dirname, '.'),
                exclude: /(node_modules)/,
                use: [{
                    loader: 'babel-loader',
                    options: {
                        presets: ["@babel/preset-env", "@babel/preset-react"]
                    }
                }]
            }
        ]
    }
};