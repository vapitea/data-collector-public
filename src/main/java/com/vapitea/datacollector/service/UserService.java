package com.vapitea.datacollector.service;

import com.vapitea.datacollector.model.Team;
import com.vapitea.datacollector.model.User;
import com.vapitea.datacollector.repository.TeamRepository;
import com.vapitea.datacollector.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;

    public UserService(UserRepository userRepository, TeamRepository teamRepository) {
        this.userRepository = userRepository;
        this.teamRepository = teamRepository;
    }

    public User createUser(String name, String password) {
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        return userRepository.save(user);
    }


    @Transactional
    public User addUserToTeam(User user, Team team) {
        User managedUser = userRepository.findById(user.getId()).orElseThrow();
        Team managedTeam = teamRepository.findById(team.getId()).orElseThrow();
        managedUser.getTeams().add(managedTeam);
        managedTeam.getUsers().add(managedUser);
        return managedUser;
    }


    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getOne(Long id) {
        return userRepository.findById(id).orElseThrow();
    }

    public User getOneWithTeams(Long id) {
        return userRepository.getOneWithTeamsFetchedEagerly(id).orElseThrow();

    }

    @Transactional
    public User modifyUser(Long id, User changedUser) {
        if (userRepository.existsById(id)) {
            return userRepository.save(changedUser);
        }
        throw new NoSuchElementException();
    }

    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow();
        List<Team> teams = user.getTeams();

        for (Team team : teams) {
            team.getUsers().remove(user);
        }

        userRepository.delete(user);
    }

    @Transactional
    public void removeUserFromTeam(Long userId, Long teamId) {
        User user = userRepository.findById(userId).orElseThrow();
        Team team = teamRepository.findById(teamId).orElseThrow();
        user.getTeams().remove(team);
        team.getUsers().remove(user);
    }
}
