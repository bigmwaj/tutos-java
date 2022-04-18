# Spring Cloud Config Server
This tutorial help to design a Spring Cloud Config with a local git repos.

## Step 1: Create the Config Repos
1. Create a dir outside your main project. Let name that repos dir to **config-repos** and **${config.repos}** the path to access to it.
2. Copy the content of **src/main/resources/config-repos** to your **${config.repos}**

## Step 2: Configure the config repos
Access the application configuration **src/main/resources/application.yml** and update the value of **spring.cloud.config.server.git.uri:${user.home}/${config.repos}**

## Step 3: Start the application
Run the class **ConfigServerApplication** as a java application

## Step 4: Test the application
1. Access the dev config [here](http://localhost:8181/client-config/dev "Dev config") 
2. Access the prod config [here](http://localhost:8181/client-config/prod "Prod config")
3. Access the default config [here](http://localhost:8181/client-config/default "Default config")

------------------------------------------------------------

Spring documentation: <https://docs.spring.io/spring-cloud-config/docs/current/reference/html/>