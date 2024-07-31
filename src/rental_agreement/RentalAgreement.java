package rental_agreement;

import java.text.DecimalFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

public class RentalAgreement {
    private Tool tool;
    private int rentalDays;
    private LocalDate checkoutDate;
    private LocalDate dueDate;
    private double discountPercent;
    private double preDiscountCharge;
    private double discountAmount;
    private double finalCharge;

//    public RentalAgreement() {
//    }

    public RentalAgreement(Tool tool, int rentalDays, LocalDate checkoutDate, double discountPercent) {
        this.tool = tool;
        this.rentalDays = rentalDays;
        this.checkoutDate = checkoutDate;
        this.dueDate = checkoutDate.plusDays(rentalDays);
        this.discountPercent = discountPercent;

        calculateFinalCharges();
    }

    private void calculateFinalCharges() {
        int chargeDays = calculateChargeDays();
        preDiscountCharge = chargeDays * tool.getDailyCharge();
        discountAmount = preDiscountCharge * (discountPercent / 100);
        finalCharge = preDiscountCharge - discountAmount;
    }

    private int calculateChargeDays() {
        int chargeDays = 0;
        LocalDate currentDate = checkoutDate.plusDays(1);

        while (!currentDate.isAfter(dueDate)) {
            if (isChargeableDay(currentDate)) {
                chargeDays++;
            }
            currentDate = currentDate.plusDays(1);
        }

        return chargeDays;
    }

    private boolean isChargeableDay(LocalDate date) {
        if (date.getDayOfWeek().getValue() >= 6 && !tool.isChargeOnWeekends()) {
            return false;
        }

        if (isHoliday(date) && !tool.isChargeOnHolidays()) {
            return false;
        }

        return true;
    }

    private boolean isHoliday(LocalDate date) {
        // Independence Day
        LocalDate independenceDay = LocalDate.of(date.getYear(), 7, 4);
        if (independenceDay.getDayOfWeek().getValue() == 6) {
            independenceDay = independenceDay.minusDays(1);
        } else if (independenceDay.getDayOfWeek().getValue() == 7) {
            independenceDay = independenceDay.plusDays(1);
        }

        // Labor Day
        LocalDate laborDay = LocalDate.of(date.getYear(), 9, 1).with(TemporalAdjusters.dayOfWeekInMonth(1, DayOfWeek.MONDAY));

        return date.equals(independenceDay) || date.equals(laborDay);
    }

    public void printRentalAgreement() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yy");
        DecimalFormat currencyFormatter = new DecimalFormat("$#,##0.00");
        DecimalFormat percentFormatter = new DecimalFormat("#0%");

        System.out.println("Tool code: " + tool.getCode());
        System.out.println("Tool type: " + tool.getType());
        System.out.println("Tool brand: " + tool.getBrand());
        System.out.println("Rental days: " + rentalDays);
        System.out.println("Check out date: " + checkoutDate.format(dateFormatter));
        System.out.println("Due date: " + dueDate.format(dateFormatter));
        System.out.println("Daily rental charge: " + currencyFormatter.format(tool.getDailyCharge()));
        System.out.println("Charge days: " + calculateChargeDays());
        System.out.println("Pre-discount charge: " + currencyFormatter.format(preDiscountCharge));
        System.out.println("Discount percent: " + percentFormatter.format(discountPercent / 100));
        System.out.println("Discount amount: " + currencyFormatter.format(discountAmount));
        System.out.println("Final charge: " + currencyFormatter.format(finalCharge));
    }
}
