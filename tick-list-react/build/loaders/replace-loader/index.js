function replaceLoader(source) {
  const handleContent = source.replace("Tick-List", "滴答清单");
  // console.log("source", source);
  return handleContent;
}
module.exports = replaceLoader;
