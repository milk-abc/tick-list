var express = require("express");

var router = express.Router();

const {
  getListAllData,
  addCategoryData,
  getCategoryPageListData,
} = require("../service/category.service");

router.post("/add", async function (req, res, next) {
  if (req.method === "OPTIONS") {
    res.status(200).send({ msg: "操作成功", code: 200 });
  } else {
    /**
     * 添加分类后需要刷新分类表，重新get请求返回最新的分类表
     */
    const { userId, name } = req.body;
    const result = await addCategoryData(userId, name);
    res.status(200).send({
      msg: "操作成功",
      code: 200,
      data: {
        id: Symbol(name),
        name: name,
        taskCount: 0,
      },
    });
  }
});

router.get("/get", async function (req, res, next) {
  /**
   * 获取分类表
   */
  const { userId } = req.query;
  const result = await getData(userId);
  res.status(200).send({
    msg: "操作成功",
    code: 200,
    data: result,
  });
});

router.get(
  "/getPageList/:userId/:pageCurrent/:pageSize",
  async function (req, res, next) {
    const { userId, pageCurrent, pageSize } = req.params;
    const result = await getCategoryPageListData(userId, pageCurrent, pageSize);
    console.log("result", result);
    const { records, total } = result;
    res.status(200).send({
      msg: "操作成功",
      code: 200,
      data: {
        records,
        total,
        size: Number(pageSize),
        current: Number(pageCurrent),
        pages: Math.ceil(total / Number(pageSize)),
      },
    });
  }
);

router.get("/listAll/:userId", async function (req, res, next) {
  const { userId } = req.params;
  const result = await getListAllData(userId);
  console.log("result", result);
  res.status(200).send({
    msg: "操作成功",
    code: 200,
    data: [...result],
  });
});

module.exports = router;
