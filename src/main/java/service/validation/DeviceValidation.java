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
        String deviceNameRegexp = "^[\\da-zA-Z�-���-ߨ ]{1,30}$";
        Pattern deviceNamePattern = Pattern.compile(deviceNameRegexp);
        String deviceNameInvalid = "������������ �������� ��� ������������� ��������������. ������ ���� �� ���� � ���� ������ �� 1 �� 30 ��������";

        String powerConsumptionRegexp = "^\\d{2,5}$";
        Pattern powerConsumptionPattern = Pattern.compile(powerConsumptionRegexp);
        String powerConsumptionInvalid = "�������� ��������. ������ ���� �� ���� ������ �� 2 �� 5 ��������";

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
