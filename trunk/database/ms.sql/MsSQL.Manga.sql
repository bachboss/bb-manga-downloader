CREATE TABLE Servers (
    S_ID            INT IDENTITY (1,1),
    S_Name          NVARCHAR(255),
    S_URL           NVARCHAR(255),
    CONSTRAINT UN_Server_Name UNIQUE(S_Name),
    CONSTRAINT PK_Server_ID PRIMARY KEY (S_ID)
);

CREATE TABLE Mangas (
    M_ID            INT IDENTITY (1,1),
    M_Name          NVARCHAR(255),  
    CONSTRAINT UN_Manga_Name UNIQUE(M_Name),
    CONSTRAINT PK_Managa_ID PRIMARY KEY (M_ID)
);

CREATE TABLE Link_Manga_Server (
    L_MS_ID         INT IDENTITY (1,1),
    L_MS_URL        NVARCHAR(255),
    L_MS_LastUpdate DATETIME,
    L_MS_Server     INTEGER REFERENCES Servers(S_ID),
    L_MS_Manga      INTEGER REFERENCES Mangas(M_ID),
    CONSTRAINT PK_Link_Manga_Server_ID PRIMARY KEY (L_MS_ID)
);

-- INSERT INTO Servers (S_Name, S_URL) VALUES ('EatManga','http://eatmanga.com/');
-- 
-- INSERT INTO Mangas (M_Name, M_URL, M_Server) VALUES ('Toriko','http://eatmanga.com/Manga-Scan/Toriko',1);