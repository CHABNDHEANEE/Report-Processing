import java.util.HashMap;

public class YearlyReport {
    String year;
    HashMap<String, Integer> expenses;
    HashMap<String, Integer> incomes;
    HashMap<String, Integer> netIncome;
    int totalIncome;
    int totalExpenses;
    double avgIncome;
    double avgExpense;
    YearlyReport(String report, String _year) {
        expenses = new HashMap<>();
        incomes = new HashMap<>();
        netIncome = new HashMap<>();
        readReport(report);
        year = _year;
        totalIncome = calcTotalIncome();
        totalExpenses = calcTotalExpenses();
        calcNetIncome();
        avgIncome = calcAvgIncome();
        avgExpense = calcAvgExpense();
    }

    void readReport(String report) {    // Считываем отчет
        String[] lines = report.split(System.lineSeparator());
        for (int i = 1; i < lines.length; i++) {
            String[] parameters = lines[i].split(",");
            Integer sum = Integer.parseInt(parameters[1]);

            if (parameters[2].equals("false")) { // Проверяем, доход или убыток
                incomes.put(getMonth(parameters[0]), sum);
            } else {
                expenses.put(getMonth(parameters[0]), sum);
            }
        }
    }

    int calcTotalIncome() {   // Считаем общие доходы
        int sum = 0;
        for (Integer income : incomes.values()) {
            sum += income;
        }
        return sum;
    }

    int calcTotalExpenses() {   // Считаем общие расходы
        int sum = 0;
        for (Integer expense : expenses.values()) {
            sum += expense;
        }
        return sum;
    }

    void calcNetIncome() {  // Считаем среднею прибыль
        for (String month : incomes.keySet()) {
            int income = incomes.get(month) - expenses.get(month);
            netIncome.put(month, income);
        }
    }

    double calcAvgIncome() {    // Считаем средние доходы
        double sum = totalIncome;
        int months = incomes.size();
        return sum / months;
    }

    double calcAvgExpense() {   // Считаем средние траты
        double sum = totalExpenses;
        int months = expenses.size();
        return sum / months;
    }
    String getMonth(String month) { // Получаем название месяца
        switch (month) {
            case "01":
                return "Январь";
            case "02":
                return "Февраль";
            case "03":
                return "Март";
            default:
                return "";
        }
    }

    void printStat() {  // Печатаем стату
        System.out.println("Статистика за " + year + ":");
        System.out.println("Прибыль по каждому месяцу: ");
        printNetIncome();
        System.out.println("Средние расходы за все месяцы в году: " + String.format(".%2f", avgExpense));
        System.out.println("Средний доход за все месяцы в году: " + String.format(".%2f", avgIncome));
    }

    void printNetIncome() { // Печатаем общую прибыль
        for (String month : netIncome.keySet()) {
            System.out.println(month + ": " + netIncome.get(month));
        }
    }
}
