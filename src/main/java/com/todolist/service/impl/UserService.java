package com.todolist.service.impl;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.mail.internet.AddressException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.todolist.dto.PerformanceDto;
import com.todolist.dto.ProjectDto;
import com.todolist.dto.TaskDto;
import com.todolist.dto.UserDto;
import com.todolist.exception.NotFoundException;
import com.todolist.exception.UsedAdressException;
import com.todolist.persistence.entity.User;
import com.todolist.persistence.repository.UserRepository;
import com.todolist.service.IEmailService;
import com.todolist.service.IProjectService;
import com.todolist.service.ITaskService;
import com.todolist.service.IUserService;
import com.todolist.utils.TasksPriorityAttributes;
import com.todolist.utils.TasksStatusAttributes;


/**
 * @author maximebn
 */
@Service
@Transactional
public class UserService implements IUserService {
	private static String unrecognizedMail= "Adresse mail non valide ou déjà existante";
	private  BCryptPasswordEncoder passwordEncoder = new  BCryptPasswordEncoder();
	
	@Autowired UserRepository userRepo;
	@Autowired IProjectService projectService;
	@Autowired ITaskService taskService;
	@Autowired IEmailService emailService;

	
	// ---------------------------------------------------------------------------------------------------------------------------//
	@Override
	public User findByLoginAndPassword(String username, String password) {
		User user = userRepo.findByLogin(username);
		
		if (passwordEncoder.matches(password, user.getPassword())) {
			return user;
		}
		else throw new NotFoundException(NotFoundException.UNRECOGNIZEDUSER);
	}
	
	
	// ---------------------------------------------------------------------------------------------------------------------------//
	/**
	 * New user registration : mail validity checking (username) + Inbox (standard project) initialization
	 * @param UtilisateurDto
	 * @return UtilisateurDto
	 */
	@Override
	public UserDto register(UserDto userDto) throws AddressException {
		if (emailService.isValidEmailAddress(userDto.getMail()) && !emailService.isEmailAlreadyUsed(userDto.getMail())) {

		String encryptPass = passwordEncoder.encode(userDto.getPassword());
		userDto.setPassword(encryptPass);
		
		User user = new User();
		user.setName(userDto.getName());
		user.setMail(userDto.getMail());
		user.setPassword(userDto.getPassword());
		
		userRepo.save(user);
		
		ProjectDto projectDto = new ProjectDto();
		projectDto.setTitle("Inbox");
		projectService.save(projectDto, user.getId());

		return userDto;
	}
		else {
			if (!emailService.isValidEmailAddress(userDto.getMail())) {
			throw new AddressException(unrecognizedMail);
		}
			throw new UsedAdressException();
		}
	}
	
	
	// ---------------------------------------------------------------------------------------------------------------------------//
	/**
	 * User data update : password, name, mail ...
	 * @param UtilisateurDto
	 * @return UtilisateurDto
	 */
	@Override
	public UserDto updateUserData(UserDto userDto, long userId) throws AddressException {		
		Optional<User> opt = userRepo.findById(userId);
		
		if (opt.isPresent()) {
			User user = opt.get();
			
			if (!userDto.getMail().equals(user.getMail())) {
				if (emailService.isValidEmailAddress(userDto.getMail()) && !emailService.isEmailAlreadyUsed(userDto.getMail())) {
					user.setMail(userDto.getMail());
			}
				else throw new AddressException(unrecognizedMail);
			}
			user.setName(userDto.getName());
			
			if (!passwordEncoder.encode(userDto.getPassword()).equals(user.getPassword())) {
				user.setPassword(passwordEncoder.encode(userDto.getPassword()));
			}
	
			userRepo.save(user);
			return userDto;
		}
		else throw new NotFoundException(NotFoundException.UNRECOGNIZEDUSER);
		}
	// ---------------------------------------------------------------------------------------------------------------------------//
	
	
	// ---------------------------------------------------------------------------------------------------------------------------//
	/**
	 * User account deletion 
	 * @param idUtilisateur
	 * @return void
	 */
	 @Override
	 public void deleteAccount(long userId) {
		Optional<User> opt = userRepo.findById(userId);
			
		if (opt.isPresent()) {
			userRepo.deleteById(userId);
		}
		else throw new NotFoundException(NotFoundException.UNRECOGNIZEDUSER);
		}
		
	
	// ---------------------------------------------------------------------------------------------------------------------------//
	/**
	 * Used later to calculate a performance index. Here, artificial number of user's tasks enhanced or reduced depending on tasks' priorities.
	 * @param tasks list
	 * @return long
	 */
	@Override
	public double artificalNumberOfTasks(List<TaskDto> list) {
		double artificialN = 0;
		for (TaskDto t : list) {
			if(t.getPriority()!=null) {
				
				switch(t.getPriority()) {
				case TasksPriorityAttributes.HIGHIMPORTANCE:
					artificialN +=3;
					break;
					
				case TasksPriorityAttributes.IMPORTANT:
					artificialN +=2;
					break;
					
				case TasksPriorityAttributes.STANDARD:
				break;
					
				default:
					artificialN +=1;
				}

		}
			else {
				artificialN += 1;
			}
		}
		System.out.println(artificialN);
		return artificialN;
	}
	
	
	// ---------------------------------------------------------------------------------------------------------------------------//
	/**
	 * Calculation of the performance index = completed tasks/artificial number of tasks
	 * @param idUtilisateur
	 * @return long
	 */
		@Override
		public PerformanceDto getPerformanceIndex(long userId) {
			Optional<User> opt = userRepo.findById(userId);
			LocalDate sixDaysAgo = LocalDate.now().minusDays(6);
			
			if (opt.isPresent()) {
				List <TaskDto> inLateTasks = taskService.findForWeek(sixDaysAgo, userId).stream().filter(t -> t.getStatus().equals(TasksStatusAttributes.INLATE)).collect(Collectors.toList());
				List <TaskDto> doneTasks= taskService.findDoneForWeek(sixDaysAgo, userId);
				
				double totalN = (this.artificalNumberOfTasks(doneTasks) + this.artificalNumberOfTasks(inLateTasks));
				double indiceDePerformance = 0;
				
				if (totalN > 0) {
					indiceDePerformance = (this.artificalNumberOfTasks(doneTasks) / totalN)*100;
				}
				
				PerformanceDto perfDto = new PerformanceDto();
				perfDto.setDoneTasksNumber(doneTasks.size());
				perfDto.setInLateTasksNumber(inLateTasks.size());
				perfDto.setPerformanceIndex((long)indiceDePerformance);
				
				return perfDto;
			}
			else throw new NotFoundException(NotFoundException.UNRECOGNIZEDUSER);
		}

			
}

