DROP TABLE Link_Manga_Server;
DROP TABLE Mangas;
DROP TABLE Servers;

CREATE TABLE Servers (
    S_ID            INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    S_Name          VARCHAR(255),
    S_URL           VARCHAR(255),
    CONSTRAINT UN_Server_Name UNIQUE(S_Name),
    CONSTRAINT PK_Server_ID PRIMARY KEY (S_ID)
);

CREATE TABLE Mangas (
    M_ID            INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    M_Name          VARCHAR(255),    
    CONSTRAINT UN_Manga_Name UNIQUE(M_Name),
    CONSTRAINT PK_Managa_ID PRIMARY KEY (M_ID)
);

CREATE TABLE Link_Manga_Server (
    L_MS_ID         INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    L_MS_URL        VARCHAR(255),
    L_MS_LastUpdate TIMESTAMP,
    L_MS_Server     INTEGER REFERENCES Servers(S_ID),
    L_MS_Manga      INTEGER REFERENCES Mangas(M_ID),
    CONSTRAINT PK_Link_Manga_Server_ID PRIMARY KEY (L_MS_ID)
);

CREATE TABLE Watchers (
    W_ID            INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    W_Name          VARCHAR(255),    
    CONSTRAINT PK_Watcher_ID PRIMARY KEY (W_ID)    
);

CREATE TABLE Link_Watcher_LinkMS (
    L_WL_ID         INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),    
    L_WL_LinMS      INTEGER REFERENCES Link_Manga_Server(L_MS_ID),
    L_Watcher       INTEGER REFERENCES Watchers(W_ID),
    CONSTRAINT PK_Link_Watcher_LinkMS_ID PRIMARY KEY (L_WL_ID)    
);

-- INSERT INTO Servers (S_Name, S_URL) VALUES ('EatManga','http://eatmanga.com/');
-- 
-- INSERT INTO Mangas (M_Name, M_URL, M_Server) VALUES ('Toriko','http://eatmanga.com/Manga-Scan/Toriko',1);