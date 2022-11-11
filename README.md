# WasteAway

## Description
### Climate Change <br/>
Background: “Singapore Will Raise Climate Ambition to Achieve Net Zero Emissions By or Around Mid Century, and Revises Carbon Tax Levels from 2024”

### Findings & Solution <br/>
Waste management has been a core issue surrounding climate change <br/>
We produce waste without proper disposal methods that do not contribute to global warming <br/>
Our application encourages users to give away excess items they no longer use <br/>
It will be an interactive platform that connects people together, allowing them to share and care for one another <br/>
Our application will feature a platform to manage all listings for products that users want to give away or receive

## Installation & Run

### SQL Server
SQL server is required to be installed in the current environment <br/>
Locate the file application.properties under /backend-spring-boot/src/main/resources <br/>
Change the value of spring.datasource.password to your root password for the SQL server <br/>
(e.g spring.datasource.password=password)

### Backend SpringBoot (Java v17.0.4.1)
Open Terminal & Run the following commands:
1. cd backend-spring-boot
2. ./mvnw spring-boot:run

### Frontend React  (Node.js v16.17.0.)
Open Terminal & Run the following commands:
1. cd frontend-react
2. npm install
3. create a .env file in WasteAway/frontend-react/ directory containing the address and port number of the backend server\
In the following format:
```
REACT_APP_API_URL="http://localhost"
REACT_APP_API_PORT=8080
```
4. npm start

You should be directed to a webpage upon completing the above instructions.
<p align="center">
  <img src="https://user-images.githubusercontent.com/91776609/200577013-a3b95e15-c34d-4839-8958-3fa4ea56bd7b.png"/>
</p>

<p align="center">
  <img src="https://user-images.githubusercontent.com/91776609/200576593-00a95353-1231-4974-a3d8-1aeb9620be32.png"/>
  <br/>
</p>



