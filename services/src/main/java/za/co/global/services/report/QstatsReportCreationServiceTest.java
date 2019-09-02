package za.co.global.services.report;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class QstatsReportCreationServiceTest {


    public static void main(String[] args) {
        BigDecimal aa = new BigDecimal(1.00113189).setScale(4, RoundingMode.HALF_DOWN);
        System.out.println("HALF UP : "+aa);

        BigDecimal aa1 = new BigDecimal(1.00113189).setScale(4, BigDecimal.ROUND_DOWN);
        System.out.println("ROUND_HALF_UP : "+aa1);
    }
}
