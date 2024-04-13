Date.prototype.format = function (fmt) {
  var o = {
    "M+": this.getMonth() + 1, //月份
    "d+": this.getDate(), //日
    "h+": this.getHours(), //小时
    "m+": this.getMinutes(), //分
    "s+": this.getSeconds(), //秒
    "q+": Math.floor((this.getMonth() + 3) / 3), //季度
    S: this.getMilliseconds(), //毫秒
  };
  if (/(y+)/.test(fmt)) {
    fmt = fmt.replace(
      RegExp.$1,
      (this.getFullYear() + "").substr(4 - RegExp.$1.length)
    );
  }
  for (var k in o) {
    if (new RegExp("(" + k + ")").test(fmt)) {
      fmt = fmt.replace(
        RegExp.$1,
        RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length)
      );
    }
  }
  return fmt;
};
//N天时间戳，时间戳指从1970-01-01 0点开始到某天的毫秒数
function timestamp(days) {
  return days * 24 * 60 * 60 * 1000;
}
// //几天前，一般以现在的时间为基准，参数默认值老的浏览器可能不支持。
function days_ago(day, base_time = Date.now()) {
  return new Date(base_time - timestamp(day));
}
function tomorrow() {
  return days_ago(-1);
}
function today() {
  return new Date();
}
// //最常用的今天、昨天、明天，返回时间戳
// function yesterday() {
//   return days_ago(1);
// }
// function tomorrow() {
//   return days_ago(-1);
// }
// function today() {
//   return new Date();
// }
///字符串格式化的日期，英文常用，如2021-01-01
function en_format(func) {
  return func.format("yyyy-MM-dd");
}
function en_today() {
  return en_format(today());
}
function en_yesterday() {
  return en_format(days_ago(1));
}
function en_tomorrow() {
  return en_format(days_ago(-1));
}
function cn_format(func) {
  return func.format("yyyy年MM月dd日");
}
//中文版今天，如2020年02月22日
function cn_today() {
  return cn_format(today());
}
//中文版昨天，如1949年10月01日
function cn_yesterday() {
  return cn_format(days_ago(1));
}
//中文版明天，如2035年09月09日
function cn_tomorrow() {
  return cn_format(days_ago(-1));
}
module.exports = {
  en_tomorrow,
  en_today,
  days_ago,
  en_format,
};
