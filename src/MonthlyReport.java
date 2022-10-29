import java.util.HashMap;

public class MonthlyReport {
    String month;
    HashMap<String, HashMap<Integer, Integer>> expenses; /* Создаем хеш-таблицу, первое значение - наименование траты,
                                                            второе еще таблица с кол-вом и ценой */
    HashMap<String, HashMap<Integer, Integer>> incomes;  // Тоже самое, только с доходом
    int totalIncomes;   // Все доходы за месяц
    int totalExpenses;  // Все траты за месяц
    int netIncome;  // Прибыль за месяц
    String mostProfitableName;  // Название самой доходной позиции
    int mostProfitableSum;  // Сумма самой доходной позиции
    String mostExpensiveName;   // Название саомй большой траты
    int mostExpensiveSum;   // Самая большая трата
    MonthlyReport(String report, String _month) {
        month = _month;
        expenses = new HashMap<>();
        incomes = new HashMap<>();
        readReport(report);
        totalIncomes = calcMonthlyIncome();
        totalExpenses = calcMonthlyExpense();
        netIncome = calcNetIncome();
        calcMostProfitableItem();
        calcMostExpensiveItem();
    }

    void readReport(String report) {    // Считываем отчет
        String[] lines = report.split(System.lineSeparator());
        for (int i = 1; i < lines.length; i++) {
            String[] parameters = lines[i].split(",");

            Integer quantity = Integer.parseInt(parameters[2]);
            Integer price = Integer.parseInt(parameters[3]);

            if (parameters[1].equals("TRUE")) { // Проверяем, доход или убыток
                HashMap<Integer, Integer> expense = new HashMap<>();
                expense.put(quantity, price);
                expenses.put(parameters[0], expense);
            } else {
                HashMap<Integer, Integer> income = new HashMap<>();
                income.put(quantity, price);
                incomes.put(parameters[0], income);
            }
        }
    }
    int calcMonthlyExpense() {   //Считаем все затраты
        int sum = 0;
        for (HashMap<Integer, Integer> expense : expenses.values()) {
            for (Integer quantity : expense.keySet()) {
                sum += quantity * expense.get(quantity);
            }
        }
        return sum;
    }

    int calcMonthlyIncome() {    //Считаем всю прибыль
        int sum = 0;
        for (HashMap<Integer, Integer> income : incomes.values()) {
            for (Integer quantity : income.keySet()) {
                sum += quantity * income.get(quantity);
            }
        }
        return sum;
    }

    int calcNetIncome() {    //Считаем выручку
        return totalIncomes - totalExpenses;
    }

    void calcMostProfitableItem() { // Ищем самый прибыльный товар
        String bestName = "";
        int bestSum = 0;
        for (String item : incomes.keySet()) {
            for (Integer quantity : incomes.get(item).keySet()) {
                int sum = quantity * incomes.get(item).get(quantity);
                if (sum > bestSum) {
                    bestSum = sum;
                    bestName = item;
                }
            }
        }
        mostProfitableName = bestName;
        mostProfitableSum = bestSum;
    }

    void calcMostExpensiveItem() {  // Ищем самую большую трату
        String maxName = "";
        int maxSum = 0;
        for (String item : expenses.keySet()) {
            for (Integer quantity : expenses.get(item).keySet()) {
                int sum = quantity * expenses.get(item).get(quantity);
                if (sum > maxSum) {
                    maxSum = sum;
                    maxName = item;
                }
            }
        }
        mostExpensiveName = maxName;
        mostExpensiveSum = maxSum;
    }

    void printStat() {  // Печатаем стату
        System.out.println("Статистика за " + month + ":");
        System.out.println("Самый прибыльный товар за этот месяц: " + mostProfitableName + ", "
        + mostProfitableSum + "р.");
        System.out.println("Самая большая трата: " + mostExpensiveName + ", " + mostExpensiveSum + "р.");
    }
}
