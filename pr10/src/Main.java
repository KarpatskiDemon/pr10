import java.util.Scanner;
import java.util.InputMismatchException;

public class Main {
    private static final String[] usNameAr = new String[15];
    private static final String[] passwAr = new String[15];
    private static int kilkUs = 15; //поставив "2", коли робив скріни щоб не вписувати 15 юзерів(дууууууууууже довго)

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.println("""
                        
                        Оберіть дію :
                        1. Зареєструвати нового користувача.
                        2. Виконання дії від користувача.
                        3. Показати користувачів.
                        4. Вихід
                        """);
                System.out.println("Сюди : ");
                int num = scanner.nextInt();
                scanner.nextLine();
                if (num == 1) {
                    registrUser();
                } else if (num == 2) {
                    diya();
                } else if (num == 3) {
                    showUs();
                } else if (num == 4) {
                    System.out.println("Goodbye!(");
                    break;
                } else {
                    throw new IllegalArgumentException("Введіть число від 1 до 4");
                }
            } catch (InputMismatchException e) {
                System.out.println("Помилка : Ви ввели не число. Введіть число!(1-4)");
                scanner.nextLine();
            } catch (IllegalArgumentException e) {
                System.out.println("Помилка : " + e.getMessage());
            }
        }
    }

    public static void registrUser() {
        try {
            Scanner scanner = new Scanner(System.in);

            System.out.println("Введіть ім'я користувача : ");
            String usName = scanner.nextLine();
            perevUsName(usName);
            boolean added = false;

            String passw = getValidPassword();

            for (int i = 0; i < kilkUs; i++) {
                if (usNameAr[i] == null) {
                    usNameAr[i] = usName;
                    passwAr[i] = passw;
                    System.out.println("Користувач " + usName + " зареєстрований");
                    added = true;
                    break;
                }
            }
            if (!added) {
                throw new IllegalArgumentException("Максимально юзерів(>15)");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Помилка : " + e.getMessage());
        }
    }

    public static String getValidPassword() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.println("Введіть пароль для користувача : ");
                String passw = scanner.nextLine();
                perevPassw(passw);
                return passw;
            } catch (IllegalArgumentException e) {
                System.out.println("Помилка : " + e.getMessage());
                System.out.println("Спробуйте ввести пароль ще раз");
            }
        }
    }

    public static void perevUsName(String usName) {
        if (usName.length() < 5) {
            throw new IllegalArgumentException("Мало символів");
        }
        boolean perevSpace = false;
        for (int i = 0; i < usName.length(); i++) {
            char c = usName.charAt(i);
            if (c == ' ') {
                perevSpace = true;
            }
            if (perevSpace) {
                throw new IllegalArgumentException("Ім'я користувача не може містити пробіли");
            }
        }
    }

    public static void perevPassw(String passw) {
        if (passw.length() < 10) {
            throw new IllegalArgumentException("Пароль має бути не менше 10 символів");
        }

        int kilkSimv = 0;
        boolean perevSpecialSimv = false;
        boolean perevSpace = false;
        boolean perevLatin = false;

        for (int i = 0; i < passw.length(); i++) {
            char c = passw.charAt(i);

            if (c >= '0' && c <= '9') {
                kilkSimv = kilkSimv + 1;
            }
            else if (c == ' ') {
                perevSpace = true;
            }
            else if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
                perevLatin = true;
            }
            else if ((c >= '!' && c <= '/') || (c >= ':' && c <= '@') || (c >= '[' && c <= '`') || (c >= '{' && c <= '~')) {
                perevSpecialSimv = true;
            }
            else {
                throw new IllegalArgumentException("Пароль має складатись виключно з латинських символів, спеціальних символів та цифр");
            }
        }

        if (perevSpace) {
            throw new IllegalArgumentException("Пароль не може містити пробіли");
        }
        if (kilkSimv < 3) {
            throw new IllegalArgumentException("Пароль має містити щонайменше 3 цифри");
        }
        if (!perevSpecialSimv) {
            throw new IllegalArgumentException("Пароль має містити щонайменше 1 спеціальний символ");
        }
    }

    public static void diya() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введіть ім'я користувача: ");
        String usName = scanner.nextLine();

        System.out.println("Введіть пароль: ");
        String passw = scanner.nextLine();

        boolean authenticated = false;

        for (int i = 0; i < usNameAr.length; i++) {
            if (usNameAr[i] != null && usNameAr[i].equals(usName) && passwAr[i].equals(passw)) {
                System.out.println("Користувача " + usName + " успішно аутентифіковано!");
                authenticated = true;

                while(true) {
                    try {
                        System.out.println("""
                            Меню користувача:
                            1. Видалити свій акаунт
                            2. Повернутися до головного меню
                            """);
                        System.out.println("Ваш вибір: ");
                        int choice = scanner.nextInt();
                        scanner.nextLine();

                        if (choice == 1) {
                            System.out.println("Для підтвердження видалення введіть свій пароль ще раз:");
                            String confirmPassw = scanner.nextLine();

                            if (confirmPassw.equals(passw)) {
                                usNameAr[i] = null;
                                passwAr[i] = null;
                                kilkUs = kilkUs - 1;
                                System.out.println("Ваш акаунт було успішно видалено");
                                return;
                            } else {
                                System.out.println("Неправильний пароль. Видалення скасовано");
                            }
                        } else if (choice == 2) {
                            System.out.println("Повернення до головного меню...");
                            break;
                        } else {
                            throw new IllegalArgumentException("Введіть число від 1 до 2");
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Помилка: Ви ввели не число. Введіть число!(1-2)");
                        scanner.nextLine();
                    } catch (IllegalArgumentException e) {
                        System.out.println("Помилка: " + e.getMessage());
                    }
                }
                break;
            }
        }

        if (!authenticated) {
            System.out.println("Помилка: Неправильне ім'я користувача або пароль");
        }
    }

    public static void showUs() {
        System.out.println("Список користувачів:");
        for (int i = 0; i < usNameAr.length; i++) {
            if (usNameAr[i] != null) {
                System.out.println((i + 1) + ". Користувач: " + usNameAr[i]);
            }
        }
    }
}