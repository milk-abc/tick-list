var express = require("express");

var router = express.Router();

const {
  getListAllData,
  addCategoryData,
  getCategoryPageListData,
} = require("../service/category.service");

/**
 * 新增分类
 */
router.post("/add", async function (req, res, next) {
  if (req.method === "OPTIONS") {
    res.status(200).send({ msg: "操作成功", code: 200 });
  } else {
    /**
     * 添加分类后需要刷新分类表，重新get请求返回最新的分类表
     */
    const { userId, name } = req.body;
    const id = await addCategoryData(userId, name);
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
router.get("/delete/:categoryId/:userId", async function (req, res, next) {});

/**
 * 获取当前分页的分类
 */
router.get(
  "/getPageList/:userId/:pageCurrent/:pageSize",
  async function (req, res, next) {
    const { userId, pageCurrent, pageSize } = req.params;
    const result = await getCategoryPageListData(userId, pageCurrent, pageSize);
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

/**
 * 获取当前用户的所有分类
 */
router.get("/listAll/:userId", async function (req, res, next) {
  const { userId } = req.params;
  const result = await getListAllData(userId);
  res.status(200).send({
    msg: "操作成功",
    code: 200,
    data: [...result],
  });
});

module.exports = router;
