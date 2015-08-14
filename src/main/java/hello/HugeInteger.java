package hello;

import java.util.function.Predicate;


public class HugeInteger {
    static int MAX_LENGTH = 40;
    int digits[];
    int sign;
    int numberOfDigits;

    public HugeInteger() {
        digits = new int[MAX_LENGTH];
        numberOfDigits = 0;
        sign = 1;
        for (int i = 0; i < MAX_LENGTH; i++) {
            digits[i] = 0;
        }
    }

    public void parseDigits(String number) {
        if (number.charAt(0) == '-') {
            sign = -1;
            number = number.substring(1);
        }
        int i = number.length() - 1;
        int j = MAX_LENGTH - 1;
        while (i >= 0) {
            digits[j] = number.charAt(i) - '0';
            j--;
            i--;
        }
        numberOfDigits = MAX_LENGTH - j - 1;
    }

    public HugeInteger(String number) {
        digits = new int[MAX_LENGTH];
        sign = 1;
        for (int i = 0; i < MAX_LENGTH; i++) {
            digits[i] = 0;
        }
        parseDigits(number);
    }

    /* Predicates:
    isEqualTo,
    isGreaterThan
    isLessThan
    isGreaterThanEqual
    isLessThanEqual
     */

    Predicate<HugeInteger> isZero = (HugeInteger h1) -> {
        return h1.getNumberOfDigits() == 0;
    };

    Predicate<HugeInteger> isEqual = (HugeInteger h1) -> {
        if (h1.getNumberOfDigits() != this.getNumberOfDigits() ||
                h1.getSign() != this.getSign())
            return false;
        else {
            int i = 1;
            int arr1[] = h1.getDigits();
            int arr2[] = this.getDigits();
            while (i <= h1.getNumberOfDigits()) {
                if (arr1[MAX_LENGTH - i] != arr2[MAX_LENGTH - i])
                    return false;
                i++;
            }
            return true;
        }
    };

    private Predicate<HugeInteger> isMagnitudeGreaterThan = (HugeInteger h2) -> {
        if (this.getNumberOfDigits() != h2.getNumberOfDigits())
            return (this.getNumberOfDigits() - h2.getNumberOfDigits()) > 0;

        else {
            int i = MAX_LENGTH - this.getNumberOfDigits();
            int arr1[] = this.getDigits();
            int arr2[] = h2.getDigits();
            while (i < 40) {
                if (arr1[i] > arr2[i])
                    return true;
                else if (arr1[i] < arr2[i])
                    return false;
                i++;
            }
            return false;
        }
    };

    Predicate<HugeInteger> isGreaterThan = (HugeInteger h2) -> {

        if (this.getSign() - h2.getSign() != 0)
            return this.getSign() > 0;

        return isMagnitudeGreaterThan.test(h2);

    };


    Predicate<HugeInteger> isLessThan = (HugeInteger h2) -> {
        return !(isGreaterThan.test(h2)) && !(isEqual.test(h2));
    };

    Predicate<HugeInteger> isNotEqualTo = (HugeInteger h2) -> {
        return !(this.isEqual.test(h2));
    };


    Predicate<HugeInteger> isGreaterThanEqualTo = (HugeInteger h2) -> {
        return (this.isEqual.test(h2)) || (this.isGreaterThan.test(h2));
    };

    Predicate<HugeInteger> isLessThanEqualTo = (HugeInteger h2) -> {
        return (this.isEqual.test(h2)) || (this.isLessThan.test(h2));
    };


    public int[] getDigits() {
        return digits;
    }

    public int getNumberOfDigits() {
        return numberOfDigits;
    }

    public void setNumberOfDigits(int numberOfDigits) {
        this.numberOfDigits = numberOfDigits;
    }

    private static HugeInteger addUtil(HugeInteger h1, HugeInteger h2) {
        int i = 1;
        int j = 1;
        int k = MAX_LENGTH - 1;
        HugeInteger sum = new HugeInteger();

        int digits1[] = h1.getDigits();
        int digits2[] = h2.getDigits();
        int digitsAns[] = sum.getDigits();

        int carry = 0;
        while (i <= h1.getNumberOfDigits() || j <= h2.getNumberOfDigits()) {
            int temp = 0;
            temp = digits1[MAX_LENGTH - i] + digits2[MAX_LENGTH - j] + carry;
            digitsAns[k] = temp % 10;
            carry = temp / 10;
            i++;
            j++;
            k--;
        }
        if (carry > 0) {
            digitsAns[k] = carry;
            sum.setNumberOfDigits(MAX_LENGTH - k);
        } else
            sum.setNumberOfDigits(MAX_LENGTH - k - 1);

        return sum;
    }

    private static HugeInteger subtractUtil(HugeInteger h1, HugeInteger h2) {
        HugeInteger ans = new HugeInteger();



        if (!(h1.isMagnitudeGreaterThan.test(h2))) {
            ans.setSign(-1);
            HugeInteger temp = h1;
            h1 = h2;
            h2 = temp;
        }



        int i = 1;
        int j = 1;
        int k = MAX_LENGTH - 1;


        int digits1[] = h1.getDigits();
        int digits2[] = h2.getDigits();
        int digitsAns[] = ans.getDigits();

        int borrow = 0;
        while (i <= h1.getNumberOfDigits() || j <= h2.getNumberOfDigits()) {

            if ((digits1[MAX_LENGTH - i] - borrow) < digits2[MAX_LENGTH - j]) {
                digitsAns[k] = (digits1[MAX_LENGTH - i] + 10) - digits2[MAX_LENGTH - j];
                borrow = 1;
            } else {
                digitsAns[k] = digits1[MAX_LENGTH - i] - digits2[MAX_LENGTH - j] - borrow;
                borrow = 0;
            }
            k--;
            i++;
            j++;
        }

        i = 0;
        while (i < MAX_LENGTH && digitsAns[i] == 0) {
            i++;
        }
        ans.setNumberOfDigits(MAX_LENGTH - i);

        return ans;
    }

    public static HugeInteger add(HugeInteger h1, HugeInteger h2) {
        if (h1.getSign() == -1 && h2.getSign() == -1) {
            HugeInteger ans = addUtil(h1, h2);
            ans.setSign(-1);
            return ans;
        } else if (h1.getSign() == 1 && h2.getSign() == 1) {
            return addUtil(h1, h2);
        } else if (h1.getSign() == 1 && h2.getSign() == -1) {
            return subtractUtil(h1, h2);
        } else {
            return subtractUtil(h2, h1);
        }
    }

    public static HugeInteger subtract(HugeInteger h1, HugeInteger h2) {
        if (h1.getSign() == 1 && h2.getSign() == 1) {
            return subtractUtil(h1, h2);
        } else if (h1.getSign() == 1 && h2.getSign() == -1) {
            return addUtil(h1, h2);
        } else if (h1.getSign() == -1 && h2.getSign() == 1) {
            HugeInteger ans = addUtil(h1, h2);
            ans.setSign(-1);
            return ans;
        } else {
            return subtractUtil(h2, h1);
        }
    }

    public void setSign(int sign) {
        this.sign = sign;
    }


    public void DebugPrintArray() {
        for (int i = 0; i < MAX_LENGTH; i++) {
            System.out.print(digits[i]);
        }
        System.out.println();
    }


    public int getSign() {
        return sign;
    }

    public String toString() {
        int i = MAX_LENGTH - this.getNumberOfDigits();
        if (numberOfDigits == 0)
            return "0";
        String str = "";
        if (this.getSign() == -1)
            str = "-";
        while (i < MAX_LENGTH) {
            //System.out.print(digits[i]);
            str = str + digits[i];
            i++;
        }
        //System.out.println();
        return str;
    }


    public static void main(String args[]) {
        HugeInteger h1 = new HugeInteger("2000");
        HugeInteger h2 = new HugeInteger("-22000");
        HugeInteger h3 = HugeInteger.add(h1, h2);
        System.out.println(h1.isGreaterThan.test(h2));

      /* Predicates:
    isEqualTo,
    isGreaterThan
    isLessThan
    isGreaterThanEqual
    isLessThanEqual

        //System.out.println(new HugeInteger("1500").isEqual.test(new HugeInteger("1500")));
        //System.out.println(new HugeInteger("1500").isEqual.test(new HugeInteger("150")));
        //System.out.println(new HugeInteger("1500").isLessThan.test(new HugeInteger("1500")));
        //System.out.println(new HugeInteger("15000").isLessThan.test(new HugeInteger("1500")));
        //System.out.println(new HugeInteger("1500").isLessThanEqualTo.test(new HugeInteger("1500")));
       */

    }
}


