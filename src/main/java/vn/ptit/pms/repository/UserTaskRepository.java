package vn.ptit.pms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import vn.ptit.pms.domain.User;
import vn.ptit.pms.domain.UserTask;
import vn.ptit.pms.domain.key.UserTaskKey;
import vn.ptit.pms.service.dto.UserDto;

import java.util.List;

public interface UserTaskRepository extends JpaRepository<UserTask, UserTaskKey> {
    List<UserTask> findByIdUserId(Long userId);

    @Modifying
    @Query(value = "DELETE FROM user_task ut WHERE ut.user_id = ?1", nativeQuery = true)
    int deleteUserTask(Long userId);

    List<UserTask> findByIdTaskId(Long taskId);

    @Query(value = "SELECT new vn.ptit.pms.service.dto.UserDto(u) " +
            "FROM vn.ptit.pms.domain.User u " +
            "JOIN UserTask ut ON u.id = ut.id.userId WHERE ut.id.taskId = ?1")
    List<UserDto> findUserByTaskId(Long taskId);
}
