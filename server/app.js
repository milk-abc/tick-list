const createError = require("http-errors");
const express = require("express");
const path = require("path");
const session = require("express-session");

const cookieParser = require("cookie-parser");
const logger = require("morgan");

const indexRouter = require("./routes/index");
const usersRouter = require("./routes/users");
const taskRouter = require("./routes/task");
const labelRouter = require("./routes/label");
const categoryRouter = require("./routes/category");

const app = express();

// view engine setup
app.set("views", path.join(__dirname, "views"));
app.set("view engine", "ejs");

app.use((req, res, next) => {
  //判断路径
  res.header("Access-Control-Allow-Origin", "*");
  res.header(
    "Access-Control-Allow-Headers",
    "Origin, X-Requested-With, Content-Type, Accept"
  );
  res.header("Access-Control-Allow-Credentials", true);
  res.header("Access-Control-Allow-Methods", "PUT, POST, GET, DELETE, OPTIONS");
  res.setHeader("Access-Control-Expose-Headers", "Authorization");
  next();
});

app.use(
  session({
    secret: "WickYo", // 对cookie进行签名
    name: "session", // cookie名称，默认为connect.sid
    resave: false, // 强制将会话保存回会话容器
    rolling: true, // 强制在每个response上设置会话标识符cookie
    cookie: {
      // 5分钟
      maxAge: 30,
    },
  })
);

app.use(logger("dev"));
app.use(express.json());
app.use(express.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, "public")));

app.use("/", indexRouter);
app.use("/users", usersRouter);
app.use("/task", taskRouter);
app.use("/label", labelRouter);
app.use("/category", categoryRouter);

// catch 404 and forward to error handler
app.use(function (req, res, next) {
  //把流转关系，转交给下一个中间件或路由
  next(createError(404));
});

// error handler
app.use(function (err, req, res, next) {
  // set locals, only providing error in development
  res.locals.message = err.message;
  res.locals.error = req.app.get("env") === "development" ? err : {};

  // render the error page
  res.status(err.status || 500);
  res.render("error");
});

module.exports = app;
