package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Device {

    private Long id;
    private String category;
    private String deviceName;
    private String producer;
    private Integer powerConsumption;
    private String location;
    private Boolean pluggable = false;

    public Device(final String category, final String deviceName, final String producer, final Integer powerConsumption, final String location, final Boolean pluggable) {
        this.category = category;
        this.deviceName = deviceName;
        this.producer = producer;
        this.powerConsumption = powerConsumption;
        this.location = location;
        this.pluggable = pluggable;
    }

    public boolean isNew() {
        return id == null;
    }
    @Override
    public String toString() {
        String plug;
        if (pluggable) {
            plug = "включен";
        } else {
            plug = "выключен";
        }
        return category + ". "
                + "Название: "+ deviceName + ", "
                + "производитель: " + producer + ", "
                + "потребляемая мощность: " + powerConsumption + " ватт, "
                + "местоположение: " + location+ ", "
                + "состояние: " + plug;
    }
}
