Communication
===============================

Create social network using Spring technologies.

### Specification

##### Users
- 2 types of users: admin and regular users
- abilities for every user:
   - create new post on main or profile page
   - add comments, likes and do reposts
   - delete his own posts, likes and reposts
   - browse list of users
   - chat with every other user 
   - update profile parameters like picture, name and other secondary fields
- special abilities for admins
   - delete any post
   - delete users from list of users
- new users must confirm their email address

##### Posts
- post may contain text or graphic files
- on main page user sees only post from his subscriptions or his own one

Projects contains integration and service test.

### Used libraries and frameworks
- Spring Boot 2
- Spring Security
- Spring Data
- Lombok
- JUnit 5
