package vn.ptit.pms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.ptit.pms.domain.Project;
import vn.ptit.pms.domain.ProjectLog;
import vn.ptit.pms.repository.ProjectLogRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Service
public class ProjectLogService {
    @Autowired
    ProjectLogRepository projectLogRepository;

    @PersistenceContext
    EntityManager em;

    public void saveLog(Long projectId, Long userId) {
        projectLogRepository.save(new ProjectLog(projectId, userId));
    }

    public List<Project> getRecentProject(Long userId){
        String sql = "SELECT DISTINCT p.* FROM project_log pl " +
                "JOIN project p ON p.id = pl.project_id " +
                "WHERE pl.user_id = " + userId + " " +
                "ORDER BY pl.id DESC LIMIT 6";
        Query query = em.createNativeQuery(sql, Project.class);

//        return projectLogRepository.findRecentProject(userId);
        return query.getResultList();
    }
}
