import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.Objects;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length < 5) {
            System.out.println("Usage: java -jar CoolapkFollow.jar uid userName token fansNumber isOperate");
            System.out.println("\tuid: your coolapk account uid");
            System.out.println("\tuserName: your coolapk username");
            System.out.println("\tfansNumber: users with less than or equal this number of followers will be unfollowed");
            System.out.println("\tisOperate: whether really do operate or not (true/false)");
            System.exit(1);
        }
        CoolapkConfig.uid = args[0];
        CoolapkConfig.userName = args[1];
        CoolapkConfig.token = args[2];
        int fansNumber = Integer.parseInt(args[3]);
        boolean isOperate = Boolean.parseBoolean(args[4]);
        CoolapkService coolapkService = new ServiceCreator().create(CoolapkService.class);
        JsonObject body = coolapkService.getUserDetail(CoolapkConfig.uid).execute().body();
        JsonObject asJsonObject = Objects.requireNonNull(body).getAsJsonObject("data");
        JsonElement jsonElement = asJsonObject.get("follow");
        int pageTotalCount = (jsonElement.getAsInt() / 19) + 1;
        int unFollowCount = 0;
        for (int i = 1; i <= pageTotalCount; i++) {
            JsonArray data = Objects.requireNonNull(coolapkService.getFollowList(CoolapkConfig.uid, String.valueOf(i)).execute().body()).getAsJsonArray("data");
            if (data.size() == 0) {
                break;
            }
            System.out.println("------------------------------------------");
            System.out.println("正在对第 " + i + " 页执行操作，共 " + pageTotalCount + " 页（不准确）");
            for (int j = 0; j < data.size(); j++) {
                JsonObject followObject = data.get(j).getAsJsonObject();
                String fUserName = followObject.get("fusername").getAsString();
                String fUID = followObject.get("fuid").getAsString();
                String fUserAvatar = followObject.get("fUserAvatar").getAsString();
                int fFans = followObject.getAsJsonObject("fUserInfo").get("fans").getAsInt();
                int fVerifyStatus = followObject.getAsJsonObject("fUserInfo").get("verify_status").getAsInt();
                if ((fUserName.contains("酷友") || fUserAvatar.equals("http://avatar.coolapk.com/images/avatar_middle.gif") || fFans <= fansNumber) && fVerifyStatus == 0) {
                    if (isOperate) {
                        coolapkService.unFollow(fUID).execute();
                    }
                    unFollowCount++;
                    System.out.println("已取消关注第 " + unFollowCount + " 个关注者 名称：" + fUserName + " UID：" + fUID + " 跟随者：" + fFans + " 认证状态：" + fVerifyStatus + " 头像：" + fUserAvatar);
                } else {
                    System.out.println("\t\t\t\t名称：" + fUserName + " UID：" + fUID + " 跟随者：" + fFans + " 认证状态：" + fVerifyStatus + " 头像：" + fUserAvatar);
                }
            }
            System.out.println("------------------------------------------");
        }
    }
}
