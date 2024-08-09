package com.example.printsystem.services.IService;

import com.example.printsystem.models.entity.Team;

import java.util.List;
import java.util.Optional;

public interface ITeamService {
    public List<Team> getAllTeams();

    public Optional<Team> getTeamById(Long id);

    public Team createTeam(Team team);

    public Team updateTeam(Long id, Team teamDetails);

    public void deleteTeam(Long id);

    public Team changeTeamManager(Long teamId, Long newManagerId);

    public Team moveEmployeeToTeam(Long employeeId, Long newTeamId);
}
