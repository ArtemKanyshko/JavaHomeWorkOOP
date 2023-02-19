package service.validation;

import java.util.regex.Pattern;

public class DeviceValidation {

    private static DeviceValidation instance;

    private DeviceValidation() {

    }

    public static DeviceValidation getInstance() {
        if (instance == null) {
            instance = new DeviceValidation();
        }
        return instance;
    }

    public DeviceValidationResult validate (final DeviceValidationRequest request) {
        String deviceNameRegexp = "^[\\da-zA-Zа-яёА-ЯЁ ]{1,30}$";
        Pattern deviceNamePattern = Pattern.compile(deviceNameRegexp);
        String deviceNameInvalid = "Неподходящее название или производитель электроприбора. Должно быть из букв и цифр длиной от 1 до 30 символов";

        String powerConsumptionRegexp = "^\\d{2,5}$";
        Pattern powerConsumptionPattern = Pattern.compile(powerConsumptionRegexp);
        String powerConsumptionInvalid = "Неверная мощность. Должна быть из цифр длиной от 2 до 5 символов";

        DeviceValidationResult result = new DeviceValidationResult();
        if (!deviceNamePattern.matcher(request.getDeviceName()).matches()) {
            result.addError(deviceNameInvalid);
        }
        if (!deviceNamePattern.matcher(request.getProducer()).matches()) {
            result.addError(deviceNameInvalid);
        }
        if (!powerConsumptionPattern.matcher(request.getPowerConsumption()).matches()) {
            result.addError(powerConsumptionInvalid);
        }
        return result;
    }
}
