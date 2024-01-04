import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrendUtils {
    /**
     * 计算近期时间趋势图
     * @param maps sql查询的时间数据（年/月/日/时）
     * @param nums 近几（年/月/日/时）数据
     * @param key 存储（年/月/日/时）时间对应字段
     * @param value 存储（年/月/日/时）值对应字段
     * @param type 时间类型（year/month/day/hour）
     * @return
     */
    public static Map<String, List> recentTimeTrend(List<Map> maps, Integer nums, String key, String value, String type){
        LocalDateTime date = LocalDateTime.now();//获取当前时间
        List<String> valueList = new ArrayList<>();//趋势图的值
        Map<Integer,String> target = new HashMap<>();
        List<Integer> dateList = new ArrayList<>();//趋势图的x轴
        Map<String,List> result = new HashMap<>();
        for (int i = 0; i < maps.size(); i++) {
            target.put(Integer.valueOf(maps.get(i).get(key).toString()),maps.get(i).get(value).toString());
        }
        for (int i = nums-1; i >= 0; i--) {
            int time = 0;
            switch (type){
                case "hour":
                    time = date.minusHours(i).getHour();
                    break;
                case "day":
                    time = date.minusDays(i).getDayOfMonth();
                    break;
                case "month":
                    time = date.minusMonths(i).getMonthValue();
                    break;
                case "year":
                    time = date.minusYears(i).getYear();
                    break;
                default:
                    break;
            }
            valueList.add(target.getOrDefault(time, "0"));
            dateList.add(time);
        }
        result.put("dateList",dateList);
        result.put("valueList",valueList);
        return result;
    }

    //key默认date,value默认为value
    public static Map<String, List> recentTimeTrend(List<Map> maps,Integer nums,String type){
        return recentTimeTrend(maps,nums,"date","value",type);
    }

    /**
     * 计算当前时间趋势图
     * @param maps sql查询的时间数据（月/日/时）
     * @param key 存储（月/日/时）时间对应字段
     * @param value 存储（月/日/时）值对应字段
     * @param type 时间类型（month/day/hour）
     * @param intercept 是否截取时间到当前时间
     * @return
     */
    public static Map<String,List> currentTimeTrend(List<Map> maps,String key,String value,String type,Boolean intercept){
        int timeBegin = 0;
        int timeEnd = 23;
        int maxTime = 0;
        Map<String,List> result = new HashMap<>();
        LocalDate date = LocalDate.now();//获取当前时间
        LocalDateTime dateTime = LocalDateTime.now();
        List<String> valueList = new ArrayList<>();//趋势图的值
        List<Integer> dateList = new ArrayList<>();//趋势图的x轴
        Map<Integer,String> target = new HashMap<>();
        for (int i = 0; i < maps.size(); i++) {
            target.put(Integer.valueOf(maps.get(i).get(key).toString()),maps.get(i).get(value).toString());
        }
        switch (type){
            case "hour":
                timeEnd = 23;
                timeBegin = 0;
                maxTime = dateTime.getHour();
                break;
            case "day":
                timeEnd = date.lengthOfMonth();
                timeBegin = 1;
                maxTime = dateTime.getDayOfMonth();
                break;
            case "month":
                timeEnd = 12;
                timeBegin = 1;
                maxTime = dateTime.getMonthValue();
                break;
            default:
                break;
        }
        for (int i = timeBegin; i <= timeEnd; i++) {
            dateList.add(i);
        }
        if (!intercept){
            maxTime = timeEnd;
        }
        for (int i = timeBegin; i <= maxTime; i++) {
            valueList.add(target.getOrDefault(i,"0"));
        }
        result.put("dateList",dateList);
        result.put("valueList",valueList);
        return result;
    }

    //key默认date,value默认value,intercept默认true
    public static Map<String,List> currentTimeTrend(List<Map> maps,String type){
        return currentTimeTrend(maps,"date","value",type,true);
    }

    //key默认date,value默认value
    public static Map<String,List> currentTimeTrend(List<Map> maps,String type,Boolean intercept){
        return currentTimeTrend(maps,"date","value",type,intercept);
    }
}