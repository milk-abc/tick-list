"use strict";
//检查node&npm版本
require("./check-versions")();
//设定环境变量
process.env.NODE_ENV = "production";
//开始导入包
const ora = require("ora"); //进度条，准确的说是编译的时候来回转的那根竖线
const rm = require("rimraf"); // 专业进行删除操作(rm命令)
const path = require("path"); // node内置包, 用于合成文件实际路径
const chalk = require("chalk"); // 输出彩色的log
const webpack = require("webpack"); // webpack实例
const config = require("../config"); // 用户配置
const webpackConfig = require("./webpack.prod.conf"); // webpack打包配置
// 进度条转起来
const spinner = ora("building for production...");
spinner.start();
// 清空旧的编译结果
// 利用config.build.assetsRoot和config.build.assetsSubDirectory合成输出的静态文件路径
// 这里直接将过去输出的的静态文件夹直接删掉, 简单粗暴
rm(path.join(config.build.assetsRoot, config.build.assetsSubDirectory), err => {
  // 删除失败直接报error
  if (err) throw err;
  // 执行webpack命令
  webpack(webpackConfig, (err, stats) => {
    // webpack打包完毕, 回调该函数
    spinner.stop();
    if (err) throw err;
    process.stdout.write(
      stats.toString({
        colors: true,
        modules: false,
        children: false, // If you are using ts-loader, setting this to true will make TypeScript errors show up during build.
        chunks: false,
        chunkModules: false
      }) + "\n\n"
    );

    if (stats.hasErrors()) {
      console.log(chalk.red("  Build failed with errors.\n"));
      process.exit(1);
    }

    console.log(chalk.cyan("  Build complete.\n"));
    console.log(
      chalk.yellow(
        "  Tip: built files are meant to be served over an HTTP server.\n" +
          "  Opening index.html over file:// won't work.\n"
      )
    );
  });
});
