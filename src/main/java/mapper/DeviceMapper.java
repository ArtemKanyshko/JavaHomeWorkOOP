package mapper;

import model.Device;
import service.validation.DeviceValidationRequest;

public class DeviceMapper {

    public static String toCsv(final Device device) {
        CsvBuilder builder = new CsvBuilder();
        return builder.add(device.getId().toString())
                .add(device.getCategory())
                .add(device.getDeviceName())
                .add(device.getProducer())
                .add(device.getPowerConsumption().toString())
                .add(device.getLocation())
                .addAndBuild(device.getPluggable().toString());
    }

    public static Device toObject(final String csvString) {
        String[] strings = csvString.split(",");
        int i = 0;

        Device device = new Device();
        device.setId(Long.parseLong(strings[i++]));
        device.setCategory(strings[i++]);
        device.setDeviceName(strings[i++]);
        device.setProducer(strings[i++]);
        device.setPowerConsumption(Integer.parseInt(strings[i++]));
        device.setLocation(strings[i++]);
        device.setPluggable(Boolean.parseBoolean(strings[i++]));

        return device;
    }

    public static Device toObject (final DeviceValidationRequest request) {
        return new Device(request.getCategory(),
                request.getDeviceName(),
                request.getProducer(),
                Integer.parseInt(request.getPowerConsumption()),
                request.getLocation(),
                Boolean.parseBoolean(request.getPluggable()));
    }
}
