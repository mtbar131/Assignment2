package hello;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by aabhijitbarve on 8/14/2015.
 */

    @RestController
    public class HugeIntegerController {

    @RequestMapping("/add")
    public String HugeIntegerAdd(@RequestParam(value="number1", defaultValue="100") String num1, @RequestParam(value = "number2", defaultValue = "100") String num2) {
        return HugeInteger.add(new HugeInteger(num1), new HugeInteger(num2)).toString();
    }

    @RequestMapping("/sub")
    public String HugeIntegerSub(@RequestParam(value="number1", defaultValue="100") String num1, @RequestParam(value = "number2", defaultValue = "100") String num2) {
        return HugeInteger.subtract(new HugeInteger(num1), new HugeInteger(num2)).toString();
    }

    @RequestMapping("/lessthan")
    public boolean HugeIntegerlessThan(@RequestParam(value="number1", defaultValue="100") String num1, @RequestParam(value = "number2", defaultValue = "100") String num2) {
        return new HugeInteger(num1).isLessThan.test(new HugeInteger(num2));
    }

    @RequestMapping("/lessthanorequal")
    public boolean HugeIntegerlessThanEqualTo(@RequestParam(value="number1", defaultValue="100") String num1, @RequestParam(value = "number2", defaultValue = "100") String num2) {
        return new HugeInteger(num1).isLessThanEqualTo.test(new HugeInteger(num2));
    }

    @RequestMapping("/greaterthan")
    public boolean HugeIntegerGreaterThan(@RequestParam(value="number1", defaultValue="100") String num1, @RequestParam(value = "number2", defaultValue = "100") String num2) {
        return new HugeInteger(num1).isGreaterThan.test(new HugeInteger(num2));
    }

    @RequestMapping("/greaterthanequal")
    public boolean HugeIntegerGreaterThanEqualTo(@RequestParam(value="number1", defaultValue="100") String num1, @RequestParam(value = "number2", defaultValue = "100") String num2) {
        return new HugeInteger(num1).isGreaterThanEqualTo.test(new HugeInteger(num2));
    }

    @RequestMapping("/iszero")
    public boolean HugeIntegerisZero(@RequestParam(value="number1", defaultValue="100") String num1) {
        return new HugeInteger(num1).isZero.test(new HugeInteger(num1));
    }

    @RequestMapping("/isequal")
    public boolean HugeIntegerisEqual(@RequestParam(value="number1", defaultValue="100") String num1, @RequestParam(value = "number2", defaultValue = "100") String num2) {
        return new HugeInteger(num1).isEqual.test(new HugeInteger(num2));
    }
}
