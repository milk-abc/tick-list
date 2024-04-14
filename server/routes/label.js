var express = require("express");

var router = express.Router();

const {
  getLabelPageListData,
  getListAllData,
} = require("../service/label.service.js");
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