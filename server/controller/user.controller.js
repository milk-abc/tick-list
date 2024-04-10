const UserService = require("../service/user.service.js");
class UserController {
  async create(ctx, next) {
    //1.获取用户传递的参数
    const user = ctx.request.body;
    //2.查询参数
    const result = await UserService.create(user);
    //3.返回数据
    ctx.body = "hello";
  }
}
module.exports = new UserController();
