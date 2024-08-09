package com.example.printsystem.services;

import com.example.printsystem.models.entity.Team;
import com.example.printsystem.models.entity.User;
import com.example.printsystem.models.repository.TeamRepository;
import com.example.printsystem.models.repository.UserRepository;
import com.example.printsystem.services.IService.ITeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TeamService implements ITeamService {
    @Autowired
    private TeamRepository _teamRepository;

    @Autowired
    private UserRepository _userRepository;

    @Override
    public List<Team> getAllTeams() {
        return _teamRepository.findAll();
    }

    @Override
    public Optional<Team> getTeamById(Long id) {
        return _teamRepository.findById(id);
    }

    @Override
    public Team createTeam(Team team) {
        team.setCreateTime(LocalDateTime.now());
        team.setNumberOfMember(0);
        return _teamRepository.save(team);
    }

    @Override
    public Team updateTeam(Long id, Team teamDetails) {
        Team team = _teamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Team not found for this id :: " + id));
        team.setName(teamDetails.getName());
        team.setDescription(teamDetails.getDescription());
        team.setNumberOfMember(teamDetails.getNumberOfMember());
        team.setManagerId(teamDetails.getManagerId());
        team.setUpdateTime(LocalDateTime.now());
        return _teamRepository.save(team);
    }

    @Override
    public void deleteTeam(Long id) {
        Team team = _teamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Team not found for this id :: " + id));
        _teamRepository.delete(team);
    }

    @Override
    public Team changeTeamManager(Long teamId, Long newManagerId) {
        Team team = _teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Team not found for this id :: " + teamId));
        team.setManagerId(newManagerId);
        team.setUpdateTime(LocalDateTime.now());
        return _teamRepository.save(team);
    }

    @Override
    public Team moveEmployeeToTeam(Long employeeId, Long newTeamId) {
        User user = _userRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("User not found for this id :: " + employeeId));
        Team newTeam = _teamRepository.findById(newTeamId)
                .orElseThrow(() -> new RuntimeException("Team not found for this id :: " + newTeamId));
        user.setTeam(newTeam);
        newTeam.setNumberOfMember(newTeam.getNumberOfMember() + 1);
        newTeam.setUpdateTime(LocalDateTime.now());
        _userRepository.save(user);
        return _teamRepository.save(newTeam);
    }
}
