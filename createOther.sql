create table 차단(
    사용자명 varchar2(20),
    차단아이디 varchar2(20),
    foreign key(사용자명) references 회원(아이디),
    foreign key(차단아이디) references 회원(아이디),
    primary key(사용자명, 차단아이디)
);



create table 신고(
    신고자명 varchar2(20),
    신고아이디 varchar2(20),
    foreign key(신고자명) references 회원(아이디),
    foreign key(신고아이디) references 회원(아이디),
    primary key(신고자명, 신고아이디)
);
