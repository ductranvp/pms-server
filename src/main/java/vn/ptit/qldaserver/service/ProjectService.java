package vn.ptit.qldaserver.service;

import vn.ptit.qldaserver.domain.Project;
import vn.ptit.qldaserver.service.dto.ProjectDto;

import java.util.List;

public interface ProjectService {
    Project save(ProjectDto projectDto);

    void delete(Long id);

    void close(Long id);

    List<Project> findAll();

    Project findOne(Long id);
}
