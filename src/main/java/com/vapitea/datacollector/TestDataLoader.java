package com.vapitea.datacollector;

import com.vapitea.datacollector.dto.UserDto;
import com.vapitea.datacollector.model.*;
import com.vapitea.datacollector.repository.UserRepository;
import com.vapitea.datacollector.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@RequiredArgsConstructor
@Component
public class TestDataLoader implements CommandLineRunner {

  private final UserService userService;
  private final TeamService teamService;
  private final DataSourceService dataSourceService;
  private final ParameterService parameterService;
  private final MeasurementService measurementService;
  private final UserRepository userRepository;


  @Override
  public void run(String... args) throws Exception {
    log.info("=========================================");
    log.info("LOADING TEST DATA TO DB");
    log.info("=========================================");


    List<User> users = createTestUsers();
    List<Team> teams = createTestTeams();
    addTestUsersToTeams(users, teams);
    List<DataSource> dataSources = createTestDataSources(teams);
    List<Parameter> parameters = createTestParameters(dataSources);
    List<Measurement> measurements = createTestMeasurement(parameters);

  }


  private List<Measurement> createTestMeasurement(List<Parameter> parameters) {
    log.info("== Creating Measurements ==");
    List<Measurement> measurements = new ArrayList<>();
    measurements.add(measurementService.createMeasurement(LocalDateTime.now(), -10d, parameters.get(0)));
    measurements.add(measurementService.createMeasurement(LocalDateTime.now(), -9.66d, parameters.get(0)));
    measurements.add(measurementService.createMeasurement(LocalDateTime.now(), -0.0d, parameters.get(0)));

    measurements.add(measurementService.createMeasurement(LocalDateTime.now(), 55d, parameters.get(1)));
    measurements.add(measurementService.createMeasurement(LocalDateTime.now(), 63.12d, parameters.get(1)));
    measurements.add(measurementService.createMeasurement(LocalDateTime.now(), 70.23d, parameters.get(1)));

    measurements.add(measurementService.createMeasurement(LocalDateTime.now(), 55d, parameters.get(5)));
    measurements.add(measurementService.createMeasurement(LocalDateTime.now(), 12.32d, parameters.get(6)));
    measurements.add(measurementService.createMeasurement(LocalDateTime.now(), 5.1d, parameters.get(7)));


    return measurements;
  }

  private List<Parameter> createTestParameters(List<DataSource> dataSources) {
    log.info("== Creating Parameters ==");


    Parameter parameter0 = Parameter.builder()
      .name("temperature")
      .unit("C")
      .build();

    Parameter parameter1 = Parameter.builder()
      .name("humidity")
      .unit("%")
      .build();
    Parameter parameter2 = Parameter.builder()
      .name("wind")
      .unit("km/h")
      .build();
    Parameter parameter3 = Parameter.builder()
      .name("rain")
      .unit("mm")
      .build();
    Parameter parameter4 = Parameter.builder()
      .name("temperature")
      .unit("C")
      .build();
    Parameter parameter5 = Parameter.builder()
      .name("humidity")
      .unit("%")
      .build();
    Parameter parameter6 = Parameter.builder()
      .name("wind")
      .unit("km/h")
      .build();
    Parameter parameter7 = Parameter.builder()
      .name("rain")
      .unit("mm")
      .build();


    List<Parameter> parameters = new ArrayList<>();
    parameters.add(parameterService.createParameter(parameter0, dataSources.get(0)));
    parameters.add(parameterService.createParameter(parameter1, dataSources.get(0)));
    parameters.add(parameterService.createParameter(parameter2, dataSources.get(0)));
    parameters.add(parameterService.createParameter(parameter3, dataSources.get(0)));
    parameters.add(parameterService.createParameter(parameter4, dataSources.get(1)));
    parameters.add(parameterService.createParameter(parameter5, dataSources.get(1)));
    parameters.add(parameterService.createParameter(parameter6, dataSources.get(2)));
    parameters.add(parameterService.createParameter(parameter7, dataSources.get(3)));


    return parameters;
  }

  private List<DataSource> createTestDataSources(List<Team> teams) {
    log.info("== Creating DataSources ==");


    DataSource dataSource0 = DataSource.builder()
      .uuid("7d253552-0054-4c22-a771-49b9cf76f148")
      .location("Budapest")
      .name("Node0")
      .description("")
      .build();

    DataSource dataSource1 = DataSource.builder()
      .uuid("9f2dce02-7aa9-4bd0-8571-a979a437f2bd")
      .location("Esztergom")
      .name("Node1")
      .description("")
      .build();

    DataSource dataSource2 = DataSource.builder()
      .uuid("f21a46f3-7065-4d14-9bf4-83ce51ad8dc7")
      .location("Visegrad")
      .name("Node2")
      .description("")
      .build();

    DataSource dataSource3 = DataSource.builder()
      .uuid("bcfdeb9c-6be0-4550-aa9d-c43804103d1d")
      .location("Vac")
      .name("Node3")
      .description("")
      .build();

    DataSource dataSource4 = DataSource.builder()
      .uuid("c17abec8-b219-478c-bcd1-43480e50e0f8")
      .location("Erd")
      .name("Node4")
      .description("")
      .build();

    List<DataSource> dataSources = new ArrayList<>();
    dataSources.add(dataSourceService.createDataSource(dataSource0, teams.get(0)));
    dataSources.add(dataSourceService.createDataSource(dataSource1, teams.get(0)));
    dataSources.add(dataSourceService.createDataSource(dataSource2, teams.get(1)));
    dataSources.add(dataSourceService.createDataSource(dataSource3, teams.get(1)));
    dataSources.add(dataSourceService.createDataSource(dataSource4, teams.get(2)));

    return dataSources;
  }

  private void addTestUsersToTeams(List<User> users, List<Team> teams) {
    log.info("== Adding users to teams ==");

    User hamilton = users.stream().filter(user -> user.getName().equals("Lewis Hamilton")).findFirst().orElse(null);
    User bottas = users.stream().filter(user -> user.getName().equals("Valtteri Bottas")).findFirst().orElse(null);
    User verstappen = users.stream().filter(user -> user.getName().equals("Max Verstappen")).findFirst().orElse(null);

    Team mercedes = teams.stream().filter(team -> team.getName().equals("Mercedes")).findFirst().orElse(null);
    Team mercedesEngine = teams.stream().filter(team -> team.getName().equals("Mercedes Engine supplier")).findFirst().orElse(null);
    Team redbull = teams.stream().filter(team -> team.getName().equals("Red Bull Racing")).findFirst().orElse(null);

    userService.addUserToTeam(hamilton.getId(), mercedes.getId());
    userService.addUserToTeam(hamilton.getId(), mercedesEngine.getId());
    userService.addUserToTeam(bottas.getId(), mercedes.getId());
    userService.addUserToTeam(bottas.getId(), mercedesEngine.getId());
    userService.addUserToTeam(verstappen.getId(), redbull.getId());

  }

  private List<Team> createTestTeams() {
    log.info("== Creating some teams ==");

    Team mercedes = teamService.createTeam(Team.builder().name("Mercedes").description("Mercedes’ modern F1 revival started with the creation of a works squad for 2010 - the platform for a meteoric rise up the Grand Prix order. The team generated huge excitement from the off with the sensational return of Michael Schumacher, but headlines soon followed on track: three podiums in their debut season, all via Nico Rosberg - who then claimed a breakthrough pole/victory double at China in 2012. The following season he was paired with Lewis Hamilton, the duo going on to stage some epic title battles as the Silver Arrows swept all before them to become one of the most dominant forces of the modern F1 era. And with Hamilton now partnered by steely Finn Valtteri Bottas, Mercedes remain very much the team to beat…").build());
    Team redbull = teamService.createTeam(Team.builder().name("Red Bull Racing").description("Red Bull were no strangers to F1 - as sponsors - prior to formally entering as a works team in 2004. Nonetheless, the scale of their success over the following decade was staggering. After a first podium in 2006, the team hit their stride in 2009, claiming six victories and second in the constructors' standings. Over the next four seasons they were a tour de force, claiming consecutive title doubles between 2010 and 2013, with Sebastian Vettel emerging as the sport's youngest quadruple champion. Now their hopes of recapturing that glory lie with an equally exciting talent – one named Max Verstappen…").build());
    Team mclaren = teamService.createTeam(Team.builder().name("McLaren").description("Since entering the sport in 1966 under the guidance and restless endeavour of eponymous founder Bruce, McLaren's success has been nothing short of breathtaking. Five glittering decades have yielded countless victories, pole positions and podiums, not to mention eight constructors' championships. What's more, some of the sport's greatest drivers made their names with the team, including Emerson Fittipaldi, Ayrton Senna, Mika Hakkinen and Lewis Hamilton...").build());
    Team ferrari = teamService.createTeam(Team.builder().name("Ferrari").description("For many, Ferrari and Formula 1 racing have become inseparable. The only team to have competed in every season since the world championship began, the Prancing Horse has grown from the humble dream of founder Enzo Ferrari to become one of the most iconic and recognised brands in the world. Success came quickly through the likes of Alberto Ascari and John Surtees, and continued – in amongst leaner times – with Niki Lauda in the 1970s and then Michael Schumacher in the 2000s, when Ferrari claimed a then unprecedented five consecutive title doubles, securing their status as the most successful and decorated team in F1 history...").build());
    Team mercedesEngine = teamService.createTeam(Team.builder().name("Mercedes Engine supplier").description("Supplier of Mercedes engines").build());
    Team renaultEngine = teamService.createTeam(Team.builder().name("Renault Engine supplier").description("Supplier of Renault engines").build());
    Team hondaEngine = teamService.createTeam(Team.builder().name("Honda Engine supplier").description("Supplier of Honda engines").build());
    Team ferrariEngine = teamService.createTeam(Team.builder().name("Ferrari Engine supplier").description("Supplier of Ferrari engines").build());

    List<Team> teams = new ArrayList<>();
    teams.add(mercedes);
    teams.add(redbull);
    teams.add(mclaren);
    teams.add(ferrari);
    teams.add(mercedesEngine);
    teams.add(renaultEngine);
    teams.add(hondaEngine);
    teams.add(ferrariEngine);

    return teams;
  }

  private List<User> createTestUsers() {
    log.info("== Creating some users ==");

    User hamilton = userService.createUser(UserDto.builder()
      .name("Lewis Hamilton")
      .password("12345")
      .dType("Admin")
      .build());
    User bottas = userService.createUser(UserDto.builder()
      .name("Valtteri Bottas")
      .password("password")
      .dType("Operator")
      .build());
    User verstappen = userService.createUser(UserDto.builder()
      .name("Max Verstappen")
      .password("qwerty")
      .dType("User")
      .build());
    User norris = userService.createUser(UserDto.builder()
      .name("Lando Norris")
      .password("letmein")
      .dType("User")
      .build());

    List<User> users = new ArrayList<>();
    users.add(hamilton);
    users.add(bottas);
    users.add(verstappen);
    users.add(norris);
    return users;
  }


}
