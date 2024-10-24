CREATE TABLE users (
                       id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY UNIQUE,
                       username VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       role VARCHAR(50) NOT NULL,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE projects (
                          id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY UNIQUE,
                          name VARCHAR(255) NOT NULL UNIQUE,
                          description TEXT NOT NULL,
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE user_project (
                              id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY UNIQUE,
                              user_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
                              project_id BIGINT REFERENCES projects(id) ON DELETE CASCADE
);

CREATE TABLE time_records (
                              id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY UNIQUE,
                              description TEXT NOT NULL,
                              start_time TIMESTAMP NOT NULL,
                              end_time TIMESTAMP NOT NULL,
                              created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                              updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                              user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                              project_id BIGINT NOT NULL REFERENCES projects(id) ON DELETE CASCADE
);
