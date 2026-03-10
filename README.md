# E-Commerce OpenCart Website

This is a Grocery Store API automation framework. This framework automates apis using Rest Assured, Java, TestNG, Maven, and integrated with GitHub, Docker, Jenkins. 

## Tools And Technologies Used To Automate Website

- **Java 17-** Programming language
- **Rest Assured-** API Automation
- **Maven-** Dependency management
- **TestNG-** Testing framework
- **GitHub-** Version controlling tool
- **Jenkins-** CICD tool
- **Docker-** Containerization platform
- **Apache POI-** Excel data reading
- **Allure, ChainTest-** Reports

## Scenarios Automated

a) GroceryStoreOrder- Create, Update, Delete APIs

b) GroceryStoreProducts- Create, Update, Delete APIs

b) Basic Auth API

d) Schema validation of APIs

e) JSON and XML path validations

f) Serialization and Deserialization

## Reports

Reports: After execution, a detailed HTML report will be generated at 

1)./allure-results directory.
The report contains information on test cases executed, passed, failed, and skipped.

2)./target/chaintest directory

## Instructions to run a test suite

1)Clone the Repository- git clone <url>

2)Import into IDE-IntelliJ/Eclipse. Open IntelliJ/Eclipse > Import as a Maven project

3)Run Test Suite using testng.xml through IntelliJ/Eclipse or CMD using below command- mvn clean test -Dsurefire.suiteXMlFiles=src/test/resources/testrunners/regression.xml -Denv=qa

4)Run Test Suite using docker using below command- docker run .:/app ashwinibhawar2892/grocerystore:v1.0 mvn test -Dsurefire.suiteXmlFiles=src/test/resources/testrunners/sanity.xml -Denv=qa

5)Generate Allure Report: Open CMD > Go to the project directory > run below command- allure serve allure-results

**Submitted By:**  Ashwini Bhawar                    
**EmailID:** bhawar.ashwini@gmail.com

## 🔗 Links
[![portfolio](https://img.shields.io/badge/my_portfolio-000?style=for-the-badge&logo=ko-fi&logoColor=white)](https://github.com/AshwiniBhawar)
[![linkedin](https://img.shields.io/badge/linkedin-0A66C2?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/ashwini-bhawar-421020b6/)
