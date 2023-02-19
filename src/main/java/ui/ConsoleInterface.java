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
        System.out.println("����� ����������!");

        while (true) {
            System.out.println("1 ������ ���������������");
            System.out.println("2 �������� � ����������������");
            System.out.println("3 �����");


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
            System.out.println("1 �������� �������������");
            System.out.println("2 ������� �������������");
            System.out.println("3 ������� ������������� �� ������ (��� ��������������)");
            System.out.println("4 ����� �������������");
            System.out.println("5 ������������� �������������� �� ��������");
            System.out.println("6 ��������� ������������ ��������");
            System.out.println("0 ����� � ���������� ����");

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
        System.out.println("������������ ��������");
        System.out.println(deviceService.sumPowerConsumption());
    }

    private void sortedPowerConsumption() {
        deviceService.listDevices().stream()
                .sorted(Comparator.comparing(Device::getPowerConsumption))
                .forEach(System.out::println);
    }

    private void findDevice() {
        List<Device> devices = deviceService.listDevices();

        System.out.println("�������� �������� ������:");
        System.out.println("1 ���������");
        System.out.println("2 ��������");
        System.out.println("3 ������������");
        System.out.println("4 ��������");
        System.out.println("5 ��������������");
        System.out.println("6 ��������� �� � ����");
        System.out.println("0 ����� � ���������� ����");

        int findParameter = scanner.nextInt();
        switch (findParameter) {
            case 1: {
                System.out.println("1 ������� ��� ����");
                System.out.println("2 ������� ��� �����");
                System.out.println("3 �������������� �������");
                System.out.println("0 ����� � ���������� ����");

                int categoryFindChoose = scanner.nextInt();
                switch (categoryFindChoose) {
                    case 1: {
                        devices.stream()
                                .filter(device -> device.getCategory().equals("������� ��� ����"))
                                .forEach(System.out::println);
                        break;
                    }
                    case 2: {
                        devices.stream()
                                .filter(device -> device.getCategory().equals("������� ��� �����"))
                                .forEach(System.out::println);
                        break;
                    }
                    case 3: {
                        devices.stream()
                                .filter(device -> device.getCategory().equals("�������������� �������"))
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
                System.out.println("������� ��������");
                String deviceNameFindChoose = scanner.nextLine();

                List<Device> deviceList = devices.stream()
                        .filter(device -> device.getDeviceName().equals(deviceNameFindChoose))
                        .toList();
                if (!deviceList.isEmpty()) {
                    System.out.println("�� ������ ������� �������:");
                    deviceList.forEach(System.out::println);
                } else {
                    System.out.println("���������� �� �������");
                }
                break;
            }
            case 3: {
                scanner.nextLine();
                System.out.println("������� �������������");
                String deviceProducerFindChoose = scanner.nextLine();

                List<Device> deviceList = devices.stream()
                        .filter(device -> device.getProducer().equals(deviceProducerFindChoose))
                        .toList();
                if (!deviceList.isEmpty()) {
                    System.out.println("�� ������ ������� �������:");
                    deviceList.forEach(System.out::println);
                } else {
                    System.out.println("���������� �� �������");
                }
                break;
            }
            case 4: {
                System.out.println("������� ��������");
                int powerConsumptionFindChoose = scanner.nextInt();

                List<Device> deviceList = devices.stream()
                        .filter(device -> device.getPowerConsumption().equals(powerConsumptionFindChoose))
                        .toList();
                if (!deviceList.isEmpty()) {
                    System.out.println("�� ������ ������� �������:");
                    deviceList.forEach(System.out::println);
                } else {
                    System.out.println("���������� �� �������");
                }
                break;
            }
            case 5: {
                System.out.println("�������� ��������������:");
                System.out.println("1 �����");
                System.out.println("2 ������ �������");
                System.out.println("3 �������");
                System.out.println("4 �������� �������");
                System.out.println("0 ����� � ���������� ����");


                int categoryFindChoose = scanner.nextInt();
                switch (categoryFindChoose) {
                    case 1: {
                        devices.stream()
                                .filter(device -> device.getLocation().equals("�����"))
                                .forEach(System.out::println);
                        break;
                    }
                    case 2: {
                        devices.stream()
                                .filter(device -> device.getLocation().equals("������ �������"))
                                .forEach(System.out::println);
                        break;
                    }
                    case 3: {
                        devices.stream()
                                .filter(device -> device.getLocation().equals("�������"))
                                .forEach(System.out::println);
                        break;
                    }
                    case 4: {
                        devices.stream()
                                .filter(device -> device.getLocation().equals("�������� �������"))
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
                System.out.println("1 �������� �������������� ������������ � ����");
                System.out.println("2 �������� �������������� ���������� �� ����");
                System.out.println("0 ����� � ���������� ����");


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
        System.out.println("��������:");
        System.out.println("1 ��������");
        System.out.println("2 ���������");
        System.out.println("0 ����� � ���������� ����");

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
        System.out.println("����������� ��������:");
        System.out.println(devices.get(element).toString());
        System.out.println("1 ��");
        System.out.println("2 ���");
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
            System.out.println("0 ������� � ���������� ����");

            int chooseDevice = scanner.nextInt();
            if (chooseDevice > 0 && chooseDevice <= devices.size()) {
                devices.get(chooseDevice - 1);
                System.out.println("�� �������:");
                System.out.println(devices.get(chooseDevice - 1).toString());
                return chooseDevice - 1;
            } else if (chooseDevice == 0) {
                throw new BackToLastMenuException();
            } else {
                System.out.println("�������� ����!");
            }
        }
    }

    private void createDevice() {
        while (true) {
            System.out.println("�������� ���������:");
            System.out.println("1 ������� ��� ����");
            System.out.println("2 ������� ��� �����");
            System.out.println("3 �������������� �������");
            System.out.println("0 �������� ����������");

            int categoryChoose = scanner.nextInt();
            String category = null;
            switch (categoryChoose) {
                case 1: {
                    category = "������� ��� ����";
                    break;
                }
                case 2: {
                    category = "������� ��� �����";
                    break;
                }
                case 3: {
                    category = "�������������� �������";
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

            System.out.println("�������� ��������������:");
            System.out.println("1 �����");
            System.out.println("2 ������ �������");
            System.out.println("3 �������");
            System.out.println("4 �������� �������");
            System.out.println("0 �������� ����������");
            int locationChoose = scanner.nextInt();
            scanner.nextLine();
            String location = null;
            switch (locationChoose) {
                case 1: {
                    location = "�����";
                    break;
                }
                case 2: {
                    location = "������ �������";
                    break;
                }
                case 3: {
                    location = "�������";
                    break;
                }
                case 4: {
                    location = "�������� �������";
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

            System.out.println("������� �������� ��������������");
            String deviceName = scanner.nextLine();
            System.out.println("������� ������������");
            String producer = scanner.nextLine();
            System.out.println("������� �������� (����)");
            String powerConsumption = scanner.next();

            String pluggable = "false";

            DeviceValidationRequest request = new DeviceValidationRequest(category, deviceName, producer, powerConsumption, location, pluggable);
            DeviceValidationResult result = deviceValidation.validate(request);

            if (result.isSuccess()) {
                deviceService.create(request);
                System.out.println("�� �������� �������������");
                return;
            } else {
                System.out.println("������ ��� ����������");
                System.out.println(result.getValidationMessage());
                continue;
            }
        }
    }
}