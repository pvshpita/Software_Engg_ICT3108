To read data from a database and show it in an HTML page using Spring Boot, follow this complete step-by-step guide:

✅ 1. Set up Dependencies (pom.xml)
Make sure you have the following dependencies:

xml
Copy
Edit
<dependencies>
    <!-- Spring Boot Starter Web -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <!-- Spring Boot Starter Thymeleaf -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-thymeleaf</artifactId>
    </dependency>

    <!-- Spring Boot Starter Data JPA -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>

    <!-- MySQL Connector -->
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <scope>runtime</scope>
    </dependency>
</dependencies>
✅ 2. Spring Boot Application Properties
From your image:

properties
Copy
Edit
spring.datasource.url=jdbc:mysql://localhost:3306/newdb
spring.datasource.username=root
spring.datasource.password=root123

spring.jpa.show-sql=true
spring.jpa.generate-ddl=true
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
✅ 3. Entity Class
Example: User.java

java
Copy
Edit
import jakarta.persistence.*;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;

    // Getters and Setters
}
✅ 4. Repository Interface
java
Copy
Edit
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
✅ 5. Service (Optional Layer)
java
Copy
Edit
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
✅ 6. Controller
java
Copy
Edit
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String showUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "user-list"; // Maps to user-list.html
    }
}
✅ 7. Thymeleaf HTML View (src/main/resources/templates/user-list.html)
html
Copy
Edit
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>User List</title>
</head>
<body>
<h2>Users</h2>
<table border="1">
    <thead>
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Email</th>
    </tr>
    </thead>
    <tbody>
    <tr th:each="user : ${users}">
        <td th:text="${user.id}"></td>
        <td th:text="${user.name}"></td>
        <td th:text="${user.email}"></td>
    </tr>
    </tbody>
</table>
</body>
</html>
✅ 8. Run the Application
Visit http://localhost:8080/users in your browser.


Here are the step-by-step instructions to create a Spring Boot project using Spring Initializr (https://start.spring.io/) as a Gradle project, that reads data from a MySQL database and displays it in an HTML page using Thymeleaf:

✅ Step-by-Step: Create Spring Boot Gradle Project with Spring Initializr
1️⃣ Visit Spring Initializr
Go to: https://start.spring.io/

2️⃣ Select Project Settings
Setting	Value
Project	Gradle - Groovy or Kotlin
Language	Java
Spring Boot	Choose the latest stable version
Group	com.example (or your package)
Artifact	springdbweb (or any name)
Name	springdbweb
Description	Demo project to read from DB
Package name	com.example.springdbweb
Packaging	Jar
Java	17 or higher recommended

3️⃣ Add Dependencies
Click "ADD DEPENDENCIES" and search for and add:

Spring Web

Spring Data JPA

Thymeleaf

MySQL Driver

4️⃣ Click "Generate"
This will download a .zip file.

5️⃣ Unzip and Open in IDE
Unzip the project and open it in IntelliJ, Eclipse, or VS Code (with Gradle support).

✅ Next: Configure Application
src/main/resources/application.properties
Replace contents with:

properties
Copy
Edit
spring.datasource.url=jdbc:mysql://localhost:3306/newdb
spring.datasource.username=root
spring.datasource.password=root123

spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
🔔 Make sure the MySQL server is running and the newdb database exists.

✅ Add Application Code
Create the following files in src/main/java/com/example/springdbweb:

User.java (Entity)
java
Copy
Edit
package com.example.springdbweb;

import jakarta.persistence.*;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;

    // Getters and setters
}
UserRepository.java
java
Copy
Edit
package com.example.springdbweb;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
UserService.java
java
Copy
Edit
package com.example.springdbweb;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<User> getAllUsers() {
        return repository.findAll();
    }
}
UserController.java
java
Copy
Edit
package com.example.springdbweb;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@Controller
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/users")
    public String getUsers(Model model) {
        List<User> users = service.getAllUsers();
        model.addAttribute("users", users);
        return "user-list";
    }
}
✅ Add Thymeleaf HTML
Create Template File
Create:
src/main/resources/templates/user-list.html

html
Copy
Edit
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Users</title>
</head>
<body>
<h2>User List</h2>
<table border="1">
    <thead>
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Email</th>
    </tr>
    </thead>
    <tbody>
    <tr th:each="user : ${users}">
        <td th:text="${user.id}">1</td>
        <td th:text="${user.name}">Name</td>
        <td th:text="${user.email}">Email</td>
    </tr>
    </tbody>
</table>
</body>
</html>
✅ Run the Application
You can run it using:

bash
Copy
Edit
./gradlew bootRun
Or run SpringDbWebApplication.java from your IDE.

✅ Access the Web Page
Open in your browser:
http://localhost:8080/users

Here's the updated user-list.html file with buttons for:

✅ Insert a new user

✅ Read users (already shown)

✏️ Update existing user

❌ Delete a user

This version includes a form to add a user, and action buttons for update/delete per row.

✅ user-list.html (Updated Version)
html
Copy
Edit
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>User List</title>
</head>
<body>
<h2>User List</h2>

<!-- Insert Form -->
<form th:action="@{/users}" method="post">
    <label>Name:</label>
    <input type="text" name="name" required />
    <label>Email:</label>
    <input type="email" name="email" required />
    <button type="submit">Add User</button>
</form>

<br>

<!-- User Table -->
<table border="1">
    <thead>
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Email</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <tr th:each="user : ${users}">
        <td th:text="${user.id}"></td>
        <td th:text="${user.name}"></td>
        <td th:text="${user.email}"></td>
        <td>
            <!-- Update Button -->
            <a th:href="@{'/users/edit/' + ${user.id}}">Update</a>

            <!-- Delete Button -->
            <form th:action="@{'/users/' + ${user.id}}" method="post" style="display:inline;">
                <input type="hidden" name="_method" value="delete"/>
                <button type="submit">Delete</button>
            </form>
        </td>
    </tr>
    </tbody>
</table>
</body>
</html>
🛠️ To Make This Work:
You'll need to implement the following in your controller and service:

✅ Controller Endpoints (to handle buttons)
@PostMapping("/users") → Add a user

@GetMapping("/users/edit/{id}") → Load update form

@PostMapping("/users/{id}") with _method=delete → Delete user

Below is the full setup to support Insert, Update, Read, and Delete (CRUD) operations in your Spring Boot app with Thymeleaf views.

✅ 1. UserController.java (Full CRUD Logic)
java
Copy
Edit
package com.example.springdbweb;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // READ: List all users
    @GetMapping("/users")
    public String getUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "user-list";
    }

    // INSERT: Handle new user form submission
    @PostMapping("/users")
    public String addUser(@ModelAttribute User user) {
        userService.saveUser(user);
        return "redirect:/users";
    }

    // DELETE: Delete a user by ID
    @PostMapping("/users/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/users";
    }

    // UPDATE (Step 1): Show update form
    @GetMapping("/users/edit/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "edit-user";
    }

    // UPDATE (Step 2): Handle form submission
    @PostMapping("/users/update")
    public String updateUser(@ModelAttribute User user) {
        userService.saveUser(user);  // saveUser will update if ID exists
        return "redirect:/users";
    }
}
✅ 2. UserService.java (Add Missing Methods)
java
Copy
Edit
package com.example.springdbweb;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<User> getAllUsers() {
        return repository.findAll();
    }

    public void saveUser(User user) {
        repository.save(user);
    }

    public void deleteUser(Long id) {
        repository.deleteById(id);
    }

    public User getUserById(Long id) {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
    }
}
✅ 3. edit-user.html (Thymeleaf Update Form)
Create this file:
src/main/resources/templates/edit-user.html

html
Copy
Edit
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Edit User</title>
</head>
<body>
<h2>Edit User</h2>

<form th:action="@{/users/update}" method="post">
    <input type="hidden" th:field="*{id}" />
    <label>Name:</label>
    <input type="text" th:field="*{name}" required />
    <br/>
    <label>Email:</label>
    <input type="email" th:field="*{email}" required />
    <br/>
    <button type="submit">Update</button>
</form>

<a href="/users">Back to User List</a>
</body>
</html>
This form uses th:field="*{...}" and expects the model attribute to be named user.

✅ 4. HTML List View (Reminder: user-list.html)
Already updated earlier with:

A form to insert users

A link to edit

A form to delete

✅ 5. Final Note on _method=delete
Spring Boot doesn't natively support _method=delete in HTML forms without configuration.

To handle it properly, you can either:

Keep it as-is and POST manually to delete.

Or configure hidden method filter (if using Spring Security or WebConfig).

For simplicity, we are using @PostMapping("/users/{id}") for deletion here.

✅ You now have a fully working CRUD application using Spring Boot, Thymeleaf, and MySQL.
