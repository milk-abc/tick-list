import request from "@/utils/request";

export function getDayData() {
  return request({
    url: `task/countTaskForDay/${this.global.user.id}`,
    method: "get",
    params: { token }
  });
}
