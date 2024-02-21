-- Embeddable as JSON
-- create sequence IF NOT EXISTS ChessGame_SEQ start with 1 increment by 50;
-- create sequence IF NOT EXISTS ChessMove_SEQ start with 1 increment by 50;
-- create table ChessGame (date date, round integer not null, version integer not null, id bigint not null, playerBlack varchar(255), playerWhite varchar(255), primary key (id));
-- create table ChessMove (moveNumber integer not null, version integer not null, game_id bigint, id uuid not null, move jsonb, primary key (id));
-- alter table if exists ChessMove add constraint FKmo1q9g7tl7mxi4y2tmtcjx4db foreign key (game_id) references ChessGame;

-- Soft Delete
create sequence IF NOT EXISTS ChessGame_SEQ start with 1 increment by 50;
create sequence IF NOT EXISTS ChessMove_SEQ start with 1 increment by 50;
create table ChessGame (date date, round integer not null, status varchar(255) not null check (status in ('active','inactive')), version integer not null, id bigint not null, playerBlack varchar(255), playerWhite varchar(255), primary key (id));
create table ChessMove (moveNumber integer not null, version integer not null, game_id bigint, id uuid, color smallint check (color between 0 and 1), move jsonb, primary key (id));
alter table if exists ChessMove add constraint FKmo1q9g7tl7mxi4y2tmtcjx4db foreign key (game_id) references ChessGame;
