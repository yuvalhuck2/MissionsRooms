package Repositories;

import Domain.ClassGroup;
import org.springframework.data.repository.CrudRepository;

public interface GroupRepository extends CrudRepository<ClassGroup, String> {
}
