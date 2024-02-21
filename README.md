# Talk: What's new since Hibernate 6.0

Some developers were a little disappointed by Hibernate ORM 6.0.
It mostly brought internal changes and renamed packages (... thanks to JPA 3.0).

But the situation has drastically changed since then.
The following releases brought a huge number of improvements and new features.

We will take a closer look at the most interesting ones during this talk.

## Working with the example projects
The example projects require a database. GitHub Codespace or an IDE supporting Development Containers start up the database automatically. Or you can download and start the Docker configuration manually.

### GitHub Codespace or Development Containers integration
The easiest way to work with the example projects is to use a [free GitHub Codespace](https://github.com/features/codespaces) or the Development Containers of your IDE (supported by VSCode and IntelliJ). Both automatically start up the required database in a Docker container.

[![Open in GitHub Codespaces](https://github.com/codespaces/badge.svg)](https://codespaces.new/Persistence-Hub/Talk_NewSinceHibernate60)

### Manual Docker setup

I’m using a PostgreSQL database in all code samples and exercises of this course. I’ve prepared a docker-compose configuration that starts the database on localhost:5432 and a pgAdmin on localhost:80.

To run the docker-compose command, please make sure that docker is installed on your machine and open a command line in the project root folder. You can then start the environment by executing the following commands:
- docker-compose build
- docker-compose up

After you start the docker-compose configuration, you can access your database at localhost:5432 using the user postgres and the password postgres.