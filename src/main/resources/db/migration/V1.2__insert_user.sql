INSERT INTO hangman.user_data (username, age, birth_date, password)
SELECT 'qska', 20, '2015-12-17', 'password'
FROM hangman.user_data 
WHERE NOT EXISTS (
    SELECT 1
    FROM hangman.user_data 
    WHERE username = 'qska'
);
