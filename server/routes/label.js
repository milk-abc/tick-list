var express = require("express");

var router = express.Router();

const {
  getLabelPageListData,
  getListAllData,
  addLabel,
} = require("../service/label.service.js");
/**
 * 获取当前分页的标签
 */
router.get(
  "/getPageList/:userId/:pageCurrent/:pageSize",
  async function (req, res, next) {
    const { userId, pageCurrent, pageSize } = req.params;
    const result = await getLabelPageListData(userId, pageCurrent, pageSize);
    console.log("result", result);
    res.status(200).send({
      msg: "操作成功",
      code: 200,
      data: {
        records: [...result],
        total: result.length,
        size: Number(pageSize),
        current: Number(pageCurrent),
        pages: Math.floor(result.length / Number(pageSize)),
      },
    });
  }
);
/**
 * 获取当前用户的所有分类
 */
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
/**
 * 添加标签
 */
router.post("/add", async function (req, res, next) {
  if (req.method === "OPTIONS") {
    res.status(200).send({ msg: "操作成功", code: 200 });
  } else {
    const { name, userId } = req.body;
    const id = await addLabel(userId, name);
    res.status(200).send({
      msg: "操作成功",
      code: 200,
      data: {
        id,
        name: name,
        taskCount: 0,
      },
    });
  }
});
/**
 * 更新分类
 */
router.post("/update", async function (req, res, next) {});

/**
 * 删除分类
 */
router.get("/delete/:labelId/:userId", async function (req, res, next) {});
module.exports = router;
