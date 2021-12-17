# HappyHome
## About the Project
An information aggregation platform based on MySQL database. It contains basic information and historical data about natural disasters and house prices of different counties in the U.S.
The website allows users to look up information, get an index regarding the investment risks in each county, and save their personal information and favorite counties for future reference.

## Tech
Javascript | JSP | CSS | Java | MySQL | Tomcat

## Application Features
- Users can select a county in the U.S. to see:
  - a list of historical natural disasters happened in that county
  - current housing price, price forecast index, and an recommendation index of this county's real estate investment risk
- Users can create an account to:
  - save their basic information
  - add a county to their favorite list

## Demo
- video: 

### Home Page
![Home Page](https://drive.google.com/uc?export=view&id=1viUe706MMm_MJZQ4aQHcp4tz8CnPn7yM)
### Disaster & County Info
![Info Display](https://drive.google.com/uc?export=view&id=1Uvyfo55YoQ4hOSP8YQIe2FGzD4DN3t_5)
### Create A User
![Create User](https://drive.google.com/uc?export=view&id=1o6Q12eeFmCvb-SCrXvMCIX348F4jq3Dg)
### User Page
![User Page](https://drive.google.com/uc?export=view&id=1DvnamadMxQ7dUfg2OXkmO98oq35Ny5PJ)

## Run the App Locally
- prerequisites
  - install Tomcat v9.0
  - install Eclipse IDE
  - install and open MySQL server
  - build the `natural disaster API` in your local machine, refer to https://github.com/yueyang-wu/natural-disasters-API
- git clone this repo and open with Eclipse
- modify the `private final String` in `src/HappyHome.dal/ConnectionManager.java`
  - user = "your mysql workbench username"
  - password = "your mysql workbench password"
- right click the project name in Eclipse -> Run As -> Run On Server
- The website will pop up automatically
