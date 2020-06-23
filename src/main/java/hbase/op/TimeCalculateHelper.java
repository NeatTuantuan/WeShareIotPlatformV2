package hbase.op;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ll
 * @version 1.0.0
 * @ClassName TimeCalculateHepler
 * @Description  处理时间问题
 * @Date 2019/9/18 9:07
 */
public class TimeCalculateHelper {

    private final LocalDateTime defaultLocalDateTime = LocalDateTime.of(1970,1,1,8,0,0);  //初始化默认时间，小时+8是因为中英时差

    /**
     * @Description:  获取两个时间之间的时间段。使用场景：根据返回时间确定要查找的表的时间后缀列表
     * @author: ll
     * @date: 2019/9/18
     * @version 1.0.0
     */
    public List<String> getDatePostfixListFromTwoDate(long startTime , long endTime){
        LocalDateTime startDate = defaultLocalDateTime.plusSeconds(startTime/1000);
        LocalDateTime endDate = defaultLocalDateTime.plusSeconds(endTime/1000);
        List<String> datePostfixs = new ArrayList<String>();
        int startYear = startDate.getYear();
        int endYear = endDate.getYear();
        int startMonth = startDate.getMonthValue();
        int endMonth = endDate.getMonthValue();
        int endMonthIndex = 0;
        int startMonthIndex = startMonth;

        for(int year = startYear ; year <= endYear ; year++) {
            if (year != endYear)
                endMonthIndex = 12;
            else
                endMonthIndex = endMonth;
            if(year != startYear)
                startMonthIndex = 1;
            else
                startMonthIndex = startMonth;
            for (int month = startMonthIndex; month <= endMonthIndex; month++) {
                if (month < 10) datePostfixs.add(year + "0" + month);
                else {
                    datePostfixs.add(year +""+ month);
                }
            }
        }
        System.out.println( datePostfixs );
        return datePostfixs;
    }

    /**
     * @Description:  根据输入日期，获得要查询(插入)的表的时间后缀
     * @author: ll
     * @date: 2019/9/18
     * @version 1.0.0
     *
     */
    public String getDatePostfix(long time){
        LocalDateTime date = defaultLocalDateTime.plusSeconds(time/1000);
        int year = date.getYear();
        int month = date.getMonthValue();
        if(month < 10)
            return year+"0"+month;
        return year+""+month;
    }

    /**
     * @Description: 时间做减法
     * @author: ll
     * @date: 2019/9/18
     * @version 1.0.0
     * @param time 被减数
     * @param day 减数，单位是时间
     */
    public long minusDays(long time , int day){
        return time - day*24*3600*1000;
    }

}
