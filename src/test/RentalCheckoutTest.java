package test;

import org.junit.jupiter.api.Test;
import main.RentalAgreement;
import main.RentalCheckout;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

class RentalCheckoutTest {

    @Test   // Test1
    public void testInvalidDiscountPercent() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            RentalCheckout.rentalCheckout("JAKR", 5, 101, LocalDate.of(2015, 9, 3));
        });
        assertEquals("Discount percent must be in the range 0-100.", exception.getMessage());
    }

    @Test    /** Test2    7/2/2020=Thursday*/
    public void testScenario2() {
        RentalAgreement agreement = RentalCheckout.rentalCheckout("LADW", 3, 10, LocalDate.of(2020, 7, 2));
        assertNotNull(agreement);
        agreement.printRentalAgreement();
    }

    @Test   /** Test3    7/2/15=Thursday, Dt=3 */
    public void testScenario3() {
        RentalAgreement agreement = RentalCheckout.rentalCheckout("CHNS", 5, 25, LocalDate.of(2015, 7, 2));
        assertNotNull(agreement);
        agreement.printRentalAgreement();
    }

    @Test   /** Test4    9/2/15=Thursday*/
    public void testScenario4() {
        RentalAgreement agreement = RentalCheckout.rentalCheckout("JAKD", 6, 0, LocalDate.of(2015, 9, 3));
        assertNotNull(agreement);
        agreement.printRentalAgreement();
    }

    @Test  /** Test5    7/2/15=Thursday*/
    public void testScenario5() {
        RentalAgreement agreement = RentalCheckout.rentalCheckout("JAKR", 9, 0, LocalDate.of(2015, 7, 2));
        assertNotNull(agreement);
        agreement.printRentalAgreement();
    }

    @Test/** Test6    7/2/2020=Thursday*/
    public void testScenario6() {
        RentalAgreement agreement = RentalCheckout.rentalCheckout("JAKR", 4, 50, LocalDate.of(2020, 7, 2));
        assertNotNull(agreement);
        agreement.printRentalAgreement();
    }

    @Test
    public void testInvalidRentalDays() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            RentalCheckout.rentalCheckout("JAKR", 0, 20, LocalDate.of(2015, 9, 3));
        });
        assertEquals("Rental day count must be 1 or greater.", exception.getMessage());
    }


    @Test
    public void testToolNotFound() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            RentalCheckout.rentalCheckout("INVALID", 5, 20, LocalDate.of(2015, 9, 3));
        });
        assertEquals("Invalid tool code.", exception.getMessage());
    }

}