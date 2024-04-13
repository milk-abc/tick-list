var express = require("express");

var router = express.Router();
const { getData, addCategoryData } = require("../service/category.service");

/* GET users listing. */
// router.get("/listAll/:userId", async function (req, res, next) {
//   const userId = req.params.userId;
//   const result = await getData(userId);
//   console.log("result", result);
//   res.status(200).send({ msg: "操作成功", code: 200 });
//   next();
// });

router.post("/add", async function (req, res, next) {
  if (req.method === "OPTIONS") {
    res.status(200).send({ msg: "操作成功", code: 200 });
  } else {
    const { userId, name } = req.body;
    const result = await addCategoryData(userId, name);
    console.log("result", result);
    res.status(200).send({ msg: "操作成功", code: 200 });
  }
});

module.exports = router;
