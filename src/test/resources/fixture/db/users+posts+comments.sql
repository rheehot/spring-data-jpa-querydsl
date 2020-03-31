insert into `users` (id, login_handle, smoker) values
    (1, 'pooh', FALSE),
    (2, 'felix', FALSE),
    (3, 'CSM', TRUE);

insert into `posts` (id, user_id, title, content) values
    (100, 1, 'hello', 'oh hai!'),
    (200, 1, 'bye', 'bye!'),
    (300, 3, 'not today', '...');

insert into `comments` (user_id, post_id, comment) values
    (2, 100, 'hey!'),
    (1, 100, 'nice to meet u there'),
    (2, 200, 'see you around');
