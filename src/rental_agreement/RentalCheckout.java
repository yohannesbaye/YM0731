package rental_agreement;

import java.time.LocalDate;

public class RentalCheckout {
    public static RentalAgreement rentalCheckout(String toolCode, int rentalDays, double discountPercent, LocalDate checkoutDate) throws IllegalArgumentException {
        if (rentalDays < 1) {
            throw new IllegalArgumentException("Rental day count must be 1 or greater.");
        }

        if (discountPercent < 0 || discountPercent > 100) {
            throw new IllegalArgumentException("Discount percent must be in the range 0-100.");
        }

        Tool tool = getToolByCode(toolCode);
        if (tool == null) {
            throw new IllegalArgumentException("Invalid tool code.");
        }

        return new RentalAgreement(tool, rentalDays, checkoutDate, discountPercent);
    }

    private static Tool getToolByCode(String toolCode) {
        switch (toolCode) {
            case "CHNS":
                return new Tool("CHNS", "Chainsaw", "Stihl", 1.49, false, true);
            case "LADW":
                return new Tool("LADW", "Ladder", "Werner", 1.99, true, false);
            case "JAKD":
                return new Tool("JAKD", "Jackhammer", "DeWalt", 2.99, false, false);
            case "JAKR":
                return new Tool("JAKR", "Jackhammer", "Ridgid", 2.99, false, false);
            default:
                return null;
        }
    }
}
