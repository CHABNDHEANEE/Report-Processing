import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

public class Reports {
    HashMap<String, MonthlyReport> reportsByMonths; // Хеш-таблица с отчетами по месяцам
    HashMap<String, YearlyReport> reportsByYears; // Хеш-таблица с отчетами по годам

    Reports() {
        reportsByMonths = new HashMap<>();
        reportsByYears = new HashMap<>();
    }

    void readMonthlyReport(String year, String month) { // Считываем месячный отчет
        String report = readFileContentsOrNull("resources/m." + year + month + ".csv");
        if (report != null) {
            month = getMonth(month);
            MonthlyReport monthlyReport = new MonthlyReport(report, month);
            reportsByMonths.put(month, monthlyReport);
        }
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

    void printStatByMonth() {   // Печать статы по месяцам
        reportsByMonths.get("Январь").printStat();
        reportsByMonths.get("Февраль").printStat();
        reportsByMonths.get("Март").printStat();
    }

    void readYearlyReport(String year) {    // Считываем годовалый отчет
        String report = readFileContentsOrNull("resources/y." + year + ".csv");
        if (report != null) {
            YearlyReport yearlyReport = new YearlyReport(report, year);
            reportsByYears.put(year, yearlyReport);
        }
    }

    void printStatByYear() {    // Печать статы по году
        reportsByYears.get("2021").printStat();
    }

    void compareReports() { // Сравниеваем отчеты за месяц и год
            boolean compared = true;
            for (MonthlyReport monthlyReport : reportsByMonths.values()) {
                String month = monthlyReport.month; // Месяц, за который проводим сверку
                int monthIncome = monthlyReport.totalIncomes; // Доход в мсячном отчете
                int yearIncome = reportsByYears.get("2021").incomes.get(month); // Доход в годовом отчете
                int monthExpense = monthlyReport.totalExpenses; // Расходы в месячном отчете
                int yearExpense = reportsByYears.get("2021").expenses.get(month); // Расходы в годовом отчете
                if (monthIncome != yearIncome || monthExpense != yearExpense) {
                    compared = false;
                    System.out.println("Обнаружена ошибка в " + month);
                    break;
                }
            }
            if (compared) {
                System.out.println("Ошибок в отчете не найдено.");
            }
    }
    String readFileContentsOrNull(String path) {    //Функция для чтения файла
        try {
            return Files.readString(Path.of(path));
        } catch (IOException e) {
            System.out.println("Невозможно прочитать файл с месячным отчётом. Возможно, файл не находится в нужной директории.");
            return null;
        }
    }
}