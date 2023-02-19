package repositiry.impl;

import mapper.DeviceMapper;
import model.Device;
import repositiry.DeviceRepository;

import java.io.*;
import java.util.Collections;
import java.util.List;
import java.util.OptionalLong;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class FileDeviceRepository implements DeviceRepository {

    private static FileDeviceRepository instance;
    private static final String DEVICE_FILE_PATH = "src\\main\\resources\\devices.csv";
    private AtomicLong deviceIdCounter;

    private FileDeviceRepository() {
        try (FileReader fileReader = new FileReader(DEVICE_FILE_PATH);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {

            OptionalLong maxDeviceId = bufferedReader.lines()
                    .map(line -> line.split(",")[0])
                    .mapToLong(Long::parseLong)
                    .max();

            if (maxDeviceId.isPresent()) {
                deviceIdCounter = new AtomicLong(maxDeviceId.getAsLong());
            } else {
                deviceIdCounter = new AtomicLong();
            }
        } catch (IOException e) {
            System.out.println("IOException" + e);
        }
    }

    public static FileDeviceRepository getInstance() {
        if (instance == null) {
            instance = new FileDeviceRepository();
        }
        return instance;
    }


    @Override
    public Device save(final Device device) {
        if (device.isNew()) {
            return insert(device);
        } else {
            return update(device);
        }
    }

    private Device insert(final Device device) {
        try (FileWriter fileWriter = new FileWriter(DEVICE_FILE_PATH, true);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
             PrintWriter printWriter = new PrintWriter(bufferedWriter)) {

            long newDeviceId = deviceIdCounter.incrementAndGet();
            device.setId(newDeviceId);
            String csvString = DeviceMapper.toCsv(device);
            printWriter.println(csvString);

            return device;
        } catch (IOException e) {
            System.out.println("IOException " + e);
            return null;
        }
    }

    private Device update(final Device device) {
        try (FileReader fileReader = new FileReader(DEVICE_FILE_PATH);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {

            List<String> lines = bufferedReader.lines().collect(Collectors.toList());
            int updateLineIndex = -1;
            for (int i = 0; i < lines.size(); i++) {
                if (lines.get(i).split(",")[0].equals(device.getId().toString())) {
                    updateLineIndex = i;
                }
            }

            String csvString = DeviceMapper.toCsv(device);
            if (updateLineIndex != -1) {
                lines.remove(updateLineIndex);
                lines.add(updateLineIndex, csvString);
            }

            try (FileWriter fileWriter = new FileWriter(DEVICE_FILE_PATH);
                 BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                 PrintWriter printWriter = new PrintWriter(bufferedWriter)) {
                lines.forEach(printWriter::println);
            }

            return device;
        } catch (IOException e) {
            System.out.println("IOException " + e);
            return null;
        }
    }

    @Override
    public List<Device> deviceAllList() {
        try (FileReader fileReader = new FileReader(DEVICE_FILE_PATH);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            return bufferedReader.lines()
                    .map(DeviceMapper::toObject)
                    .collect(Collectors.toList());

        } catch (IOException e) {
            System.out.println("IOException" + e);
            return Collections.emptyList();
        }
    }


    @Override
    public void cleanAdnRecord(List devices) {
        try (FileWriter fileWriter = new FileWriter(DEVICE_FILE_PATH);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
             PrintWriter printWriter = new PrintWriter(bufferedWriter)) {

            printWriter.print("");
            for (int i = 0; i < devices.size(); i++) {
                Device device = (Device) devices.get(i);
                String csvString = DeviceMapper.toCsv(device);
                printWriter.println(csvString);
            }
        } catch (IOException e) {
            System.out.println("IOException " + e);
        }
    }
}
