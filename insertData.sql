--분야 테이블
insert into 분야 values(seq_theme.nextval,'쇼핑');
insert into 분야 values(seq_theme.nextval,'음악');
insert into 분야 values(seq_theme.nextval,'영화');
insert into 분야 values(seq_theme.nextval,'게임');
insert into 분야 values(seq_theme.nextval,'스포츠');
insert into 분야 values(seq_theme.nextval,'학습');
insert into 분야 values(seq_theme.nextval,'패션');
insert into 분야 values(seq_theme.nextval,'뷰티');
insert into 분야 values(seq_theme.nextval,'뉴스');
insert into 분야 values(seq_theme.nextval,'가정');
insert into 분야 values(seq_theme.nextval,'경제');
insert into 분야 values(seq_theme.nextval,'정치');
insert into 분야 values(seq_theme.nextval,'컴퓨터');
insert into 분야 values(seq_theme.nextval,'종교');
insert into 분야 values(seq_theme.nextval,'여행');
insert into 분야 values(seq_theme.nextval,'만화');
insert into 분야 values(seq_theme.nextval,'역사');
insert into 분야 values(seq_theme.nextval,'외국어');
insert into 분야 values(seq_theme.nextval,'요리');
insert into 분야 values(seq_theme.nextval,'과학');


--회원 테이블
--암호화 해서 저장할 것
insert into 회원 values('dangdang','1111','당당',4,null,'안녕하세요. 당당입니다.');

--게시글 테이블
insert into 게시글
values(seq_post.nextval,'게시글 제목입니다.','dangdang','안녕하세요! 일단 아무글이나 적어보는 겁니다.... 데이터를 입력해야 하니까',null,14);
