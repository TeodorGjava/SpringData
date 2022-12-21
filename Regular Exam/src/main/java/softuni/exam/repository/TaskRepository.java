package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entity.Task;
import softuni.exam.models.enums.CarType;

import java.util.List;

// TODO:
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findAllByCar_CarTypeOrderByPriceDesc(CarType carType);

}
