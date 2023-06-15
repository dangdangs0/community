create table 분야(
    분야번호 number primary key,
    분야명 varchar2(20)
);

create table 회원(
    아이디 varchar2(20) PRIMARY KEY,
    비밀번호 varchar2(50) not null,
    닉네임 varchar2(50) not null,
    관심분야 number,
    프로필사진 blob,
    한줄소개 varchar2(100),
    foreign key(관심분야) references 분야(분야번호)
);

create table 게시글(
    게시글번호 number PRIMARY KEY,
    제목 varchar2(50) not null,
    작성자 varchar2(20) not null,
    내용 clob not null,
    사진 blob,
    분야 number,
    foreign key(분야) references 분야(분야번호),
    foreign key(작성자) references 회원(아이디)
);

create table 댓글(
    게시글번호 number,
    댓글번호 number,
    작성자 varchar2(20) not null,
    내용 varchar(1000) not null,
    primary key(댓글번호, 게시글번호),
    foreign key(게시글번호) references 게시글(게시글번호),
    foreign key(작성자) references 회원(아이디)
);


create sequence seq_theme; --분야번호 시퀀스 생성
create sequence seq_post; --게시글 시퀀스
create sequence seq_reply; --댓글 시퀀스
