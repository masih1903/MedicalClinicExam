_Task 1.5.4_

**Output for list all doctors**

GET http://localhost:7070/api/doctor

HTTP/1.1 200 OK
Date: Tue, 29 Oct 2024 14:23:43 GMT
Content-Type: application/json
Content-Length: 740

[
{
"id": 1,
"name": "Dr. John Doe",
"dateOfBirth": "1975-03-15",
"yearOfGraduation": 2000,
"nameOfClinic": "City Health Clinic",
"speciality": "FAMILY_MEDICINE"
},
{
"id": 2,
"name": "Dr. Jane Smith",
"dateOfBirth": "1980-07-22",
"yearOfGraduation": 2005,
"nameOfClinic": "Downtown Medical Center",
"speciality": "PEDIATRICS"
},
{
"id": 3,
"name": "Dr. Sam Brown",
"dateOfBirth": "1969-11-05",
"yearOfGraduation": 1995,
"nameOfClinic": "General Hospital",
"speciality": "PSYCHIATRY"
},
{
"id": 4,
"name": "Dr. Emily White",
"dateOfBirth": "1985-02-28",
"yearOfGraduation": 2010,
"nameOfClinic": "Westside Clinic",
"speciality": "GERIATRICS"
},
{
"id": 5,
"name": "Dr. Michael Green",
"dateOfBirth": "1978-09-12",
"yearOfGraduation": 2003,
"nameOfClinic": "Eastside Health Services",
"speciality": "SURGERY"
}
]
Response file saved.
> 2024-10-29T152344.200.json

Response code: 200 (OK); Time: 135ms (135 ms); Content length: 740 bytes (740 B)


**Output for get doctor by id 1:**

GET http://localhost:7070/api/doctor/1

HTTP/1.1 200 OK
Date: Tue, 29 Oct 2024 14:25:01 GMT
Content-Type: application/json
Content-Length: 148

{
"id": 1,
"name": "Dr. John Doe",
"dateOfBirth": "1975-03-15",
"yearOfGraduation": 2000,
"nameOfClinic": "City Health Clinic",
"speciality": "FAMILY_MEDICINE"
}
Response file saved.
> 2024-10-29T152501.200.json

Response code: 200 (OK); Time: 10ms (10 ms); Content length: 148 bytes (148 B)


**Output for search all doctors by speciality SURGERY**

GET http://localhost:7070/api/doctor/speciality/SURGERY

HTTP/1.1 200 OK
Date: Tue, 29 Oct 2024 14:26:01 GMT
Content-Type: application/json
Content-Length: 153

[
{
"id": 5,
"name": "Dr. Michael Green",
"dateOfBirth": "1978-09-12",
"yearOfGraduation": 2003,
"nameOfClinic": "Eastside Health Services",
"speciality": "SURGERY"
}
]
Response file saved.
> 2024-10-29T152601.200.json

Response code: 200 (OK); Time: 9ms (9 ms); Content length: 153 bytes (153 B)


**Output for get all Doctors by date of birth range**

GET http://localhost:7070/api/doctor/birthdate-range/1980-01-01/1990-01-01

HTTP/1.1 200 OK
Date: Tue, 29 Oct 2024 14:30:03 GMT
Content-Type: application/json
Content-Length: 296

[
{
"id": 2,
"name": "Dr. Jane Smith",
"dateOfBirth": "1980-07-22",
"yearOfGraduation": 2005,
"nameOfClinic": "Downtown Medical Center",
"speciality": "PEDIATRICS"
},
{
"id": 4,
"name": "Dr. Emily White",
"dateOfBirth": "1985-02-28",
"yearOfGraduation": 2010,
"nameOfClinic": "Westside Clinic",
"speciality": "GERIATRICS"
}
]
Response file saved.
> 2024-10-29T153003.200.json

Response code: 200 (OK); Time: 13ms (13 ms); Content length: 296 bytes (296 B)


**Output for creating a new doctor**

POST http://localhost:7070/api/doctor

HTTP/1.1 201 Created
Date: Tue, 29 Oct 2024 14:30:51 GMT
Content-Type: application/json
Content-Length: 148

{
"id": 6,
"name": "CREATED DOCTOR",
"dateOfBirth": "1978-09-12",
"yearOfGraduation": 2003,
"nameOfClinic": "Eastside Health Services",
"speciality": "SURGERY"
}
Response file saved.
> 2024-10-29T153051.201.json

Response code: 201 (Created); Time: 34ms (34 ms); Content length: 148 bytes (148 B)


**Output for updating a existing doctor**

PUT http://localhost:7070/api/doctor/6

HTTP/1.1 200 OK
Date: Tue, 29 Oct 2024 14:31:12 GMT
Content-Type: application/json
Content-Length: 156

{
"id": 6,
"name": "UPDATED CREATED DOCTOR",
"dateOfBirth": "1978-09-12",
"yearOfGraduation": 2003,
"nameOfClinic": "Eastside Health Services",
"speciality": "SURGERY"
}
Response file saved.
> 2024-10-29T153112.200.json

Response code: 200 (OK); Time: 13ms (13 ms); Content length: 156 bytes (156 B)


**Output for deleting a existing doctor**

DELETE http://localhost:7070/api/doctor/6

HTTP/1.1 204 No Content
Date: Tue, 29 Oct 2024 14:31:29 GMT
Content-Type: text/plain

<Response body is empty>

Response code: 204 (No Content); Time: 6ms (6 ms); Content length: 0 bytes (0 B)

_Task 3.2_

Generics provide flexibility, type safety, and reusability in our code. By using generics,
we can write classes and methods that work with any data type, 
making them adaptable without requiring multiple versions for each type.

_Task 6.4_

The tests in my code perform integration testing rather than pure unit testing due to their interaction with a test-configured database.
The main differences between regular unit tests and my test are:
Unit Test: Run in isolation with mock objects, without accessing databases.
My test: Interact with a real or test database, checking actual data persistence and retrieval.

_Task 7.1_

Rest Assured is a Java library for testing RESTful APIs. It allows us to easily send HTTP requests and validate responses, 
making it ideal for verifying API endpoints' functionality.
The test ensuures correct responses and confirms API endpoints return the expected status and data.
Using Rest Assured, we can validate our endpoints' behavior, making our API more robust and reliable.

_Task 7.2_

We are using test container dependencies to run a test database in a Docker container. 
All the setup is in our Hibernate class where there is a method to create a test container and run it.

_Task 7.3_

Testing REST endpoints with Rest Assured differs from database tests by focusing on the entire API flow, 
from request handling to response structure, through HTTP. It simulates real client interactions, 
ensuring correct status codes, headers, and response data. Unlike isolated database tests, which focus only on internal logic, 
endpoint testing verifies that the full API stack functions together as expected.

