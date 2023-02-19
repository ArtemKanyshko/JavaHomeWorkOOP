package ui;

import exception.BackToLastMenuException;
import exception.ExitException;
import model.Device;
import service.DeviceService;
import service.validation.DeviceValidation;
import service.validation.DeviceValidationRequest;
import service.validation.DeviceValidationResult;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class ConsoleInterface {

    private final DeviceService deviceService = DeviceService.getInstance();
    private final DeviceValidation deviceValidation = DeviceValidation.getInstance();
    private final Scanner scanner;

    public ConsoleInterface(final Scanner scanner) {
        this.scanner = scanner;
    }

    public void mainLoop() {
        System.out.println("Добро пожаловать!");

        while (true) {
            System.out.println("1 Список электроприборов");
            System.out.println("2 Действия с электроприборами");
            System.out.println("3 Выход");


            int mainChoose = scanner.nextInt();
            switch (mainChoose) {
                case 1: {
                    deviceService.listDevices().forEach(System.out::println);
                    break;
                }
                case 2: {
                    try {
                        actionLoop();
                    } catch (BackToLastMenuException e) {
                        break;
                    }
                    break;
                }
                case 3: {
                    throw new ExitException();
                }
                default: {
                    System.out.println("Unimplemented");
                    break;
                }
            }
        }
    }

    private void actionLoop() {
        while (true) {
            System.out.println("1 Добавить электроприбор");
            System.out.println("2 Удалить электроприбор");
            System.out.println("3 Выбрать электроприбор из списка (для взаимодействия)");
            System.out.println("4 Поиск электроприбор");
            System.out.println("5 Отсортировать электроприборы по мощности");
            System.out.println("6 Посчитать потребляемую мощность");
            System.out.println("0 Выход в предыдущее меню");

            int actionChoose = scanner.nextInt();
            switch (actionChoose) {
                case 1: {
                    try {
                        createDevice();
                    } catch (BackToLastMenuException e) {
                        break;
                    }
                    break;
                }
                case 2: {
                    try {
                        deleteDevice();
                    } catch (BackToLastMenuException e) {
                        break;
                    }
                    break;
                }
                case 3: {
                    try {
                        pluggableDevice();
                    } catch (BackToLastMenuException e) {
                        break;
                    }
                    break;
                }
                case 4: {
                    try {
                        findDevice();
                        break;
                    } catch (BackToLastMenuException e) {
                        break;
                    }
                }
                case 5: {
                    sortedPowerConsumption();
                    break;
                }
                case 6: {
                    sumPowerConsumption();
                    break;
                }
                case 0: {
                    throw new BackToLastMenuException();
                }
                default: {
                    System.out.println("Unimplemented");
                    return;
                }
            }
        }
    }

    private void sumPowerConsumption() {
        System.out.println("Потребляемая мощность");
        System.out.println(deviceService.sumPowerConsumption());
    }

    private void sortedPowerConsumption() {
        deviceService.listDevices().stream()
                .sorted(Comparator.comparing(Device::getPowerConsumption))
                .forEach(System.out::println);
    }

    private void findDevice() {
        List<Device> devices = deviceService.listDevices();

        System.out.println("Выбирите параметр поиска:");
        System.out.println("1 Категория");
        System.out.println("2 Название");
        System.out.println("3 Прозводитель");
        System.out.println("4 Мощность");
        System.out.println("5 Местоположение");
        System.out.println("6 Подключен ли к сети");
        System.out.println("0 Выход в предыдущее меню");

        int findParameter = scanner.nextInt();
        switch (findParameter) {
            case 1: {
                System.out.println("1 Техника для дома");
                System.out.println("2 Техника для кухни");
                System.out.println("3 Мультимедийная техника");
                System.out.println("0 Выход в предыдущее меню");

                int categoryFindChoose = scanner.nextInt();
                switch (categoryFindChoose) {
                    case 1: {
                        devices.stream()
                                .filter(device -> device.getCategory().equals("Техника для дома"))
                                .forEach(System.out::println);
                        break;
                    }
                    case 2: {
                        devices.stream()
                                .filter(device -> device.getCategory().equals("Техника для кухни"))
                                .forEach(System.out::println);
                        break;
                    }
                    case 3: {
                        devices.stream()
                                .filter(device -> device.getCategory().equals("Мультимедийная техника"))
                                .forEach(System.out::println);
                        break;
                    }
                    case 0: {
                        throw new BackToLastMenuException();
                    }
                    default: {
                        System.out.println("Unimplemented");
                        return;
                    }
                }
                System.out.println();
                break;
            }
            case 2: {
                scanner.nextLine();
                System.out.println("Введите название");
                String deviceNameFindChoose = scanner.nextLine();

                List<Device> deviceList = devices.stream()
                        .filter(device -> device.getDeviceName().equals(deviceNameFindChoose))
                        .toList();
                if (!deviceList.isEmpty()) {
                    System.out.println("По вашему запросу найдено:");
                    deviceList.forEach(System.out::println);
                } else {
                    System.out.println("Совпадений не найдено");
                }
                break;
            }
            case 3: {
                scanner.nextLine();
                System.out.println("Введите производителя");
                String deviceProducerFindChoose = scanner.nextLine();

                List<Device> deviceList = devices.stream()
                        .filter(device -> device.getProducer().equals(deviceProducerFindChoose))
                        .toList();
                if (!deviceList.isEmpty()) {
                    System.out.println("По вашему запросу найдено:");
                    deviceList.forEach(System.out::println);
                } else {
                    System.out.println("Совпадений не найдено");
                }
                break;
            }
            case 4: {
                System.out.println("Введите мощность");
                int powerConsumptionFindChoose = scanner.nextInt();

                List<Device> deviceList = devices.stream()
                        .filter(device -> device.getPowerConsumption().equals(powerConsumptionFindChoose))
                        .toList();
                if (!deviceList.isEmpty()) {
                    System.out.println("По вашему запросу найдено:");
                    deviceList.forEach(System.out::println);
                } else {
                    System.out.println("Совпадений не найдено");
                }
                break;
            }
            case 5: {
                System.out.println("Выберите местоположение:");
                System.out.println("1 Кухня");
                System.out.println("2 Ванная комната");
                System.out.println("3 Спальня");
                System.out.println("4 Гостевая комната");
                System.out.println("0 Выход в предыдущее меню");


                int categoryFindChoose = scanner.nextInt();
                switch (categoryFindChoose) {
                    case 1: {
                        devices.stream()
                                .filter(device -> device.getLocation().equals("Кухня"))
                                .forEach(System.out::println);
                        break;
                    }
                    case 2: {
                        devices.stream()
                                .filter(device -> device.getLocation().equals("Ванная комната"))
                                .forEach(System.out::println);
                        break;
                    }
                    case 3: {
                        devices.stream()
                                .filter(device -> device.getLocation().equals("Спальня"))
                                .forEach(System.out::println);
                        break;
                    }
                    case 4: {
                        devices.stream()
                                .filter(device -> device.getLocation().equals("Гостевая комната"))
                                .forEach(System.out::println);
                        break;
                    }
                    case 0: {
                        throw new BackToLastMenuException();
                    }
                }
                break;
            }
            case 6: {
                System.out.println("1 Показать электроприборы подключенные к сети");
                System.out.println("2 Показать электроприборы отключеные от сети");
                System.out.println("0 Выход в предыдущее меню");


                int pluggableFindChoose = scanner.nextInt();
                switch (pluggableFindChoose) {
                    case 1: {
                        devices.stream()
                                .filter(device -> device.getPluggable().toString().equals("true"))
                                .forEach(System.out::println);
                        break;
                    }
                    case 2: {
                        devices.stream()
                                .filter(device -> device.getPluggable().toString().equals("false"))
                                .forEach(System.out::println);
                        break;
                    }
                    case 0: {
                        throw new BackToLastMenuException();
                    }
                    default: {
                        System.out.println("Unimplemented");
                        return;
                    }
                }
                break;
            }
        }
    }

    private void pluggableDevice() {
        int element = chooseDevice();
        System.out.println("Действие:");
        System.out.println("1 Включить");
        System.out.println("2 Выключить");
        System.out.println("0 Выход в предыдущее меню");

        int choosePluggable = scanner.nextInt();
        switch (choosePluggable) {
            case 1: {
                deviceService.plugIn(element);
                break;
            }
            case 2: {
                deviceService.unPlug(element);
                break;
            }
            case 0: {
                throw new BackToLastMenuException();
            }
            default: {
                System.out.println("Unimplemented");
                return;
            }
        }
    }

    private void deleteDevice() {
        List<Device> devices = deviceService.listDevices();
        int element = chooseDevice();
        System.out.println("Подтвердите удаление:");
        System.out.println(devices.get(element).toString());
        System.out.println("1 Да");
        System.out.println("2 Нет");
        int chooseDelete = scanner.nextInt();
        switch (chooseDelete) {
            case 1: {
                deviceService.deleteDevice(element);
            }
            case 2: {
                throw new BackToLastMenuException();
            }
            default: {
                System.out.println("Unimplemented");
                return;
            }
        }
    }

    private int chooseDevice() {
        List<Device> devices = deviceService.listDevices();
        while (true) {
            for (int i = 0; i < devices.size(); i++) {
                System.out.println((i + 1) + " " + devices.get(i).toString());
            }
            System.out.println("0 Возврат в предыдущее меню");

            int chooseDevice = scanner.nextInt();
            if (chooseDevice > 0 && chooseDevice <= devices.size()) {
                devices.get(chooseDevice - 1);
                System.out.println("Вы выбрали:");
                System.out.println(devices.get(chooseDevice - 1).toString());
                return chooseDevice - 1;
            } else if (chooseDevice == 0) {
                throw new BackToLastMenuException();
            } else {
                System.out.println("Неверный ввод!");
            }
        }
    }

    private void createDevice() {
        while (true) {
            System.out.println("Выберите категорию:");
            System.out.println("1 Техника для дома");
            System.out.println("2 Техника для кухни");
            System.out.println("3 Мультимедийная техника");
            System.out.println("0 Отменить добавление");

            int categoryChoose = scanner.nextInt();
            String category = null;
            switch (categoryChoose) {
                case 1: {
                    category = "Техника для дома";
                    break;
                }
                case 2: {
                    category = "Техника для кухни";
                    break;
                }
                case 3: {
                    category = "Мультимедийная техника";
                    break;
                }
                case 0: {
                    throw new BackToLastMenuException();
                }
                default: {
                    System.out.println("Unimplemented");
                    return;
                }
            }

            System.out.println("Выберите местоположение:");
            System.out.println("1 Кухня");
            System.out.println("2 Ванная комната");
            System.out.println("3 Спальня");
            System.out.println("4 Гостевая комната");
            System.out.println("0 Отменить добавление");
            int locationChoose = scanner.nextInt();
            scanner.nextLine();
            String location = null;
            switch (locationChoose) {
                case 1: {
                    location = "Кухня";
                    break;
                }
                case 2: {
                    location = "Ванная комната";
                    break;
                }
                case 3: {
                    location = "Спальня";
                    break;
                }
                case 4: {
                    location = "Гостевая комната";
                    break;
                }
                case 0: {
                    throw new BackToLastMenuException();
                }
                default: {
                    System.out.println("Unimplemented");
                    return;
                }
            }

            System.out.println("Введите название электроприбора");
            String deviceName = scanner.nextLine();
            System.out.println("Введите прозводителя");
            String producer = scanner.nextLine();
            System.out.println("Введите мощность (ватт)");
            String powerConsumption = scanner.next();

            String pluggable = "false";

            DeviceValidationRequest request = new DeviceValidationRequest(category, deviceName, producer, powerConsumption, location, pluggable);
            DeviceValidationResult result = deviceValidation.validate(request);

            if (result.isSuccess()) {
                deviceService.create(request);
                System.out.println("Вы добавили электроприбор");
                return;
            } else {
                System.out.println("Ошибка при добавлении");
                System.out.println(result.getValidationMessage());
                continue;
            }
        }
    }
}