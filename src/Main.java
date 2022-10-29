import java.util.Scanner;
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Reports reports = new Reports();
        boolean yearlyReportLoaded = false;
        boolean monthlyReportLoaded = false;

        while (true) {
            printMenu();
            int command = scanner.nextInt();
            if (command == 1) {
                try {
                    for (int i = 1; i <= 3; i++) {
                        reports.readMonthlyReport("2021", "0" + i);
                    }
                    System.out.println("Отчет загружен.");
                    monthlyReportLoaded = true;
                } catch (Exception e) {
                    System.out.println("Невозможно прочитать файл с месячным отчётом. Возможно, файл не находится в нужной директории.");
                }
            } else if (command == 2) {
                try {
                    reports.readYearlyReport("2021");
                    System.out.println("Отчет загружен.");
                    yearlyReportLoaded = true;
                } catch (Exception e) {
                    System.out.println("Невозможно прочитать файл с годовым отчётом. Возможно, файл не находится в нужной директории.");
                }
            } else if (command == 3) {
                if (yearlyReportLoaded && monthlyReportLoaded) {
                    reports.compareReports();
                } else {
                    System.out.println("Для сверки данных необходимо сначала загрузить отчеты за год и месяц.");
                }
            } else if (command == 4) {
                if (monthlyReportLoaded) {
                    reports.printStatByMonth();
                } else {
                    System.out.println("Сначала загрузите отчет.");
                }
            } else if (command == 5) {
                if (yearlyReportLoaded) {
                    reports.printStatByYear();
                } else {
                    System.out.println("Сначала загрузите отчет.");
                }
            } else if (command == 0) {
                System.out.println("Работа завершена.");
                break;
            }
        }
    }

    public static void printMenu() {    // Печать меню
        System.out.println("Выбирите необходимое действие:");
        System.out.println(
                "1. Считать все месячные отчёты\n" +
                        "2. Считать годовой отчёт\n" +
                        "3. Сверить отчёты\n" +
                        "4. Вывести информацию о всех месячных отчётах\n" +
                        "5. Вывести информацию о годовом отчёте\n" +
                        "0. Выход из программы"
        );
    }
}

