package vendingmachine.view;

import static vendingmachine.view.OutputView.printErrorMessage;

import camp.nextstep.edu.missionutils.Console;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import vendingmachine.domain.Product;

public class InputView {

    private static final String INPUT_VENDING_MACHINE_OWN_MONEY_MESSAGE = "자판기가 보유하고 있는 금액을 입력해 주세요.";

    private static final String INPUT_PRODUCTS = "상품명과 가격, 수량을 입력해 주세요.";
    private static final String INPUT_PRODUCT_REGEX = "\\[.*,[0-9]+,[0-9]+\\]";
    private static final String INPUT_PRODUCTS_DELIMITER = ";";
    private static final String INPUT_PRODUCT_DELMIETER = ",";
    private static final int INPUT_PRODUCTS_LIMIT = -1;

    private static final String INPUT_MONEY = "투입 금액을 입력해 주세요.";

    private InputView() {
    }

    public static String inputVendinMachineOwnMoney() {
        System.out.println(INPUT_VENDING_MACHINE_OWN_MONEY_MESSAGE);
        return Console.readLine();
    }

    public static List<Product> inputProducts() {
        try {
            System.out.println(INPUT_PRODUCTS);
            return calculateProductList();
        } catch (IllegalArgumentException e) {
            printErrorMessage(e);
            return inputProducts();
        }
    }

    private static List<Product> calculateProductList() {
        return inputProductValue().stream()
            .map(product -> product.split(INPUT_PRODUCT_DELMIETER))
            .map(InputView::valueToProduct)
            .collect(Collectors.toList());
    }

    private static List<String> inputProductValue() {
        List<String> products = inputNamePriceAmounts();
        checkInputProductPattern(products);
        return products.stream()
            .map(product -> product.substring(1, product.length() - 1))
            .collect(Collectors.toList());
    }

    private static List<String> inputNamePriceAmounts() {
        return Arrays.stream(Console.readLine().split(INPUT_PRODUCTS_DELIMITER, INPUT_PRODUCTS_LIMIT))
            .collect(Collectors.toList());
    }

    private static void checkInputProductPattern(List<String> products) {
        if (isInputProductPatternMismatches(products)) {
            throw new IllegalArgumentException("[ERROR] 정해진 입력방식이 아닙니다."
                + "상품명, 가격, 수량은 쉼표로, 개별 상품은 대괄호([])로 묶어 세미콜론(;)으로 구분해주세요.");
        }
    }

    private static boolean isInputProductPatternMismatches(List<String> products) {
        return products.stream()
            .anyMatch(product -> !product.matches(INPUT_PRODUCT_REGEX));
    }

    private static Product valueToProduct(String[] value) {
        return new Product(value[0], Integer.parseInt(value[1]), Integer.parseInt(value[2]));
    }

    public static String inputMoney() {
        System.out.println(INPUT_MONEY);
        return Console.readLine();
    }
}
