SELECT u.id u_id,
       u.username u_username,
       u.email u_email,
       r.id r_id,
       r.code r_code FROM users u
LEFT JOIN user_roles ur on u.id = ur.user_id
LEFT JOIN roles r on r.id = ur.role_id
WHERE u.id = :userId ;