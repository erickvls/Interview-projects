# Mancala Game

Mancala is an very old game that is being played all around the world. The rules sometimes differ. 
This version's rules can be found [here](http://boardgames.about.com/cs/mancala/ht/play_mancala.htm
)

#### Goal
This program is part of an assignment at Bol.com.

#### Technologies
- It was developed in Java

- It is a Rest API.

- Spring MVC

- Spring Boot

- Docker

- Docker compose

- MongoDB

- Swagger

#### Description
- The program is written in Java and works sending a request to endpoint. 
- It is Two players
- Players enter a move by typing in the pit from where the sowing should start.
- A board containing 2 sets of 6 pits for each player laid out in two lines of 6 facing each other, 
with one larger end cup - called as Store.
The board is composed by a LinkedList of Pit. Each pit is pointing to the next pit.
- From pit 1 to 7 is from Player in the bottom, and the last is its store - eg. Player one.
- From pit 8 to 14 is from Player in the top, and the last is its store - eg. Player two.
- Position 7 and 14 are stores (Player bottom and player top).
- Each pit is filled with 6 stones.

#### Implementation
- The implementation consists one RestApi implemented in Java using Spring Boot.
- mancala-api:
    StartGame Endpoint: To create a new Game receiving two players.
    Move Endpoint: To move a specific pit position, receiving a pit position and gameid.
- Web:
    - Client is incomplete :(
  

##### Board

- Below you can see how the board is disposed, when one game is created.

```python
                     13|12|11|10 |9 |8 | <-- Player 02 - Top player
                 -------------------------
                 |  |6 |6 |6 |6 |6 |6 |  |
Player top Pit   |0 |--+--+--+--+--+--|0 |  Player bottom pit
                 |  |6 |6 |6 |6 |6 |6 |  |
                 -------------------------
                     1 |2 |3 |4 |5 |6 |  <- Player 01 - Bottom player
```

#### Build and run
- First run `mvn clean install` in project folder
- Build docker images in project folder, run `docker-compose -f docker-compose.yaml build`
- Now, start the containers using docker-compose `docker-compose -f docker-compose.yaml up -d`

- The api is running on port `8080`
- Endpoints are located in http://localhost:8080/swagger-ui.html
- MongoDB is running on port `27017`
- MongoExpress - GUI is running on port `8081`



