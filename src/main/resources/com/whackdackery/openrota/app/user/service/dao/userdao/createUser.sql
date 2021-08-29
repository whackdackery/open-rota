INSERT INTO users (username, email, password, created, updated)
VALUES (:user.username, :user.email, :user.password, NOW(), NOW());