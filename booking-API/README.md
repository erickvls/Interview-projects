# Booking API

This project is an assignment for Alten.

#### Remarks
- This is an API for booking a room.
- For the purpose of the test, the hotel has only one room available - It is created when application start.
    - Room(id=1,price=120).
- To give a chance to everyone to book the room, the stay can’t be longer than 3 days and
  can’t be reserved more than 30 days in advance.
- All reservations start at least the next day of booking.
- To simplify the use case, a “DAY’ in the hotel room starts from 00:00 to 23:59:59.
- To simplify the API is insecure.
- It was assumed only a basic scenario where we have a room and a reservation. It was not taken into consideration 
other relationships such as: client, invoice, so on.
![Association](https://i.ibb.co/2gvFZWk/Untitled-Diagram-1.png)

 - To simplify the API, the application was not dealing with client id. Although we know that it must have in a real application.
 - To simplify the API, the application is handling with roomId and bookingId to perform the operations.
 - It was assumed a date format from [Date and time notation in Canada - Wikipedia](https://en.wikipedia.org/wiki/Date_and_time_notation_in_Canada#:~:text=The%20YYYY%20%2D%20MM%20%2D%20DD%20format%20is%20the%20only%20method%20of,formats%20often%20results%20in%20misinterpretation)
 
#### Documentation


This section describes the available API endpoints with example request and response.

There are two endpoins in an application.

    /api/v1/booking
    /api/v1/room


- For booking, we have 4 operations: Create a booking, find a booking, cancel a booking and update a booking.
- For room, we can check the room availability.

#### 1. Booking
##### 1.1 Create a reservation
![Create a reservation](https://i.ibb.co/chFYxB2/http-POST-fw.png)

```python
POST /api/v1/booking

request body:
{
    "startDate": "2021-10-20",
    "endDate":"2021-10-22",
    "room": 1
}

```

```python

response:
{
    "id": 1,
    "startDate": "2021-10-20T00:00:00",
    "endDate": "2021-10-22T23:59:59",
    "cancelled": false
}

```

- As we are assuming only one room for the hotel, we are already passing its id.

##### 1.2 View a booking
![See Get scenario](https://i.ibb.co/58Qr5nP/http-GET-fw.png)


```python
GET /api/v1/booking/{id}
eg.:/api/v1/booking/1

{id} => bookingId

response:
{
    "id": 1,
    "startDate": "2021-10-20T00:00:00",
    "endDate": "2021-10-22T23:59:59",
    "cancelled": false
}

```

##### 1.3 Update a booking

- For testing purpose, as we have only one room, the application is assuming to update only 
the check-in(startDate) and check-out(endDate).

![See patch scenario](https://i.ibb.co/nMpSJC8/http-PATCH-update-fw.png)


```python
PATCH /api/v1/booking/{id}/update
eg.: /api/v1/booking/1/update

{id} => bookingId

request body:
{
    "startDate": "2021-10-13",
    "endDate":"2021-10-15"
}

startDate: new check-in date.
endDate: new check-out date.

```

```python

response:
{
    "id": 1,
    "startDate": "2021-10-13T00:00:00",
    "endDate": "2021-10-15T23:59:59",
    "cancelled": false
}

```


##### 1.4 Cancel a booking
![See Post scenario](https://i.ibb.co/nqX6JRy/http-PATCH-cancel-fw.png)


```python
PATCH /api/v1/booking/{id}/cancel
eg.: /api/v1/booking/1/cancel

{id} => bookingId

response:
{
    "id": 1,
    "startDate": "2021-10-13T00:00:00",
    "endDate": "2021-10-15T23:59:59",
    "cancelled": true
}

```

#### 2. Room
##### 1.1 Check room availability
![See Get scenario](https://i.ibb.co/4Khpwkc/http-GET-Room-fw.png)



```python
GET api/v1/room/{id}/availability?startDate=2021-10-10&endDate=2021-10-10
eg.: api/v1/room/1/availability??startDate=2021-10-15&endDate=2021-10-15

{id} => roomId

response:
[
    "2021-10-10",
    "2021-10-02",
       .....
    "2021-10-15"
]

```
- It will return all available dates in that range.

#### Technologies
- It was developed in Java

- It is a Rest API.

- Spring MVC

- Spring Boot

- Docker

- Docker compose

- Postgres

- Swagger

- Hibernate

#### Build and run
- Clone the repository
- Run `mvn clean install` in project folder
- Build docker images in project folder, run `docker-compose -f docker-compose.yaml build`
- Now, start the containers using docker-compose `docker-compose -f docker-compose.yaml up -d`

- The api is running on port `8080`
- Endpoints are located in http://localhost:8080/swagger-ui/



