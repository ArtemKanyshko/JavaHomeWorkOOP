package service.validation;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeviceValidationRequest {
    private String category;
    private String deviceName;
    private String producer;
    private String powerConsumption;
    private String location;
    private String pluggable;
}
