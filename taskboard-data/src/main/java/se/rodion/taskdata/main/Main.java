package se.rodion.taskdata.main;
//package se.rodion.taskboard.main;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//import org.springframework.context.annotation.AnnotationConfigApplicationContext;
//import org.springframework.data.domain.PageRequest;
//
//import se.rodion.taskboard.exception.ServiceException;
//import se.rodion.taskboard.model.Issue;
//import se.rodion.taskboard.model.Team;
//import se.rodion.taskboard.model.User;
//import se.rodion.taskboard.model.WorkItem;
//import se.rodion.taskboard.service.IssueService;
//import se.rodion.taskboard.service.TeamService;
//import se.rodion.taskboard.service.UserService;
//import se.rodion.taskboard.service.WorkItemService;
//import se.rodion.taskboard.status.WorkItemStatus;
//
//public class Main
//{
//
//	public static void main(String[] args) throws ServiceException, ParseException
//	{
//		try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext())
//		{
//			context.scan("se.rodion.taskboard");
//			context.refresh();
//			
//			UserService userService = context.getBean(UserService.class);
//			
//			System.out.println(userService.findByUserNumber("1001"));
//			System.out.println(userService.findByFirstNameAndLastNameAndUserNumber("Anders10", "Andersson10", "1001"));
//			System.out.println(userService.findByFirstNameOrLastNameOrUserNumber("Anders4", "Zlobin1", "1003"));
//			User user1 = new User("Anders10", "Andersson10", "master1234", "1001");
//			User user2 = new User("Anders2", "Andersson2", "master2345", "1002");
//			User user3 = new User("Anders3", "Andersson3", "master3456", "1003");
//			User user4 = new User("Anders4", "Andersson4", "master4567", "1004");
//			User user5 = new User("Anders5", "Andersson5", "master5678", "1005");
//			User user6 = new User("Anders7", "Andersson7", "master7890", "1007");
//			userService.save(user1);
//			userService.save(user2);
//			userService.save(user3);
//			userService.save(user4);
//			userService.save(user5);
//			userService.save(user6);
//			
//			userService.save(new User(1L, "Rodion1", "Zlobin1", "student1", "2001", "ACTIVE"));
//			
//			userService.inactivateUser(userService.findByUserNumber("1005"));
//			
//			System.out.println(userService.findByTeam(7L));
//			
//			
//			TeamService teamService = context.getBean(TeamService.class);
//			Team team1 = teamService.findTeam(6L);
//			Team team1 = new Team(6L, "Number1");
//			Team team1 = new Team("Number1");
//			Team team2 = new Team("Number4");
//			teamService.save(team1);
//			teamService.save(team2);
//			User user6 = userService.findByUserNumber("1001");
//			teamService.addUser(teamService.findTeam(7L), userService.findByUserNumber("1001"));
//			teamService.addUser(teamService.findTeam(8L), userService.findByUserNumber("1002"));
//			teamService.addUser(teamService.findTeam(7L), userService.findByUserNumber("1006"));
//			teamService.addUser(team1, user1);
//			teamService.addUser(team2, user2);
//			teamService.addUser(team1, user3);
//			teamService.addUser(team2, user4);
//			teamService.addUser(team1, user5);
//			
//			teamService.inactivateTeam(teamService.findTeam(8L));
//			System.out.println(teamService.findAll());
//			
//			Team team4 = new Team(6L, "new");
//			teamService.save(team4);
//			
//			WorkItemService workItemService = context.getBean(WorkItemService.class);
//			WorkItem workItem1 = new WorkItem("Item12");
//			WorkItem workItem2 = new WorkItem("Item13");
//			WorkItem workItem3 = new WorkItem("Item8");
//			WorkItem workItem4 = new WorkItem("Item9");
//			WorkItem workItem5 = new WorkItem("Item11");
//			workItemService.save(workItem1);
//			workItemService.save(workItem2);
//			workItemService.save(workItem3);
//			workItemService.save(workItem4);
//			workItemService.save(workItem5);
//			
//			WorkItem workItem2 = new WorkItem (19L, "Item1", "UNSTARTED");
//			WorkItem workItem2 = new WorkItem (19L, "Item2", "UNSTARTED");
//			workItemService.changeStatus(workItemService.findById(28L), WorkItemStatus.DONE);
//			workItemService.changeStatus(workItemService.findById(17L), WorkItemStatus.UNSTARTED);
//			workItemService.changeStatus(workItemService.findById(18L), WorkItemStatus.DONE);
//			workItemService.changeStatus(workItemService.findById(12L), WorkItemStatus.DONE);
//			workItemService.deleteWorkItem(10L);
//			
//			System.out.println(workItemService.findById(20L));
//			
//			workItemService.addWorkItemToUser(workItemService.findById(9L), userService.findByUserNumber("1004"));
//			workItemService.addWorkItemToUser(workItemService.findById(11L), userService.findByUserNumber("1004"));
//			workItemService.addWorkItemToUser(workItemService.findById(10L), userService.findByUserNumber("1002"));
//			workItemService.addWorkItemToUser(workItemService.findById(12L), userService.findByUserNumber("1002"));
//			workItemService.addWorkItemToUser(workItemService.findById(13L), userService.findByUserNumber("1005"));
//			workItemService.addWorkItemToUser(workItem2, user2);
//			workItemService.addWorkItemToUser(workItem3, user3);
//			workItemService.addWorkItemToUser(workItem4, user4);
//			workItemService.addWorkItemToUser(workItem5, user3);
//			
//			System.out.println(workItemService.fetchAllWorkItemsByStatus(WorkItemStatus.STARTED));
//			System.out.println(workItemService.fetchAllWorkItemsByStatus(WorkItemStatus.UNSTARTED));
//			
//			System.out.println(workItemService.fetchWorkItemsByTeam("Number2"));
//			
//			System.out.println(workItemService.fetchWorkItemsByUser(userService.findByUserNumber("1005")));
//			
//			System.out.println(workItemService.fetchWorkItemsByDescription("te"));
//			
//			IssueService issueService = context.getBean(IssueService.class);
//			
//			Issue issue1 = new Issue("Fantasy");
//			issueService.saveIssue(issue1);
//			Issue issue2 = new Issue("Fanta");
//			issueService.saveIssue(issue2);
//			
//			issueService.addIssueToWorkItem(issueService.findIssueById(15L), workItemService.findById(12L));
//			issueService.addIssueToWorkItem(issueService.findIssueById(14L), workItemService.findById(11L));
//			
//			Issue issue2 = issueService.findIssueById(15L);
//			issue2.setId(16L);
//			issueService.saveIssue(issue2);
//			
//			issueService.findAll(2, 2).forEach(e -> System.out.println(e));
//			userService.findAll(1, 3).forEach(e -> System.out.println(e));
//			teamService.findAll(1, 2).forEach(e -> System.out.println(e));
//			workItemService.findAll(1, 4).forEach(e -> System.out.println(e));
//			workItemService.findAll(2, 5).forEach(System.out::println);
//			teamService.findAll().forEach(System.out::println);
//			
//			System.out.println(issueService.findWorkItemsWithIssue());
//			
//			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//			Date date1 = format.parse("2016-02-14");
//			Date date2 = format.parse("2016-02-20");
//			
//			workItemService.fetchAllWorkItemInSpecifiedPeriod(date1, date2).forEach(System.out::println);
//			workItemService.fetchAllWorkItemWithSpecifiedPeriodAndStatus(date1, date2, WorkItemStatus.STARTED).forEach(System.out::println);
//			
//			issueService.findWorkItemsWithIssue().forEach(System.out::println);
//			
//			System.out.println((workItemService.findById(1L)).getCreatingDate());
//			
//		}
//
//	}
//
//}
