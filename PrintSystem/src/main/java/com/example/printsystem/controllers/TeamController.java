package com.example.printsystem.controllers;

import com.example.printsystem.models.entity.Team;
import com.example.printsystem.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/teams")
public class TeamController {
    @Autowired
    private TeamService teamService;

    @GetMapping("/")
    public List<Team> getAllTeams() {
        return teamService.getAllTeams();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTeamById(@PathVariable Long id) {
        Team team = teamService.getTeamById(id)
                .orElseThrow(() -> new RuntimeException("Team not found for this id :: " + id));
        Map<String, Object> response = new HashMap<>();
        response.put("team", team);
        response.put("users", team.getUsers());
        return ResponseEntity.ok().body(response);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @PostMapping("/create")
    public Team createTeam(@RequestBody Team team) {
        return teamService.createTeam(team);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Team> updateTeam(@PathVariable Long id, @RequestBody Team teamDetails) {
        Team updatedTeam = teamService.updateTeam(id, teamDetails);
        return ResponseEntity.ok(updatedTeam);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable Long id) {
        teamService.deleteTeam(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @PutMapping("/{teamId}/manager/{newManagerId}")
    public ResponseEntity<?> changeTeamManager(@PathVariable Long teamId, @PathVariable Long newManagerId) {

        return ResponseEntity.ok(teamService.changeTeamManager(teamId, newManagerId));
    }
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MANAGER')")
    @PutMapping("/{newTeamId}/moveEmployee/{employeeId}")
    public ResponseEntity<?> moveEmployeeToTeam(@PathVariable Long employeeId, @PathVariable Long newTeamId) {
        teamService.moveEmployeeToTeam(employeeId, newTeamId);
        return ResponseEntity.noContent().build();
    }
}
