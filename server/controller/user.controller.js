const UserService = require("../service/user.service.js");
class UserController {
  async create(req, res, next) {
    //1.获取用户传递的参数
    const user = req.body;
    //2.查询参数
    const result = await UserService.create(user);
    //3.返回数据
    res.status(200).send({ data: "success", code: 200 });
  }
}
module.exports = new UserController();
