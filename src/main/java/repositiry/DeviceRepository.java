package repositiry;

import model.Device;
import java.util.List;

public interface DeviceRepository {
    Device save(Device device);
    List<Device> deviceAllList();
    void cleanAdnRecord(List devices);
}
