import request from "@/utils/request";

export function getDayData() {
  return request({
    url: `task/countTaskForDay/${this.$store.state.userInfo.id}`,
    method: "get",
    params: { token }
  });
}
