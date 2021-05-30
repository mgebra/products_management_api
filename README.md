# Product Management API

#### setup
* To create mysql in docker execute this steps
```
docker build -t proucts-management .

docker run --name proucts-management -p 3306:3306 -e MYSQL_ROOT_PASSWORD=my_sql_password -d proucts-management
```
* create products_management database and execute these sql
```
CREATE TABLE products (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(1000),
    weight	DECIMAL,
    price DECIMAL NOT NULL,
    country VARCHAR(500) NOT NULL,
    create_date DATETIME NOT NULL,
    update_date DATETIME
);

CREATE INDEX products_name ON products (name);
```

#### Using API with POSTMAN
* you can use this Postman collection: ProductsMagagementApi.postman_collection.json

#### Technologies
* [Spring Boot](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)
* [Maven](https://maven.apache.org/guides/index.html)
* [MySql](https://www.mysql.com/)

##### Authors:
* Mamuka Mghebrishvili - mgebra@gmail.com