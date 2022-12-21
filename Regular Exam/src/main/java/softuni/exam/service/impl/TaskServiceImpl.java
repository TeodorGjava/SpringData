package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.xml.tasks.ImportTaskDTO;
import softuni.exam.models.dto.xml.tasks.ImportTasksWrapper;
import softuni.exam.models.entity.Car;
import softuni.exam.models.entity.Mechanic;
import softuni.exam.models.entity.Task;
import softuni.exam.models.enums.CarType;
import softuni.exam.repository.CarRepository;
import softuni.exam.repository.MechanicRepository;
import softuni.exam.repository.PartRepository;
import softuni.exam.repository.TaskRepository;
import softuni.exam.service.TaskService;
import softuni.exam.util.ValidationCheck;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static softuni.exam.constants.Messages.TASK_IMPORTED;
import static softuni.exam.constants.Messages.TASK_INVALID;
import static softuni.exam.constants.Paths.TASKS_PATH;

@Service
public class TaskServiceImpl implements TaskService {
    private static final String OUTPUT_FORMAT = "Car %s %s with %dkm%n" +
            "-Mechanic: %s %s - task №%d:%n" +
            "--Engine: %.1f%n" +
            "---Price: %.2f$";
    private final ModelMapper modelMapper;
    private final MechanicRepository mechanicRepository;
    private final CarRepository carRepository;
    private final TaskRepository taskRepository;
    private final PartRepository partRepository;
    private final XmlParser xmlParser;
    private final ValidationCheck validationCheck;

    @Autowired
    public TaskServiceImpl(ModelMapper modelMapper, MechanicRepository mechanicRepository, CarRepository carRepository, TaskRepository taskRepository, PartRepository partRepository, XmlParser xmlParser, ValidationCheck validationCheck) {
        this.modelMapper = modelMapper;
        this.mechanicRepository = mechanicRepository;
        this.carRepository = carRepository;
        this.taskRepository = taskRepository;
        this.partRepository = partRepository;
        this.xmlParser = xmlParser;
        this.validationCheck = validationCheck;
    }

    @Override
    public boolean areImported() {
        return this.taskRepository.count() > 0;
    }

    @Override
    public String readTasksFileContent() throws IOException {
        return Files.readString(TASKS_PATH);
    }

    @Override
    public String importTasks() throws IOException, JAXBException {
        final StringBuilder str = new StringBuilder();

        final ImportTasksWrapper tasksWrapper = this.xmlParser.fromFile(TASKS_PATH.toFile(), ImportTasksWrapper.class);
        final List<ImportTaskDTO> taskDTOS = tasksWrapper.getTasks();
        for (ImportTaskDTO taskDTO : taskDTOS) {
            boolean isValid = this.validationCheck.isValid(taskDTO);
            final Optional<Mechanic> mechanicByFirstName = this.mechanicRepository.findFirstByFirstName(taskDTO.getMechanic().getFirstName());

            if (mechanicByFirstName.isEmpty()) {
                isValid = false;
            }
            final Optional<Car> carById = carRepository.findFirstById(taskDTO.getCar().getId());
            if (carById.isEmpty()) {
                isValid = false;
            }
            if (isValid) {
                final Mechanic mechanicToAdd = mechanicByFirstName.get();
                final Car carToAdd = carById.get();
                final Task taskToSave = this.modelMapper.map(taskDTO, Task.class);

                taskToSave.setCar(carToAdd);
                taskToSave.setMechanic(mechanicToAdd);

                str.append(String.format(TASK_IMPORTED, taskDTO.getPrice()));
                this.taskRepository.saveAndFlush(taskToSave);
            } else {
                str.append(String.format(TASK_INVALID));
            }
        }


        return str.toString();
    }

    //    • "Car {carMake} {carModel} with {kilometers}km
//           -Mechanic: {firstName} {lastName} - task №{taskId}
//            --Engine: {engine}
//  ---Price: {taskPrice}$
//. . ."
    @Override
    public String getCoupeCarTasksOrderByPrice() {
        return
                this.taskRepository.findAllByCar_CarTypeOrderByPriceDesc(CarType.coupe)
                        .stream().map(task -> String.format(
                                OUTPUT_FORMAT, task.getCar().getCarMake()
                                , task.getCar().getCarModel(), task.getCar().getKilometers()
                                , task.getMechanic().getFirstName(), task.getMechanic().getLastName()
                                , task.getId(), task.getCar().getEngine(),
                                task.getPrice()
                        )).collect(Collectors.joining(System.lineSeparator()));
    }
}
