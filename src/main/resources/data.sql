INSERT INTO lecture (lecture_id, title, instructor, description) VALUES
    ('L001', 'Introduction to AI', 'Dr. Smith', 'A comprehensive overview of artificial intelligence concepts and applications.'),
    ('L002', 'Blockchain Technology', 'Prof. Johnson', 'Exploring the fundamentals of blockchain and its various applications in different industries.');

INSERT INTO lecture_session (session_id, lecture_id, session_datetime, max_capacity)
VALUES
    ('S001', 'L001', '2024-10-10 09:00:00', 30),
    ('S002', 'L001', '2024-10-11 09:00:00', 30),
    ('S003', 'L001', '2024-10-12 09:00:00', 30),
    ('S004', 'L001', '2024-10-13 09:00:00', 30),
    ('S005', 'L001', '2024-10-14 09:00:00', 30),

    ('S006', 'L002', '2024-11-10 10:00:00', 30),
    ('S007', 'L002', '2024-11-11 10:00:00', 30),
    ('S008', 'L002', '2024-11-12 10:00:00', 30),
    ('S009', 'L002', '2024-11-13 10:00:00', 30),
    ('S010', 'L002', '2024-11-14 10:00:00', 30);