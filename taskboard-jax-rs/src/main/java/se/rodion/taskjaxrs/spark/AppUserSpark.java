package se.rodion.taskjaxrs.spark;

import static spark.Spark.get;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import se.rodion.taskdata.model.Team;
import se.rodion.taskdata.model.User;
import se.rodion.taskdata.service.TeamDataService;
import se.rodion.taskdata.service.UserDataService;

public class AppUserSpark
{
	
	public static void main(String[] args)
	{
		@SuppressWarnings("resource")
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		Gson gson = new GsonBuilder().create();
	
				context.scan("se.rodion.taskdata");
				context.refresh();
				
				UserDataService userDataService = context.getBean(UserDataService.class);
				TeamDataService teamDataService = context.getBean(TeamDataService.class);
				
				get("/user/:userNumber", (request, response) -> {
					String userNumber = request.params(":userNumber");
					User user = userDataService.findByUserNumber(userNumber);
					response.type("application/json");
//					return gson.toJson(user);
					return user.getTeam().getName();
				});
				
				get("/team/:teamName", (request, response) -> {
					String teamName = request.params(":teamName");
					Team team = teamDataService.findByName(teamName);
					response.type("application/json");
					return team.getId();
				});

		
	}
}
