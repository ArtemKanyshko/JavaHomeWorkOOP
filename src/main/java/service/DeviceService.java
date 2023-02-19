package service;

import mapper.DeviceMapper;
import model.Device;
import repositiry.DeviceRepository;
import repositiry.impl.FileDeviceRepository;
import service.validation.DeviceValidationRequest;
import java.util.List;

public class DeviceService {

    private final DeviceRepository deviceRepository = FileDeviceRepository.getInstance();

    private static DeviceService instance;

    private DeviceService() {
    }

    public static DeviceService getInstance() {
        if (instance == null) {
            instance = new DeviceService();
        }
        return instance;
    }

    public Device create(DeviceValidationRequest request) {
        return deviceRepository.save(DeviceMapper.toObject(request));
    }

    public void plugIn(int element) {
        List<Device> deviceList = deviceRepository.deviceAllList();
        Device device = deviceList.get(element);

        if (!device.getPluggable()) {
            System.out.println(device.getDeviceName() + " " + device.getProducer() + " On");
            device.setPluggable(true);
            deviceRepository.save(device);
        } else {
            System.out.println("Прибор включен");
        }
    }

    public void unPlug(int element) {
        List<Device> deviceList = deviceRepository.deviceAllList();
        Device device = deviceList.get(element);

        if (device.getPluggable()) {
            System.out.println(device.getDeviceName() + " " + device.getProducer() + " Off");
            device.setPluggable(false);
            deviceRepository.save(device);
        } else {
            System.out.println("Прибор выключен");
        }
    }

    public List<Device> listDevices() {
        return deviceRepository.deviceAllList();
    }

    public void deleteDevice(int element) {

        List<Device> deviceList = deviceRepository.deviceAllList();

        deviceList.remove(element);
        deviceRepository.cleanAdnRecord(deviceList);
    }

    public int sumPowerConsumption() {
        List<Device> devices = listDevices();

        List<Device> devicePluggableList = devices.stream()
                .filter(device -> device.getPluggable().toString().equals("true"))
                .toList();

        return devicePluggableList.stream().mapToInt(Device::getPowerConsumption).sum();
    }
}
